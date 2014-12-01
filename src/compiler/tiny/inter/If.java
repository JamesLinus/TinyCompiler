package compiler.tiny.inter;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * if 语句
 */
public class If extends Stmt {
    private final Logical mLogical;
    private final Seq mThenStmt;

    public If(Logical logicalExpr, Seq thenStmt) {
        mLogical = logicalExpr;
        mThenStmt = thenStmt;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        int nextDepth = depth + 2;
        printSpace(depth, out);
        out.println("if");
        mLogical.dumpTree(nextDepth, out);
        printSpace(depth, out);
        out.println("then");
        mThenStmt.dumpTree(nextDepth, out);
        printSpace(depth, out);
        out.println("end");
    }
}
