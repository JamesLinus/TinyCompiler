package compiler.tiny.lexer;

/**
 * Created by Noisyfox on 2014/11/30.
 * 整型常量
 */
public class Int extends Token {
    final int mValue;

    public Int(int value) {
        super(TAG_INT);
        mValue = value;
    }

    public final int getValue() {
        return mValue;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + mValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Int) {
            Int i = (Int) obj;
            return mValue == i.mValue;
        }

        return false;
    }

    @Override
    public String toString() {
        return "integer:" + mValue;
    }
}
