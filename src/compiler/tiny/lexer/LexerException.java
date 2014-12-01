package compiler.tiny.lexer;

/**
 * Created by Noisyfox on 2014/12/1.
 */
public class LexerException extends Exception {
    public static LexerException generateException(int line, int row, String message) {
        return new LexerException(String.format("LexerError:(%d,%d):%s", line, row, message));
    }

    private LexerException(String message) {
        super(message);
    }
}
