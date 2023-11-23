package org.genesys.interpreter;

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

    public HigherUnop(Unop op) {
        this.unop = op;
        this.val = null;
        this.binop = null;
    }

    public Object apply(Object obj) {
        if (this.binop != null) {
            return this.binop.apply(obj, this.val);
        } else {
            return this.unop.apply(obj);
        }
    }

    @Override
    public String toString() {
        if (this.binop != null) {
            return "l(a).(" + this.binop + " (" + this.val.toString() + ") b)";
        }
        return this.unop.toString();
    }
}
