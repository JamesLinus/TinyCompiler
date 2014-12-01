package compiler.tiny.lexer;

/**
 * Created by Noisyfox on 2014/11/30.
 * Token 容器，主要目的是将一个词元Token和它对应的行、列相关联起来
 */
public final class TokenContainer {

    private Token mToken = null;
    private int mLine = 0;
    private int mRow = 0;

    protected void putToken(Token token, int line, int row) {
        mToken = token;
        mLine = line;
        mRow = row;
    }

    public Token getToken() {
        return mToken;
    }

    public int getLine() {
        return mLine;
    }

    public int getRow() {
        return mRow;
    }

    @Override
    public String toString() {
        return mLine + ":" + mRow + ":" + mToken.toString();
    }
}
