package com.bignerdranch.android.houseworkturntable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import Util.HouseworkTurntable.ColorUtil;

/**
 * Created by zhangbojin on 8/02/17.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ColorUtilUnitTest {
    @Test
    public void getRandomColor() throws Exception {
        int randomColor = ColorUtil.getRandomColor();

        Assert.assertTrue(randomColor < 0);
    }

    @Test
    public void compareRandomColors() throws Exception {
        int randomColor1 = ColorUtil.getRandomColor();
        int randomColor2 = ColorUtil.getRandomColor();

        Assert.assertTrue(randomColor1 != randomColor2);
    }
}
