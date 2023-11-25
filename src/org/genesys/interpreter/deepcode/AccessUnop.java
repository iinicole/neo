package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Binop;
import org.genesys.interpreter.Unop;

import java.util.List;

/**
 * Created by yufeng on 9/7/17.
 */
public class AccessUnop implements Unop {

    private Integer n;

    public AccessUnop(int n) {
        this.n = n;
    }

    public AccessUnop() {
        this.n = null;
    }

    public Object apply(Object obj) {
        if (n != null) {
            return access(obj, n);
        }
        
        if (!(obj instanceof List))
            return null;
        List pair = (List) obj;
        if (pair.size() != 2)
            return null;
        assert pair.size() == 2 : pair;
        if (!(pair.get(0) instanceof List) || !(pair.get(1) instanceof Integer))
            return null;
        assert pair.get(0) instanceof List;
        assert pair.get(1) instanceof Integer;
        List xs = (List) pair.get(0);
        int n = (Integer) pair.get(1);
        return access(xs, n);
    }

    private Object access(Object obj, int idx) {
        if (!(obj instanceof List))
            return null;
        List xs = (List) obj;
        if (idx < 0)
            return null;
        else if (xs.size() > idx) {
            return xs.get(idx);
        }
        return null;
    }

    public String toString() {
        return "ACCESS";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AccessUnop;
    }
}
