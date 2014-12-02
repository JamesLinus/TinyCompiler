package compiler.tiny.parser;

import compiler.tiny.inter.Id;
import compiler.tiny.inter.Temp;
import compiler.tiny.lexer.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Noisyfox on 2014/12/2.
 * 符号表
 */
public class Env {
    private final HashMap<Word, Id> mSymbols = new HashMap<Word, Id>();

    public Id putVariable(Word word, Id id) {
        Id exist = mSymbols.get(word);
        if (exist == null) {
            mSymbols.put(word, id);
            exist = id;
        }

        return exist;
    }

    public Id getVariable(Word word) {
        return mSymbols.get(word);
    }

    private int mTempMax = 0;
    private final ArrayList<Temp> mTemps = new ArrayList<Temp>();
    private final Stack<Integer> mTempStack = new Stack<Integer>();

    public void tmpPush() {
        mTempStack.push(mTempMax);
    }

    public void tmpPop() {
        mTempMax = mTempStack.pop();
    }

    public Temp obtainTmp() {
        while (mTempMax >= mTemps.size()) {
            mTemps.add(new Temp(mTemps.size()));
        }

        Temp t = mTemps.get(mTempMax);
        mTempMax++;
        return t;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("int ");
        boolean first = true;
        for (Map.Entry<Word, Id> wordIdEntry : mSymbols.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(wordIdEntry.getValue().toString());
        }
        for (Temp temp : mTemps) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(temp.toString());
        }
        sb.append(";");
        return sb.toString();
    }
}
