package compiler.tiny.inter;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 赋值语句
 */
public class Assign extends Stmt {
    private final Id mLeft;
    private final Expr mExpr;

    public Assign(Id left, Expr expr) {
        mLeft = left;
        mExpr = expr;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        printSpace(depth, out);
        out.printf("assign to:%s\n", mLeft.mVariable.getLexeme());
        mExpr.dumpTree(depth + 2, out);
    }
}
