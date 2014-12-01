package compiler.tiny.lexer;

import java.util.HashMap;

/**
 * Created by Noisyfox on 2014/11/30.
 * 标识符
 */
public class Word extends Token {
    public static final Word WORD_IF = new Word(TAG_IF, "if");
    public static final Word WORD_THEN = new Word(TAG_THEN, "then");
    public static final Word WORD_ELSE = new Word(TAG_ELSE, "else");
    public static final Word WORD_END = new Word(TAG_END, "end");
    public static final Word WORD_REPEAT = new Word(TAG_REPEAT, "repeat");
    public static final Word WORD_UNTIL = new Word(TAG_UNTIL, "until");
    public static final Word WORD_READ = new Word(TAG_READ, "read");
    public static final Word WORD_WRITE = new Word(TAG_WRITE, "write");

    private static final HashMap<String, Word> WORDS_RESERVED = new HashMap<String, Word>();

    static {
        WORDS_RESERVED.put(WORD_IF.mLexeme, WORD_IF);
        WORDS_RESERVED.put(WORD_THEN.mLexeme, WORD_THEN);
        WORDS_RESERVED.put(WORD_ELSE.mLexeme, WORD_ELSE);
        WORDS_RESERVED.put(WORD_END.mLexeme, WORD_END);
        WORDS_RESERVED.put(WORD_REPEAT.mLexeme, WORD_REPEAT);
        WORDS_RESERVED.put(WORD_UNTIL.mLexeme, WORD_UNTIL);
        WORDS_RESERVED.put(WORD_READ.mLexeme, WORD_READ);
        WORDS_RESERVED.put(WORD_WRITE.mLexeme, WORD_WRITE);
    }

    public static Word getReserve(String lexeme) {
        return WORDS_RESERVED.get(lexeme);
    }

    final String mLexeme;

    public Word(int tag, String lexeme) {
        super(tag);
        mLexeme = lexeme;
    }

    public final String getLexeme() {
        return mLexeme;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + mLexeme.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Word) {
            Word w = (Word) obj;
            return w.mTag == mTag && w.mLexeme.equals(mLexeme);
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isReservedWord(this)) {
            sb.append("reserved word:");
        } else {
            sb.append("ID,name=");
        }
        sb.append(mLexeme);

        return sb.toString();
    }
}
