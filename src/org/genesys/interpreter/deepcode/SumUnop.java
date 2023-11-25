package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Unop;
import org.genesys.utils.LibUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yufeng on 6/4/17.
 */
public class SumUnop implements Unop {

    public Object apply(Object obj) {
        if (obj instanceof  Integer){
            // assert ((Integer)obj == 256);
            return null;
        }
        assert obj instanceof List : obj;
        List<Integer> list = LibUtils.cast(obj);
        if (((List)obj).isEmpty()) return null;
        if (((List)obj).get(0) instanceof List) return null;
        Optional<Integer> sum = list.stream().reduce(Integer::sum);
        return sum.get();
    }

    public String toString() {
        return "SUM";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SumUnop;
    }
}
