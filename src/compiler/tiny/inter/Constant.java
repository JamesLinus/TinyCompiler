package compiler.tiny.inter;

import compiler.tiny.lexer.Int;
import compiler.tiny.parser.Env;

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
        out.printf("const:%d", mInt.getValue());
        out.println();
    }

    @Override
    protected Expr reduce(Env env, PrintStream out) {
        return this;
    }

    @Override
    protected Expr gen(Env env, PrintStream out) {
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(mInt.getValue());
    }
}
