package business.HouseworkTurntable;

import android.content.Context;

import java.util.List;

import dao.HouseworkTurntable.HouseworkDao;
import models.HouseworkTurntable.HouseworkItem;

/**
 * Created by zhangbojin on 1/02/17.
 */

public class HouseworkLab {
    private static HouseworkLab sHouseworkLab;
    private List<HouseworkItem> mItems;


    public static business.HouseworkTurntable.HouseworkLab get(Context context) {
        if (sHouseworkLab == null) {
            sHouseworkLab = new HouseworkLab(context);
        }

        return sHouseworkLab;
    }

    public void Refresh(Context context){
        mItems = new HouseworkDao(context).queryHouseworkItems();
    }

    private HouseworkLab(Context context) {
        mItems = new HouseworkDao(context).queryHouseworkItems();
    }

    public List<HouseworkItem> getHouseworkItems() {
        return mItems;
    }

    public HouseworkItem getHouseworkItem(int id) {
        for (HouseworkItem item : mItems) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}

