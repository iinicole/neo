package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Unop;
import org.genesys.interpreter.Binop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yufeng on 5/31/17.
 */
public class CountList implements Unop {
    private final Binop op;
    private int rhs;

    public CountList(Binop unop, int l) {
        this.op = unop;
        this.rhs = l;
    }

    public Object apply(Object obj) {
        if (obj instanceof  Integer){
            // assert ((Integer)obj == 256);
            return null;
        }
        List list = (List) obj;
        int cnt = 0;
        if (list.isEmpty()) {
            return cnt;
        } else {
            for (Object elem : list) {
                if (!(elem instanceof Integer)) {
                    return null;
                }
                if (op.toString().equals("l(a,b).(%!= a b)") || op.toString().equals("l(a,b).(%= a b)")) {
                    if (rhs == 0)
                        return null;
                }
                if (op.toString().equals("l(a,b).(< a b)") && (Integer)elem < rhs) {
                    cnt++;
                } else if (op.toString().equals("l(a,b).(> a b)") && (Integer)elem > rhs) {
                    cnt++;
                } else if (op.toString().equals("l(a,b).(== a b)") && (Integer)elem == rhs) {
                    cnt++;
                } else if (op.toString().equals("l(a,b).(!= a b)") && (Integer)elem != rhs) {
                    cnt++;
                } else if (op.toString().equals("l(a,b).(%= a b)") && (Integer)elem % rhs == 0) {
                    cnt++;
                } else if (op.toString().equals("l(a,b).(%!= a b)") && (Integer)elem % rhs != 0) {
                    cnt++;
                }
            }
            return cnt;
        }
    }

    public String toString() {
        return "COUNT";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CountList && this.op.equals(((CountList) obj).op) && this.rhs == ((CountList) obj).rhs;
    }
}
