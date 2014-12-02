package compiler.tiny.inter;

import compiler.tiny.parser.Env;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/2.
 * 临时变量符号
 */
public class Temp extends Expr {
    private final int mIndex;

    public Temp(int index) {
        mIndex = index;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {

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
        return "tmp_" + mIndex;
    }
}
