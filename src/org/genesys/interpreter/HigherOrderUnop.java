package org.genesys.interpreter;

import org.genesys.interpreter.deepcode.HeadUnop;

/**
 * Created by yufeng on 5/31/17.
 */
public class HigherOrderUnop implements Unop {

    private final Unop unop;

    public HigherOrderUnop(Unop unop) {
        this.unop = unop;
    }

    public Object apply(Object obj) {
        return this.unop.apply(obj);
    }

    @Override
    public String toString() {
        return this.unop.toString();
    }
}
