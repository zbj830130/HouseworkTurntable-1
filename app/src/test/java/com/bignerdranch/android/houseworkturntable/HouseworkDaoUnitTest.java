package com.bignerdranch.android.houseworkturntable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import dao.HouseworkTurntable.HouseworkDao;
import models.HouseworkTurntable.HouseworkItem;

/**
 * Created by zhangbojin on 8/02/17.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class HouseworkDaoUnitTest {
    @Test
    public void saveUnitTest() throws Exception {
        HouseworkDao dao = new HouseworkDao(RuntimeEnvironment.application);

        HouseworkItem newItem = new HouseworkItem();
        newItem.setName("Test Housework Item");
        newItem.setSelected(true);

        int index = dao.save(newItem);

        Assert.assertTrue(dao.getItemCount() > 0);
    }

    @Test
    public void saveFormatUnitTest() throws Exception {
        HouseworkDao dao = new HouseworkDao(RuntimeEnvironment.application);

        HouseworkItem newItem = new HouseworkItem();
        newItem.setName("TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12");
        newItem.setSelected(true);

        int index = dao.save(newItem);

        Assert.assertTrue(dao.getItemCount() > 0);
        newItem.setId(index);
        newItem.setSelected(false);
        Assert.assertTrue(dao.getItem(index).getName().equals("TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12"));
    }

    @Test
    public void updateSelectedUnitTest() throws Exception {
        HouseworkDao dao = new HouseworkDao(RuntimeEnvironment.application);

        HouseworkItem newItem = new HouseworkItem();
        newItem.setName("Test Housework Item");
        newItem.setSelected(true);

        int index = dao.save(newItem);

        Assert.assertTrue(dao.getItemCount() > 0);

        newItem.setId(index);
        newItem.setSelected(false);

        dao.updateSelected(newItem);
        Assert.assertTrue(dao.getItem(index).isSelected() == false);
    }

    @Test
    public void updateUnitTest() throws Exception {
        HouseworkDao dao = new HouseworkDao(RuntimeEnvironment.application);

        HouseworkItem newItem = new HouseworkItem();
        newItem.setName("Test1");
        newItem.setSelected(true);

        int index = dao.save(newItem);

        newItem.setId(index);
        newItem.setSelected(false);
        newItem.setName("TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12");

        dao.updateItem(newItem);
        Assert.assertTrue(dao.getItem(index).getName().equals("TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12"));

    }

    @Test
    public void DeleteUnitTest() throws Exception {
        HouseworkDao dao = new HouseworkDao(RuntimeEnvironment.application);

        HouseworkItem newItem = new HouseworkItem();
        newItem.setName("Test1");
        newItem.setSelected(true);

        int index = dao.save(newItem);

        Assert.assertTrue(dao.getItemCount() > 0);

        dao.deleItem(index);
        Assert.assertTrue(dao.getItemCount() == 0);
    }
}
