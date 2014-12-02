package compiler.tiny.lexer;

/**
 * Created by Noisyfox on 2014/11/30.
 * 运算符
 */
public class Operator extends Token {
    public static final Operator OP_ASSIGN = new Operator(TAG_ASSIGN, ":=", "=");
    public static final Operator OP_PLUS = new Operator(TAG_PLUS, "+", "+");
    public static final Operator OP_MINUS = new Operator(TAG_MINUS, "-", "-");
    public static final Operator OP_MUTL = new Operator(TAG_MUTL, "*", "*");
    public static final Operator OP_DIV = new Operator(TAG_DIV, "/", "/");
    public static final Operator OP_EQUAL = new Operator(TAG_EQUAL, "=", "==");
    public static final Operator OP_LESS = new Operator(TAG_LESS, "<", "<");

    final String mOperator;
    final String mCOperator;

    private Operator(int tag, String op, String cop) {
        super(tag);
        mOperator = op;
        mCOperator = cop;
    }

    public final String getOperator() {
        return mOperator;
    }

    public final String getCOperator() {
        return mCOperator;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Operator) {
            Operator op = (Operator) obj;
            return mTag == op.mTag && mOperator.equals(op.mOperator);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + mOperator.hashCode();
    }

    @Override
    public String toString() {
        return "operator:" + mOperator;
    }
}
