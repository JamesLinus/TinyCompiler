package compiler.tiny;

import compiler.tiny.inter.Program;
import compiler.tiny.lexer.Lexer;
import compiler.tiny.lexer.LexerException;
import compiler.tiny.lexer.TokenContainer;
import compiler.tiny.parser.ParseException;
import compiler.tiny.parser.Parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Noisyfox on 2014/12/1.
 */
public class Main {
    private static final String testInput =
            "{  Sample Program in TINY\n" +
                    "   －computes factorial            }\n" +
                    "read x;   {input an integer}\n" +
                    "if 0<x then {don’t do if x<=0 }\n" +
                    "  fact := 1;\n" +
                    "  repeat\n" +
                    "    fact := fact * x ;\n" +
                    "    x := x-1\n" +
                    "  until x=0;\n" +
                    "  write fact { output factorial of x }\n" +
                    "end\n";

    public static void main(String args[]) {
        InputStream in = new ByteArrayInputStream(testInput.getBytes());

        TokenContainer container = new TokenContainer();
        Lexer lexer = new Lexer(in);
        Parser parser = new Parser(lexer);

        /*
        try {
            while (lexer.nextToken(container)) {
                System.out.println(container.toString());
            }
            System.out.println(container.getLine() + ":EOF");
        } catch (LexerException e) {
            e.printStackTrace();
        }
        */
        try {
            Program program = parser.parse();
            program.dumpTree(0, System.out);
            program.gen(System.out);
        } catch (LexerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
