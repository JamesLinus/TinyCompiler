package compiler.tiny.inter;

import compiler.tiny.lexer.Operator;

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
        out.printf("op:%s\n", mOperator.getOperator());
        int nextDepth = depth + 2;
        mLeft.dumpTree(nextDepth, out);
        mRight.dumpTree(nextDepth, out);
    }
}
