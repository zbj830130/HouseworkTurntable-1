package com.bignerdranch.android.houseworkturntable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import dao.HouseworkTurntable.ChartDao;
import models.HouseworkTurntable.TurntableCountItem;

/**
 * Created by zhangbojin on 8/02/17.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ChartDaoUnitTest {

    @Test
    public void saveUnitTest() throws Exception {
        ChartDao dao = new ChartDao(RuntimeEnvironment.application);

        TurntableCountItem item = new TurntableCountItem();
        item.setHouseworkId(1);
        item.setHouseworkName("Test1");

        dao.save(item);

        Assert.assertTrue(dao.getItemCount() == 1);

        item = dao.getItem(1);
        Assert.assertTrue(item.getHouseworkName().equals("Test1"));
        Assert.assertTrue(item.getCountNum() == 0);
    }

    @Test
    public void saveFormateUnitTest() throws Exception {
        ChartDao dao = new ChartDao(RuntimeEnvironment.application);

        TurntableCountItem item = new TurntableCountItem();
        item.setHouseworkId(1);
        item.setHouseworkName("TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12");

        dao.save(item);

        Assert.assertTrue(dao.getItemCount() == 1);

        item = dao.getItem(1);
        Assert.assertTrue(item.getHouseworkName().equals("TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12"));
        Assert.assertTrue(item.getCountNum() == 0);
    }

    @Test
    public void updateHWNameUnitTest() {
        ChartDao dao = new ChartDao(RuntimeEnvironment.application);

        TurntableCountItem item = new TurntableCountItem();
        item.setHouseworkId(1);
        item.setHouseworkName("Test1");

        dao.save(item);

        Assert.assertTrue(dao.getItemCount() == 1);

        item = dao.getItem(1);
        Assert.assertTrue(item.getHouseworkName().equals("Test1"));
        Assert.assertTrue(item.getCountNum() == 0);

        item.setHouseworkName("Test2");
        item.setCountNum(2);
        dao.updateHWName(item);

        item = dao.getItem(1);
        Assert.assertTrue(item.getHouseworkName().equals("Test2"));
        Assert.assertTrue(item.getCountNum() == 0);
    }

    @Test
    public void updateHWNameFormateUnitTest() {
        ChartDao dao = new ChartDao(RuntimeEnvironment.application);

        TurntableCountItem item = new TurntableCountItem();
        item.setHouseworkId(1);
        item.setHouseworkName("Test1");

        dao.save(item);

        Assert.assertTrue(dao.getItemCount() == 1);

        item = dao.getItem(1);
        Assert.assertTrue(item.getHouseworkName().equals("Test1"));
        Assert.assertTrue(item.getCountNum() == 0);

        item.setHouseworkName("TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12");
        item.setCountNum(2);
        dao.updateHWName(item);

        item = dao.getItem(1);
        Assert.assertTrue(item.getHouseworkName().equals("TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12"));
        Assert.assertTrue(item.getCountNum() == 0);
    }

    @Test
    public void updateCountNumUnitTest() {
        ChartDao dao = new ChartDao(RuntimeEnvironment.application);

        TurntableCountItem item = new TurntableCountItem();
        item.setHouseworkId(1);
        item.setHouseworkName("Test1");

        dao.save(item);

        dao.updateCountNum(1);
        item = dao.getItem(1);
        Assert.assertTrue(item.getCountNum() == 1);

        dao.updateCountNum(1);
        item = dao.getItem(1);
        Assert.assertTrue(item.getCountNum() == 2);
    }
}
