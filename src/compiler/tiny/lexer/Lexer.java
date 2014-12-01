package compiler.tiny.lexer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Noisyfox on 2014/11/30.
 * 词法分析器
 */
public class Lexer {

    private final InputStream mInput;
    private boolean mEndOfInput = false;
    private char mPeek = 0;
    private int mRow = 0;
    private int mLine = 1;
    private int mPutBack = -1; // 回放字符

    public Lexer(InputStream input) {
        mInput = input;
    }

    /**
     * 获取下一个词元
     *
     * @param container
     * @return
     */
    public boolean nextToken(TokenContainer container) throws LexerException {
        // 1.剔除空格和注释
        boolean isComment = false;
        spaceLoop:
        while (true) {
            peekNext();
            if (mEndOfInput) {
                if (isComment) {
                    throw LexerException.generateException(mLine, mRow, "意外的输入结尾：注释未结束");
                }
                container.putToken(null, mLine, mRow);
                return false;
            }
            switch (mPeek) {
                case ' ':
                case '\t':
                    break;
                case '\n':
                    mLine++;
                    mRow = 0;
                    break;
                case '{':
                    if (isComment) {
                        throw LexerException.generateException(mLine, mRow, "意外的输入字符：嵌套的注释");
                    } else {
                        isComment = true;
                    }
                    break;
                case '}':
                    if (isComment) {
                        isComment = false;
                    } else {
                        throw LexerException.generateException(mLine, mRow, "意外的输入字符：不是注释");
                    }
                    break;
                default:
                    if (!isComment) {
                        break spaceLoop;
                    }
            }
        }

        // 2.识别复合词法符号
        switch (mPeek) {
            case ':':
                if (!checkNext('=')) {
                    container.putToken(new Token(':'), mLine, mRow);
                    return true;
                } else {
                    container.putToken(Operator.OP_ASSIGN, mLine, mRow - 1);
                    return true;
                }
        }

        // 3.识别符号
        switch (mPeek) {
            case '+':
                container.putToken(Operator.OP_PLUS, mLine, mRow);
                return true;
            case '-':
                container.putToken(Operator.OP_MINUS, mLine, mRow);
                return true;
            case '*':
                container.putToken(Operator.OP_MUTL, mLine, mRow);
                return true;
            case '/':
                container.putToken(Operator.OP_DIV, mLine, mRow);
                return true;
            case '=':
                container.putToken(Operator.OP_EQUAL, mLine, mRow);
                return true;
            case '<':
                container.putToken(Operator.OP_LESS, mLine, mRow);
                return true;
        }

        // 4.识别数字常量
        if (Character.isDigit(mPeek)) {
            int startRow = mRow;

            int v = Character.digit(mPeek, 10);
            while (true) {
                peekNext();
                if (mEndOfInput) {
                    break;
                }
                if (!Character.isDigit(mPeek)) {
                    putBack(mPeek);
                    break;
                }
                v = v * 10 + Character.digit(mPeek, 10);
            }

            container.putToken(new Int(v), mLine, startRow);
            return true;
        }

        // 5.识别标识符
        if (Character.isLetter(mPeek)) {
            int startRow = mRow;

            StringBuilder sb = new StringBuilder();
            sb.append(mPeek);
            while (true) {
                peekNext();
                if (mEndOfInput) {
                    break;
                }
                if (!Character.isLetterOrDigit(mPeek)) {
                    putBack(mPeek);
                    break;
                }
                sb.append(mPeek);
            }
            String lexeme = sb.toString();

            Word w = Word.getReserve(lexeme); // 检查是否是保留字
            if (w == null) {
                w = new Word(Token.TAG_VARIABLE, lexeme); // 否则是变量
            }

            container.putToken(w, mLine, startRow);
            return true;
        }

        // 6.剩下的情况，直接将单字作为词法单元
        container.putToken(new Token(mPeek), mLine, mRow);

        return true;
    }

    /**
     * 从输入串中读取下一个字符
     */
    private void peekNext() {
        if (mPutBack != -1) {
            mPeek = (char) mPutBack;
            mPutBack = -1;
            mRow++;
            mEndOfInput = false;
            return;
        }

        try {
            int c = mInput.read();
            if (c == -1) {
                mEndOfInput = true;
                return;
            }
            mRow++;
            mPeek = (char) c;
            mEndOfInput = false;
        } catch (IOException e) {
            e.printStackTrace();
            mEndOfInput = true;
        }
    }

    private boolean checkNext(char c) {
        peekNext();
        if (mEndOfInput) {
            return false;
        }
        if (mPeek == c) {
            return true;
        }
        putBack(mPeek);

        return false;
    }

    private void putBack(char c) {
        if (mPutBack == -1) {
            mPutBack = c;
            mRow--;
        } else {
            // error
        }
    }
}
