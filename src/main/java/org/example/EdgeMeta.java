package org.example;

import java.util.Objects;

public final class EdgeMeta {
    private final int v1;
    private final int v2;
    private final int ind;

    public EdgeMeta(int v1, int v2, int ind) {
        this.v1 = v1;
        this.v2 = v2;
        this.ind = ind;
    }

    public int v1() {
        return v1;
    }

    public int v2() {
        return v2;
    }

    public int ind() {
        return ind;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (EdgeMeta) obj;
        return this.v1 == that.v1 &&
                this.v2 == that.v2 &&
                this.ind == that.ind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2, ind);
    }

    @Override
    public String toString() {
        return "EdgeMeta[" +
                "v1=" + v1 + ", " +
                "v2=" + v2 + ", " +
                "ind=" + ind + ']';
    }

}
