package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Binop;

/**
 * Created by yufeng on 5/31/17.
 */
public class MaxBinop implements Binop {

    public Object apply(Object first, Object second) {
        if (!(first instanceof Integer) || !(second instanceof Integer)) {
            return null;
        }
        int i1 = (int) first;
        int i2 = (int) second;
        return i1 > i2 ? i1 : i2;
    }

    @Override
    public String toString() {
        return "MAX";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MaxBinop;
    }
}
