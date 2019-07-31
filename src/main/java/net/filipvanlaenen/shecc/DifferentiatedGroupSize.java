package net.filipvanlaenen.shecc;

class DifferentiatedGroupSize extends GroupSize {

    private final int lowerBound;
    private final int median;
    private final int total;

    DifferentiatedGroupSize(int... sizes) {
        this.lowerBound = sizes[0];
        this.median = sizes[1];
        if (sizes.length > 2) {
            this.total = sizes[2];
        } else {
            this.total = sizes[1];
        }
    }

    @Override
    public int getFullSize() {
        return total;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getMedian() {
        return median;
    }

}
