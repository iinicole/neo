package org.genesys.type;

import java.util.List;

/**
 * Created by yufeng on 5/31/17.
 */
public class FunctionType implements AbstractType {
    public final List<AbstractType> inputTypes;
    public final AbstractType outputType;

    public FunctionType(List<AbstractType> inputTypes, AbstractType outputType) {
        this.inputTypes = inputTypes;
        this.outputType = outputType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FunctionType)) {
            return false;
        }
        FunctionType type = (FunctionType) obj;
        return this.inputTypes.equals(type.inputTypes) && this.outputType.equals(type.outputType);
    }

    @Override
    public int hashCode() {
        return 5 * (5 * this.inputTypes.hashCode() + this.outputType.hashCode()) + 3;
    }

    @Override
    public String toString() {
        return "(" + this.inputTypes.toString() + " -> " + this.outputType.toString() + ")";
    }
}
