package compiler.tiny.parser;

import compiler.tiny.inter.*;
import compiler.tiny.lexer.*;

/**
 * Created by Noisyfox on 2014/12/1.
 */
public class Parser {

    private final Lexer mLexer;
    TokenContainer mLookhead = new TokenContainer();

    public Parser(Lexer lexer) {
        mLexer = lexer;
    }

    public Program parse() throws LexerException, ParseException {
        Seq seq;
        if (mLexer.nextToken(mLookhead)) {
            seq = seq();
        } else {
            seq = new Seq(Nop.NOP, Nop.NOP);
        }
        return new Program(seq);
    }

    /**
     * 语句序列
     *
     * @return
     */
    private Seq seq() throws ParseException, LexerException {
        checkEOF();

        Stmt left = statement();

        if (isEOF() || !match(';')) {
            return new Seq(left, Nop.NOP);
        }

        return new Seq(left, seq());
    }

    private Stmt statement() throws ParseException, LexerException {
        Stmt stmt;
        checkEOF();
        switch (mLookhead.getToken().getTag()) {
            case Token.TAG_IF:
                stmt = if_stmt();
                break;
            case Token.TAG_REPEAT:
                stmt = repeat_stmt();
                break;
            case Token.TAG_READ:
                stmt = read_stmt();
                break;
            case Token.TAG_WRITE:
                stmt = write_stmt();
                break;
            case Token.TAG_VARIABLE:
                stmt = assign_stmt();
                break;
            default:
                throw ParseException.generateException(mLookhead, "不能识别的符号 \"" + mLookhead.getToken().toString() + "\"");
        }

        return stmt;
    }

    /**
     * if 语句
     *
     * @return
     * @throws ParseException
     * @throws LexerException
     */
    private Stmt if_stmt() throws ParseException, LexerException {
        if (!match(Token.TAG_IF)) {
            // this should never happen
            throw ParseException.generateException(mLookhead, "致命错误");
        }

        Logical logicalExpr = rel();

        if (!match(Token.TAG_THEN)) {
            throw ParseException.generateException(mLookhead, "需要 then");
        }

        Seq thenStmt = seq();
        Seq elseStmt = null;

        if (match(Token.TAG_ELSE)) {
            elseStmt = seq();
        }

        if (!match(Token.TAG_END)) {
            throw ParseException.generateException(mLookhead, "需要 end");
        }

        Stmt stmt;

        if (elseStmt == null) {
            stmt = new If(logicalExpr, thenStmt);
        } else {
            stmt = new IfElse(logicalExpr, thenStmt, elseStmt);
        }

        return stmt;
    }

    /**
     * repeat 语句
     *
     * @return
     */
    private Stmt repeat_stmt() throws ParseException, LexerException {
        if (!match(Token.TAG_REPEAT)) {
            // this should never happen
            throw ParseException.generateException(mLookhead, "致命错误");
        }

        Seq stmt = seq();

        if (!match(Token.TAG_UNTIL)) {
            throw ParseException.generateException(mLookhead, "需要 until");
        }

        Logical logicalExpr = rel();

        return new Repeat(logicalExpr, stmt);
    }

    /**
     * read 语句
     *
     * @return
     * @throws ParseException
     * @throws LexerException
     */
    private Stmt read_stmt() throws ParseException, LexerException {
        if (!match(Token.TAG_READ)) {
            // this should never happen
            throw ParseException.generateException(mLookhead, "致命错误");
        }

        Stmt stmt;
        switch (mLookhead.getToken().getTag()) {
            case Token.TAG_VARIABLE:
                stmt = new Read(new Id((Word) mLookhead.getToken()));
                if (!match(Token.TAG_VARIABLE)) {
                    // this should never happen
                    throw ParseException.generateException(mLookhead, "致命错误");
                }
                break;
            default:
                throw ParseException.generateException(mLookhead, "需要一个变量");
        }

        return stmt;
    }

    /**
     * write 语句
     *
     * @return
     * @throws ParseException
     * @throws LexerException
     */
    private Stmt write_stmt() throws ParseException, LexerException {
        if (!match(Token.TAG_WRITE)) {
            // this should never happen
            throw ParseException.generateException(mLookhead, "致命错误");
        }

        Expr expr = expr();

        return new Write(expr);
    }

    private Stmt assign_stmt() throws ParseException, LexerException {
        Id left = new Id((Word) mLookhead.getToken());
        if (!match(Token.TAG_VARIABLE)) {
            // this should never happen
            throw ParseException.generateException(mLookhead, "致命错误");
        }

        if (!match(Token.TAG_ASSIGN)) {
            throw ParseException.generateException(mLookhead, "需要 :=");
        }

        Expr expr = expr();

        return new Assign(left, expr);
    }

    /**
     * 关系表达式
     *
     * @return
     */
    private Logical rel() throws ParseException, LexerException {
        checkEOF();

        Expr left = expr();
        checkEOF();
        Operator op;
        switch (mLookhead.getToken().getTag()) {
            case Token.TAG_EQUAL:
                op = Operator.OP_EQUAL;
                break;
            case Token.TAG_LESS:
                op = Operator.OP_LESS;
                break;
            default:
                throw ParseException.generateException(mLookhead, "不是一个关系表达式");
        }

        next();

        Expr right = expr();
        checkEOF();

        return new Logical(op, left, right);
    }

    /**
     * 表达式
     * 特指 加减运算
     *
     * @return
     */
    private Expr expr() throws ParseException, LexerException {
        checkEOF();

        Expr left = term();
        if (isEOF()) {
            return left;
        }

        exprLoop:
        while (true) {
            Operator op;
            switch (mLookhead.getToken().getTag()) {
                case Token.TAG_PLUS:
                    op = Operator.OP_PLUS;
                    break;
                case Token.TAG_MINUS:
                    op = Operator.OP_MINUS;
                    break;
                default:
                    break exprLoop;
            }
            next();
            left = new Arith(op, left, term());
        }

        return left;
    }

    /**
     * 乘除运算
     *
     * @return
     */
    private Expr term() throws ParseException, LexerException {
        checkEOF();

        Expr left = unary();
        if (isEOF()) {
            return left;
        }

        termLoop:
        while (true) {
            Operator op;
            switch (mLookhead.getToken().getTag()) {
                case Token.TAG_MUTL:
                    op = Operator.OP_MUTL;
                    break;
                case Token.TAG_DIV:
                    op = Operator.OP_DIV;
                    break;
                default:
                    break termLoop;
            }
            next();
            left = new Arith(op, left, unary());
        }

        return left;
    }

    /**
     * 单目运算
     * 特指负号
     *
     * @return
     */
    private Expr unary() throws ParseException, LexerException {
        checkEOF();

        switch (mLookhead.getToken().getTag()) {
            case '-':
                next();
                return new Unary(Operator.OP_MINUS, unary());
            default:
                return factor();
        }
    }

    /**
     * 表达式因子
     *
     * @return
     */
    private Expr factor() throws ParseException, LexerException {
        Expr exp;
        checkEOF();

        switch (mLookhead.getToken().getTag()) {
            case '(':
                next();
                exp = expr();
                if (!match(')')) {
                    throw ParseException.generateException(mLookhead, "需要 )");
                }
                break;
            case Token.TAG_INT:
                exp = new Constant((Int) mLookhead.getToken());
                if (!match(Token.TAG_INT)) {
                    // this should never happen
                    throw ParseException.generateException(mLookhead, "致命错误");
                }
                break;
            case Token.TAG_VARIABLE:
                exp = new Id((Word) mLookhead.getToken());
                if (!match(Token.TAG_VARIABLE)) {
                    // this should never happen
                    throw ParseException.generateException(mLookhead, "致命错误");
                }
                break;
            default:
                throw ParseException.generateException(mLookhead, "意外的符号 \"" + mLookhead.getToken().toString() + "\"");
        }
        return exp;
    }

    private boolean match(int tag) throws EOFException, LexerException {
        checkEOF();

        if (mLookhead.getToken().getTag() == tag) {
            mLexer.nextToken(mLookhead);
            return true;
        } else {
            return false;
        }
    }

    private boolean isEOF() {
        return mLookhead.getToken() == null;
    }

    private void checkEOF() throws EOFException {
        if (isEOF()) {
            throw EOFException.generateException(mLookhead);
        }
    }

    private void next() throws LexerException, EOFException {
        mLexer.nextToken(mLookhead);
        checkEOF();
    }

}
