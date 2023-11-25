package org.genesys.interpreter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yufeng on 5/31/17.
 */
public class HigherUnop implements Unop {
    private final Binop binop;
    private final Unop unop;
    private final Integer val;

    public HigherUnop(Binop op, Integer val) {
        this.binop = op;
        this.val = val;
        this.unop = null;
    }

    public HigherUnop(Unop op, Integer val) {
        this.unop = op;
        this.val = val;
        this.binop = null;
    }

    public Object apply(Object obj) {
        if (this.binop != null) {
            return this.binop.apply(obj, this.val);
        } else {
            return this.unop.apply(new ArrayList<Object>(Arrays.asList(obj, this.val)));
        }
    }

    @Override
    public String toString() {
        if (this.binop != null) {
            return "l(a).(" + this.binop + " (" + this.val.toString() + ") b)";
        }
        return this.unop.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof HigherUnop && this.binop.equals(((HigherUnop) obj).binop) && this.unop.equals(((HigherUnop) obj).unop) && this.val.equals(((HigherUnop) obj).val);
    }
}
