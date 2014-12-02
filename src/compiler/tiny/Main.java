package compiler.tiny;

import compiler.tiny.inter.Program;
import compiler.tiny.lexer.Lexer;
import compiler.tiny.lexer.LexerException;
import compiler.tiny.parser.ParseException;
import compiler.tiny.parser.Parser;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Noisyfox on 2014/12/1.
 * 主程序
 */
public class Main {
    private static Scanner in = new Scanner(System.in);
    private static PrintWriter out = new PrintWriter(System.out, true);

    public static void main(String args[]) {
        out.println(ABOUT);

        if (args.length != 1) {
            out.println(USAGE);
            return;
        }

        out.println("Compiling: " + args[0]);
        out.println();

        File inputFile = new File(args[0]);
        FileInputStream fis;
        try {
            fis = new FileInputStream(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String fileNameWithoutExt = inputFile.getName();
        int lastDot = fileNameWithoutExt.lastIndexOf('.');
        if (lastDot != -1) {
            fileNameWithoutExt = fileNameWithoutExt.substring(0, lastDot);
        }

        String file_c, file_exe, file_list, file_grammar;

        out.printf("C File [%s.c]: ", fileNameWithoutExt);
        out.flush();
        file_c = in.nextLine();
        out.printf("Run File [%s.exe]: ", fileNameWithoutExt);
        out.flush();
        file_exe = in.nextLine();
        out.print("List File [nul.map]: ");
        out.flush();
        file_list = in.nextLine();
        out.print("Grammar File [nul.ga]: ");
        out.flush();
        file_grammar = in.nextLine();

        File cFile;
        File exeFile;
        File listFile;
        File gaFile;
        if (file_c.isEmpty()) {
            file_c = fileNameWithoutExt + ".c";
            cFile = new File(inputFile.getParentFile(), file_c);
        } else {
            cFile = new File(file_c);
        }
        if (file_exe.isEmpty()) {
            file_exe = fileNameWithoutExt + ".exe";
            exeFile = new File(inputFile.getParentFile(), file_exe);
        } else {
            exeFile = new File(file_exe);
        }
        if (file_list.isEmpty()) {
            listFile = null;
        } else {
            listFile = new File(file_list);
        }
        if (file_grammar.isEmpty()) {
            gaFile = null;
        } else {
            gaFile = new File(file_grammar);
        }

        PrintStream outC, outList = null, outGa = null;
        try {
            outC = new PrintStream(new FileOutputStream(cFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        if (listFile != null) {
            try {
                outList = new PrintStream(new FileOutputStream(listFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        if (gaFile != null) {
            try {
                outGa = new PrintStream(new FileOutputStream(gaFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }

        Lexer lexer = new Lexer(fis);
        Parser parser = new Parser(lexer);
        if (outList != null) {
            lexer.setDebugTokenOutput(outList); // 输出词法单元流
        }

        try {
            Program program = parser.parse();
            if (outGa != null) {
                program.dumpTree(0, outGa); // 输出语法树
            }
            program.gen(outC); // 代码生成
        } catch (LexerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outC.close();
        if (outList != null) {
            outList.close();
        }
        if (outGa != null) {
            outGa.close();
        }

        out.println();

        // 调用 gcc 编译
        String gccCmd = String.format("gcc -Wall \"%s\" -g -o \"%s\"", cFile.getAbsolutePath(), exeFile.getAbsolutePath());
        out.println(gccCmd);
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec(gccCmd);

            //将调用结果打印到控制台上
            BufferedReader readerE = new BufferedReader(new InputStreamReader(process.getErrorStream(), "GBK"));
            String line;
            while ((line = readerE.readLine()) != null) {
                out.println(line);
            }
            in.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String ABOUT =
            "FoxTeam Tiny Compiler Version 1.0\n" +
                    "Copyright (C) FoxTeam 2014. All rights reserved\n";

    private static final String USAGE =
            "usage: java -jar tiny.jar file\n";
}
