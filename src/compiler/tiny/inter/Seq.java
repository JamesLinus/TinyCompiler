package compiler.tiny.inter;

import compiler.tiny.parser.Env;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 语句序列
 */
public class Seq extends Stmt {
    private final Stmt mLeft, mRight;

    public Seq(Stmt left, Stmt right) {
        mLeft = left;
        mRight = right;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        mLeft.dumpTree(depth, out);
        mRight.dumpTree(depth, out);
    }

    @Override
    public void gen(Env env, PrintStream out) {
        mLeft.gen(env, out);
        mRight.gen(env, out);
    }
}
