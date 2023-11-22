package org.genesys.type;

/**
 * Created by yufeng on 5/31/17.
 */
public class ListType implements AbstractType {
    public final AbstractType type;

    public ListType(AbstractType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        // System.out.println("ListType equals: " + obj.toString());
        if (!(obj instanceof ListType)) {
            return false;
        }
        // if obj type is templated return true
        // if (((ListType) obj).type instanceof TemplateType) {
        //     return true;
        // }
        // if (this.type instanceof TemplateType) {
        //     return true;
        // }
        return this.type.equals(((ListType) obj).type);
    }

    @Override
    public int hashCode() {
        return 5 * this.type.hashCode() + 1;
    }

    @Override
    public String toString() {
        return "List<" + this.type.toString() + ">";
    }
}
