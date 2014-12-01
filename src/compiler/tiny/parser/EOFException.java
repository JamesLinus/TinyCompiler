package compiler.tiny.parser;

import compiler.tiny.lexer.TokenContainer;

/**
 * Created by Noisyfox on 2014/12/1.
 */
public class EOFException extends ParseException {
    public static EOFException generateException(int line, int row) {
        return new EOFException(String.format("ParseError:(%d,%d):意外的文件结尾", line, row));
    }

    public static EOFException generateException(TokenContainer container) {
        return generateException(container.getLine(), container.getRow());
    }

    private EOFException(String message) {
        super(message);
    }
}
