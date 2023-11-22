package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Unop;
import org.genesys.utils.LibUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by yufeng on 6/4/17.
 */
public class SortUnop implements Unop {

    public Object apply(Object obj) {
        if (obj instanceof  Integer){
            // assert ((Integer)obj == 256);
            return null;
        }
        assert obj instanceof List : obj;
        if (((List)obj).isEmpty()) return null;
        List list = LibUtils.cast(obj);
        // Make a deep copy
        List sorted = new ArrayList<>();
        for (Object i : list)
            sorted.add(i);
        Collections.sort(sorted);
        return sorted;
    }

    public String toString() {
        return "SORT";
    }
}
