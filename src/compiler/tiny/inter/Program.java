package compiler.tiny.inter;

import compiler.tiny.parser.Env;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 语法树顶点
 */
public class Program extends Node {
    private final Env mEnv;
    private final Seq mSeq;

    public Program(Env env, Seq seq) {
        mEnv = env;
        mSeq = seq;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        mSeq.dumpTree(depth, out);
    }

    public void gen(PrintStream out) {

        out.println("#include <stdio.h>");
        out.println();
        out.println("int main(){");
        // 然后产生语句
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        PrintStream o = new PrintStream(byteArrayOutputStream);
        // 先生成中间代码到内存缓冲，用来预先确定要用到的临时变量
        mSeq.gen(mEnv, o);
        // 声明用到的变量和临时符号
        out.println(mEnv.toString());
        // 再输出代码
        try {
            byteArrayOutputStream.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("return 0;");
        out.println("}");
    }
}
