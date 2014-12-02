package compiler.tiny.inter;

import compiler.tiny.parser.Env;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * read 语句
 */
public class Read extends Stmt {
    private final Id mId;

    public Read(Id id) {
        mId = id;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        printSpace(depth, out);
        out.printf("read:%s\n", mId.mVariable.getLexeme());
    }

    @Override
    public void gen(Env env, PrintStream out) {
        out.print("scanf(\"%d\", &");
        out.print(mId.mVariable.getLexeme());
        out.println(");");
    }
}
