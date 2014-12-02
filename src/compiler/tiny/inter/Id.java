package compiler.tiny.inter;

import compiler.tiny.lexer.Word;
import compiler.tiny.parser.Env;

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
        out.printf("id:%s", mVariable.getLexeme());
        out.println();
    }

    @Override
    protected Expr reduce(Env env, PrintStream out) {
        return this;
    }

    @Override
    protected Expr gen(Env env, PrintStream out) {
        return this;
    }

    @Override
    public String toString() {
        return mVariable.getLexeme();
    }
}
