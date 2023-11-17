package org.genesys.type;

/**
 * Created by Nicole on 5/31/23.
 */
public class TemplateType implements AbstractType {
    public AbstractType type;

    public TemplateType() {
    }

    @Override
    public boolean equals(Object obj) {
        // compare that type is not yet set or that the types are equal
        if (obj instanceof TemplateType) {
            return this.type == null || this.type.equals(((TemplateType) obj).type);
        }
        else if (obj instanceof IntType) {
            return this.type == null || this.type.equals(((IntType) obj));
        }
        else if (obj instanceof ListType) {
            return this.type == null || this.type.equals(((ListType) obj));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 17 * this.type.hashCode() + 1;
    }

    @Override
    public String toString() {
        if (this.type == null) {
            return "T";
        }
        return this.type.toString();
    }
}
