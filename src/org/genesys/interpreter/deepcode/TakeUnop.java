package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Binop;
import org.genesys.interpreter.Unop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yufeng on 5/31/17.
 */
public class TakeUnop implements Unop {

    public TakeUnop() {
    }

    public Object apply(Object obj) {
        assert obj != null;
        List pair = (List) obj;
        if (!(pair.get(0) instanceof List) || !(pair.get(1) instanceof Integer))
            return null;
        assert pair.size() == 2 : pair;
        assert pair.get(0) instanceof List;
        assert pair.get(1) instanceof Integer;
        List input1 = (List) pair.get(0);
        int input2 = (Integer) pair.get(1);
        if (input2 < 0) {
            return null;
        } else if (input2 >= input1.size()) {
            return null;
        } else {
            List res = input1.subList(0, input2);
            return res.size() == 0 ? null : res;
        }
    }

    public String toString() {
        return "TAKE";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TakeUnop;
    }
}
