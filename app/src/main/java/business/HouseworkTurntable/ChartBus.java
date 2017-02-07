package business.HouseworkTurntable;

import android.content.Context;

import java.util.ArrayList;

import dao.HouseworkTurntable.ChartDao;
import dao.HouseworkTurntable.HouseworkDao;
import models.HouseworkTurntable.HouseworkItem;
import models.HouseworkTurntable.TurntableCountItem;

/**
 * Created by zhangbojin on 3/02/17.
 */

public class ChartBus {
    private ChartDao dao = null;

    public ChartBus(Context context) {
        dao = new ChartDao(context);
    }

    public boolean isInsetDefaultData() {
        return !(dao.getItemCount() > 0);
    }

    public void addItem(int houseworkId,String houseworkName) {
        TurntableCountItem tcItem = new TurntableCountItem();
        tcItem.setHouseworkId(houseworkId);
        tcItem.setHouseworkName(houseworkName);
        tcItem.setCountNum(0);

        dao.save(tcItem);
    }

    public void updateHWName(int houseworkId,String houseworkName) {
        TurntableCountItem tcItem = new TurntableCountItem();
        tcItem.setHouseworkId(houseworkId);
        tcItem.setHouseworkName(houseworkName);
        tcItem.setCountNum(0);
        dao.updateHWName(tcItem);
    }

    public void updateCountNum(int hwID) {
        dao.updateCountNum(hwID);
    }

    public ArrayList<TurntableCountItem> queryTurntableCountItems(){
        return dao.queryTurntableCountItems();
    }
}
