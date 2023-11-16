package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Unop;
import org.genesys.utils.LibUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicole on 11/15/23.
 */
public class GroupUnop implements Unop {

    public Object apply(Object obj) {
        if (obj instanceof  Integer){
            assert ((Integer)obj == 256);
            return new ArrayList<>();
        }
        assert obj instanceof List;
        List<Integer> list = LibUtils.cast(obj);
        if (list.isEmpty()) return 256;
        // group same element together and form new list of list
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        cur.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).equals(list.get(i - 1))) {
                cur.add(list.get(i));
            } else {
                result.add(cur);
                cur = new ArrayList<>();
                cur.add(list.get(i));
            }
        }
        result.add(cur);
        return result;
    }

    public String toString() {
        return "GROUP";
    }
}
