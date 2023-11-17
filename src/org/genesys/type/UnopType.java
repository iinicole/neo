package org.genesys.type;

/**
 * Created by yufeng on 9/15/17.
 */
public class UnopType implements AbstractType {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnopType;
    }

    @Override
    public int hashCode() {
        return 51;
    }

    @Override
    public String toString() {
        return "UnopType";
    }
}
