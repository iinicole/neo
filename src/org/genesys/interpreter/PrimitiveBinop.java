package org.genesys.interpreter;

/**
 * Created by yufeng on 5/31/17.
 */
public class PrimitiveBinop implements Binop {
    private final String op;

    public PrimitiveBinop(String op) {
        this.op = op;
    }

    public Object apply(Object first, Object second) {
        if (!(first instanceof Integer) || !(second instanceof Integer)) {
            return null;
        }
        if (this.op.equals("+")) {
            return (int) first + (int) second;
        } else if (this.op.equals("^")) {
            if ((int) second < 0) return null;
            int res = 1;
            for (int i = 1; i <= (int)second; i++){
                res *= (int)first;
            }
            return res;
        } else if (this.op.equals("*")) {
            return (int) first * (int) second;
        } else if (this.op.equals(">")) {
            return (int) first > (int) second;
        } else if (this.op.equals(">=")) {
            return (int) first >= (int) second;
        } else if (this.op.equals("<")) {
            return (int) first < (int) second;
        } else if (this.op.equals("<=")) {
            return (int) first <= (int) second;
        } else if (this.op.equals("||")) {
            return (boolean) first || (boolean) second;
        } else if (this.op.equals("&&")) {
            return (boolean) first && (boolean) second;
        } else if (this.op.equals("-")) {
            return (int) first - (int) second;
        } else if (this.op.equals("/")) {
            if ((int) second == 0) return null;
            return (int) first / (int) second;
        } else if (this.op.equals("~")) {
            return !(boolean) first;
        } else if (this.op.equals("%")) {
            return (int) first % (int) second;
        } else if (this.op.equals("==")) {
            return (int) first == (int) second;
        } else if (this.op.equals("!=")) {
            return (int) first != (int) second;
        } else if (this.op.equals("%=")){
            if ((int) second <= 0) return null;
            return (((int) first) % (int)second) == 0;
        } else if (this.op.equals("%!=")){
            if ((int) second <= 0) return null;
            return (((int) first) % (int)second) != 0;
        } else if (this.op.equals("%!=2")) {//ODD
            return (((int) first) % 2) != (int) second;
        } else if (this.op.equals("%=2")) {//EVEN
            return (((int) first) % 2) == (int) second;
        } else if (this.op.equals("**")) {
            return ((int) first * (int)first);
        } else {
            assert false : this.op;
            throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return "l(a,b).(" + this.op + " a b)";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PrimitiveBinop)) {
            return false;
        }
        PrimitiveBinop binop = (PrimitiveBinop) obj;
        return this.op.equals(binop.op);
    }
}
