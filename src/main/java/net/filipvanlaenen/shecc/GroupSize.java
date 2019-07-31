package net.filipvanlaenen.shecc;

import java.util.Arrays;

public abstract class GroupSize {

    static GroupSize parseGroupSize(String string) {
        if (string.contains(":")) {
            int[] sizes = Arrays.stream(string.split(":")).mapToInt(Integer::parseInt).toArray();
            return new DifferentiatedGroupSize(sizes);
        } else {
            int size = Integer.parseInt(string);
            return new SimpleGroupSize(size);
        }
    }

    public abstract int getFullSize();

}
