package com.bignerdranch.android.houseworkturntable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import Util.HouseworkTurntable.ColorUtil;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        ColorUtil.getColor();
        assertEquals(4, 2 + 2);
    }
}