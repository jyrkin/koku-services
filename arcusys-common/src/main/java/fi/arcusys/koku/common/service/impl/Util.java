package fi.arcusys.koku.common.service.impl;

public class Util {

    public static void validateLimits(final int startIndex, final int queryMaxResultCount, final int comparisonMaximumResultCount) {
        if (startIndex < 1) {
            throw new IllegalArgumentException("Incorrect number for start number: " + startIndex + ", it should be greater or equal to 1.");
        }
        if (queryMaxResultCount > comparisonMaximumResultCount) {
            throw new IllegalArgumentException("Incorrect queryMaxResultCount: " + queryMaxResultCount + ", it should be less than or equal to " + comparisonMaximumResultCount + ".");
        }
    }
}
