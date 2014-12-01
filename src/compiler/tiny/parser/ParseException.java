package compiler.tiny.parser;

import compiler.tiny.lexer.TokenContainer;

/**
 * Created by Noisyfox on 2014/12/1.
 */
public class ParseException extends Exception {
    public static ParseException generateException(int line, int row, String message) {
        return new ParseException(String.format("ParseError:(%d,%d):%s", line, row, message));
    }

    public static ParseException generateException(TokenContainer container, String message) {
        return generateException(container.getLine(), container.getRow(), message);
    }

    protected ParseException(String message) {
        super(message);
    }
}
