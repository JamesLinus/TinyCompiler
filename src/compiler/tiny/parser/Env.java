package compiler.tiny.parser;

import compiler.tiny.inter.Id;
import compiler.tiny.lexer.Word;

import java.util.HashMap;

/**
 * Created by Noisyfox on 2014/12/2.
 * 符号表
 */
public class Env {
    private final HashMap<Word, Id> mSymbols = new HashMap<Word, Id>();

    public Id put(Word word, Id id) {
        Id exist = mSymbols.get(word);
        if (exist == null) {
            mSymbols.put(word, id);
            exist = id;
        }

        return exist;
    }

    public Id get(Word word) {
        return mSymbols.get(word);
    }
}
