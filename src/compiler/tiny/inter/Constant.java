package compiler.tiny.inter;

import compiler.tiny.lexer.Int;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 常量
 */
public class Constant extends Expr {
    private final Int mInt;

    public Constant(Int token) {
        mInt = token;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        printSpace(depth, out);
        out.printf("const:%d\n", mInt.getValue());
    }
}
