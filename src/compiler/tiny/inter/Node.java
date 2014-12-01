package compiler.tiny.inter;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 语法树节点
 */
public abstract class Node {
    public abstract void dumpTree(int depth, PrintStream out);

    protected static void printSpace(int depth, PrintStream out) {
        for (int i = 0; i < depth; i++) {
            out.print(' ');
        }
    }
}
