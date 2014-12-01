package compiler.tiny.inter;

import compiler.tiny.lexer.Operator;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 单目运算
 */
public class Unary extends Operation {
    private final Operator mOperator;
    private final Expr mLeft;

    public Unary(Operator op, Expr expr) {
        mOperator = op;
        mLeft = expr;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        printSpace(depth, out);
        out.printf("op:%s\n", mOperator.getOperator());
        mLeft.dumpTree(depth + 2, out);
    }
}
