package compiler.tiny.inter;

import compiler.tiny.lexer.Operator;
import compiler.tiny.parser.Env;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 逻辑表达式
 */
public class Logical extends Expr {
    private final Operator mOperator;
    private final Expr mLeft, mRight;

    public Logical(Operator op, Expr left, Expr right) {
        mOperator = op;
        mLeft = left;
        mRight = right;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        printSpace(depth, out);
        out.printf("op:%s", mOperator.getOperator());
        out.println();
        int nextDepth = depth + 2;
        mLeft.dumpTree(nextDepth, out);
        mRight.dumpTree(nextDepth, out);
    }

    @Override
    protected Expr reduce(Env env, PrintStream out) {
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", mLeft.toString(), mOperator.getCOperator(), mRight.toString());
    }

    @Override
    protected Expr gen(Env env, PrintStream out) {
        return new Logical(mOperator, mLeft.reduce(env, out), mRight.reduce(env, out));
    }
}
