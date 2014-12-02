package compiler.tiny.inter;

import compiler.tiny.parser.Env;

import java.io.PrintStream;

/**
 * Created by Noisyfox on 2014/12/1.
 * 运算类表达式
 */
public abstract class Operation extends Expr {
    @Override
    protected Expr reduce(Env env, PrintStream out) {
        Expr expr = gen(env, out);
        env.tmpPush();
        Temp temp = env.obtainTmp();
        out.printf("%s = %s;\n", temp.toString(), expr.toString());
        env.tmpPop();
        return temp;
    }
}
