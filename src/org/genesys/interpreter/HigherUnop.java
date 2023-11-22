package org.genesys.interpreter;

/**
 * Created by yufeng on 5/31/17.
 */
public class HigherUnop implements Unop {
    private final Binop op;
    private final Integer val;

    public HigherUnop(Binop op, Integer val) {
        this.op = op;
        this.val = val;
    }

    public Object apply(Object obj) {
        return this.op.apply(obj, this.val);
    }

    @Override
    public String toString() {
        return "l(a).(" + this.op + " (" + this.val.toString() + ") b)";
    }
}
