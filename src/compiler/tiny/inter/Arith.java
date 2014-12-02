package compiler.tiny.inter;

import compiler.tiny.lexer.Operator;
import compiler.tiny.parser.Env;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 双目运算
 */
public class Arith extends Operation {
    private final Operator mOperator;
    private final Expr mLeft, mRight;

    public Arith(Operator op, Expr left, Expr right) {
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
    public String toString() {
        return String.format("%s %s %s", mLeft.toString(), mOperator.getCOperator(), mRight.toString());
    }

    @Override
    protected Expr gen(Env env, PrintStream out) {
        return new Arith(mOperator, mLeft.reduce(env, out), mRight.reduce(env, out));
    }
}
