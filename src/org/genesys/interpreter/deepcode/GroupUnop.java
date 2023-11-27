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
        if (!(obj instanceof  List)){
            //  assert ((Integer)obj == 256);
            return null;
        }
        assert obj instanceof List;
        List<Object> list = LibUtils.cast(obj);
        if (list.isEmpty()) return 256;
        // group same element together and form new list of list
        List<List<Object>> result = new ArrayList<>();
        List<Object> cur = new ArrayList<>();
        cur.add(list.get(0));
        if (list.get(0) == null) {
            return null;
        }
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).equals(list.get(i - 1))) {
                cur.add(list.get(i));
            } else {
                result.add(cur);
                cur = new ArrayList<>();
                cur.add(list.get(i));
                if (list.get(i) == null) {
                    return null;
                }
            }
        }
        result.add(cur);
        return result;
    }

    public String toString() {
        return "GROUP";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GroupUnop;
    }
}
