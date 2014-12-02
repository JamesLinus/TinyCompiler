package compiler.tiny.inter;

import compiler.tiny.parser.Env;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 语句节点
 */
public abstract class Stmt extends Node {
    /**
     * 生成代码
     */
    public abstract void gen(Env env, PrintStream out);
}
