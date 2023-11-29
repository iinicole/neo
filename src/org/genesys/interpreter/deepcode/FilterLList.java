package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Unop;
import org.genesys.interpreter.Binop;
import org.genesys.type.AbstractList;
import org.genesys.type.Cons;
import org.genesys.type.EmptyList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yufeng on 5/31/17.
 */
public class FilterLList implements Unop {
    private final Binop op;

    private Integer rhs;

    public FilterLList(Binop op, int l) {
        this.op = op;
        this.rhs = l;
    }

    public Object apply(Object obj) {
        if (obj instanceof  Integer){
            // assert ((Integer)obj == 256);
            return null;
        }
        List list = (List)  obj;
        if (list.isEmpty()) {
            return null;
        } else {
            List targetList = new ArrayList<>();
            for (Object elem : list) {
                if (!(elem instanceof Integer)) {
                    return null;
                }
                if (op.toString().equals("l(a,b).(< a b)") && (Integer)elem < rhs) {
                    targetList.add(elem);
                } else if (op.toString().equals("l(a,b).(> a b)") && (Integer)elem > rhs) {
                    targetList.add(elem);
                } else if (op.toString().equals("l(a,b).(== a b)") && (Integer)elem == rhs) {
                    targetList.add(elem);
                } else if (op.toString().equals("l(a,b).(!= a b)") && (Integer)elem != rhs) {
                    targetList.add(elem);
                } else if (op.toString().equals("l(a,b).(%= a b)")){
                    if (rhs == 0)
                        return null;
                    else if((Integer)elem % rhs == 0){
                        targetList.add(elem);
                    }
                } else if (op.toString().equals("l(a,b).(%!= a b)")){
                    if (rhs == 0)
                        return null;
                    else if((Integer)elem % rhs != 0){
                        targetList.add(elem);
                    }
                }
            }
            assert targetList.size() <= list.size();
            return targetList;
        }
    }

    public String toString() {
        return "FILTER";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FilterLList && this.op.equals(((FilterLList) obj).op) && this.rhs.equals(((FilterLList) obj).rhs);
    }
}
