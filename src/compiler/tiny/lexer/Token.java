package compiler.tiny.lexer;

/**
 * Created by Noisyfox on 2014/11/30.
 * 词法单元
 */
public class Token {
    public static final int TAG_NORMAL = 256;
    public static final int TAG_INT = 257; // 整数常量
    public static final int TAG_IF = 258; // if
    public static final int TAG_THEN = 259; // then
    public static final int TAG_ELSE = 260; // else
    public static final int TAG_END = 261; // end
    public static final int TAG_REPEAT = 262; // repeat
    public static final int TAG_UNTIL = 263; // until
    public static final int TAG_READ = 264; // read
    public static final int TAG_WRITE = 265; // write
    public static final int TAG_ASSIGN = 266; // :=  赋值符号
    public static final int TAG_PLUS = 267; // +  加号
    public static final int TAG_MINUS = 268; // -  减号
    public static final int TAG_MUTL = 269; // *  乘号
    public static final int TAG_DIV = 270; // /  除号
    public static final int TAG_EQUAL = 271; // =  相等
    public static final int TAG_LESS = 272; // <  小于
    public static final int TAG_VARIABLE = 273; // 变量

    public static boolean isReservedWord(Token token) {
        int tag = token.mTag;
        return tag >= TAG_IF && tag <= TAG_WRITE;
    }

    final int mTag;

    public Token(int tag) {
        mTag = tag;
    }

    public final int getTag() {
        return mTag;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Token) {
            Token t = (Token) obj;
            return mTag == t.mTag;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return mTag;
    }

    @Override
    public String toString() {
        return "symbol:" + (char) mTag;
    }
}
