package com.password.manager.core.brut.force.prevention;

/**
 * Created by Clemens on 06.01.2015.
 */
public class LockTime {
    // Times in minutes

    public static final int SMALL = 1;

    public static final int MEDIUM = 5;

    public static final int LARGE = 10;

    public static final int EXTRA_LARGE = 20;

    public static final int EXTRA_EXTRA_LARGE = 60;

    public static int getTime(int iTry) {
        if (iTry == LockTry.FIRST_STRIKE) return SMALL;
        if (iTry == LockTry.SECOND_STRIKE) return MEDIUM;
        if (iTry == LockTry.THIRD_STRIKE) return LARGE;
        if (iTry == LockTry.FOURTH_STRIKE) return EXTRA_LARGE;
        if (iTry == LockTry.FIFTH_STRIKE) return EXTRA_EXTRA_LARGE;

        return 0;
    }
}
