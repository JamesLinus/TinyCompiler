package compiler.tiny.inter;

import compiler.tiny.lexer.Word;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 标识符
 */
public class Id extends Expr {
    protected final Word mVariable;

    public Id(Word token) {
        mVariable = token;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        printSpace(depth, out);
        out.printf("id:%s\n", mVariable.getLexeme());
    }
}
