package business.HouseworkTurntable;

import java.util.ArrayList;
import java.util.List;

import models.HouseworkTurntable.HouseworkItem;


/**
 * Created by zhangbojin on 31/01/17.
 */

public class HouseworkLab {
    private static HouseworkLab sHouseworkLab;
    private List<HouseworkItem> mItems;

    public static HouseworkLab get(){
        if(sHouseworkLab == null){
            sHouseworkLab = new HouseworkLab();
        }

        return sHouseworkLab;
    }

    private HouseworkLab(){
        mItems = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HouseworkItem crime = new HouseworkItem();
            crime.setId(i);
            crime.setName("HW #" + i);
            mItems.add(crime);
        }
    }

    public List<HouseworkItem> getHouseworkItem() {
        return mItems;
    }

    public HouseworkItem getCrime(int id) {
        for (HouseworkItem item : mItems) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
