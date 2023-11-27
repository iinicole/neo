package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Unop;
import org.genesys.utils.LibUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yufeng on 6/4/17.
 */
public class LastUnop implements Unop {

    public Object apply(Object obj) {
        if (!(obj instanceof  List)){
            // assert ((Integer)obj == 256);
            return null;
        }
        assert obj instanceof List : obj;
        List<Object> list = LibUtils.cast(obj);
        int len = list.size();
        return (len == 0) ? null : list.get(len - 1);
    }

    public String toString() {
        return "LAST";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LastUnop;
    }
}
