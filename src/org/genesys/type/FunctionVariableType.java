package org.genesys.type;

import java.util.List;

/**
 * Created by yufeng on 5/31/17.
 */
public class FunctionVariableType implements AbstractType {
    public final List<List<AbstractType>> types;

    public FunctionVariableType(List<List<AbstractType>> types) {
        assert types.isEmpty() == false;
        this.types = types;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FunctionVariableType)) {
            return false;
        }

        // if (obj instanceof FunctionType) {
        //     FunctionType type = (FunctionType) obj;
        //     // if any one pair matches, then return true
        //     for (int i = 0; i < this.types.size(); i++) {
        //         // get input types as sublist
        //         List<AbstractType> inputTypes = this.types.get(i).subList(0, this.types.get(i).size() - 1);
        //         // get output type as last element
        //         AbstractType outputType = this.types.get(i).get(this.types.get(i).size() - 1);
        //         if (inputTypes.equals(type.inputTypes) && outputType.equals(type.outputType)) {
        //             return true;
        //         }
        //     }
        //     return false;
        // }

        
        FunctionVariableType type = (FunctionVariableType) obj;
        // if any one pair matches, then return true
        for (int i = 0; i < this.types.size(); i++) {
            for (int j = 0; j < type.types.size(); j++) {
                if (this.types.get(i).equals(type.types.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 5 * (5 * this.types.hashCode()) + 3;
    }

    @Override
    public String toString() {
        String result = "(";
        for (int i = 0; i < this.types.size(); i++) {
            // last element is output type, others are input types
            // get input types as sublist
            List<AbstractType> inputTypes = this.types.get(i).subList(0, this.types.get(i).size() - 1);
            // get output type as last element
            AbstractType outputType = this.types.get(i).get(this.types.get(i).size() - 1);
            result += "(" + inputTypes.toString() + " -> " + outputType.toString() + ")";
            // add comma if not last element
            if (i != this.types.size() - 1) {
                result += " OR ";
            }
        }
        result += ")";
        return result;
    }
}
