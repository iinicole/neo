package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Unop;
import org.genesys.utils.LibUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yufeng on 6/4/17.
 */
public class MaximumUnop implements Unop {

    public Object apply(Object obj) {
        if (obj instanceof  Integer){
            //  assert ((Integer)obj == 256);
            return null;
        }
        assert obj instanceof List : obj;
        if (((List)obj).isEmpty()) return null;
        if (((List)obj).get(0) instanceof List) return null;
        List<Integer> list = LibUtils.cast(obj);
        if (list.isEmpty()) return null;
        Optional<Integer> max = list.stream().reduce(Integer::max);
        return max.get();
    }

    public String toString() {
        return "MAXIMUM";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MaximumUnop;
    }
}
