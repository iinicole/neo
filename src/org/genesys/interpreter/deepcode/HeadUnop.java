package org.genesys.interpreter.deepcode;

import org.genesys.interpreter.Unop;
import org.genesys.utils.LibUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yufeng on 6/4/17.
 */
public class HeadUnop implements Unop {

    public Object apply(Object obj) {
        if (!(obj instanceof List)){
            //  assert ((Integer)obj == 256);
            return null;
        }
        assert obj instanceof List : obj;
        List<Object> list = LibUtils.cast(obj);
        return list.isEmpty() ? null : list.get(0);
    }

    public String toString() {
        return "HEAD";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof HeadUnop;
    }
}
