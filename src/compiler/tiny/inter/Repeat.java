package compiler.tiny.inter;

import compiler.tiny.parser.Env;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * repeat 语句
 */
public class Repeat extends Stmt {
    private final Logical mLogical;
    private final Seq mStmt;

    public Repeat(Logical logicalExpr, Seq stmt) {
        mLogical = logicalExpr;
        mStmt = stmt;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        int nextDepth = depth + 2;
        printSpace(depth, out);
        out.println("repeat");
        mStmt.dumpTree(nextDepth, out);
        printSpace(depth, out);
        out.println("until");
        mLogical.dumpTree(nextDepth, out);
    }

    @Override
    public void gen(Env env, PrintStream out) {
        out.println("do {");
        mStmt.gen(env, out);
        Expr l = mLogical.gen(env, out);
        out.printf("} while(!(%s));", l.toString());
        out.println();
    }
}
