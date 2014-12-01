package compiler.tiny.inter;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 空语句
 */
public class Nop extends Stmt {
    public static final Nop NOP = new Nop();

    private Nop() {

    }

    @Override
    public void dumpTree(int depth, PrintStream out) {

    }
}
