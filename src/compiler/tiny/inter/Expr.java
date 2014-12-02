package compiler.tiny.inter;

import compiler.tiny.parser.Env;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 表达式节点
 */
public abstract class Expr extends Node {
    /**
     * 将表达式规约成一个地址
     *
     * @return
     */
    protected abstract Expr reduce(Env env, PrintStream out);

    /**
     * 转换成一个基础操作表达式
     *
     * @return
     */
    protected abstract Expr gen(Env env, PrintStream out);
}
