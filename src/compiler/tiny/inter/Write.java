package compiler.tiny.inter;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * write 语句
 */
public class Write extends Stmt {
    private final Expr mExpr;

    public Write(Expr expr) {
        mExpr = expr;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        printSpace(depth, out);
        out.println("write");
        mExpr.dumpTree(depth + 2, out);
    }
}
