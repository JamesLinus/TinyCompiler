package compiler.tiny.inter;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 语法树顶点
 */
public class Program extends Node {
    private final Seq mSeq;

    public Program(Seq seq) {
        mSeq = seq;
    }

    @Override
    public void dumpTree(int depth, PrintStream out) {
        mSeq.dumpTree(depth, out);
    }
}
