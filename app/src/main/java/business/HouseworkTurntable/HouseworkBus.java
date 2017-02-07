package business.HouseworkTurntable;

import android.content.Context;

import dao.HouseworkTurntable.HouseworkDao;
import models.HouseworkTurntable.HouseworkItem;

/**
 * Created by zhangbojin on 1/02/17.
 */

public class HouseworkBus {
    private HouseworkDao dao = null;

    public HouseworkBus(Context context) {
        dao = new HouseworkDao(context);
    }

    public boolean isInsetDefaultData() {
        return !(dao.getItemCount() > 0);
    }

    public void insertDefaultData() {
        dao.save(new HouseworkItem("Wash the dishes", false));
        dao.save(new HouseworkItem("Mop", false));
        dao.save(new HouseworkItem("Vacuuming", false));
        dao.save(new HouseworkItem("Take care of children", false));
    }

    public int addItem(String name, boolean isSelected) {
        return dao.save(new HouseworkItem(name, isSelected));
    }

    public void updateSelected(int id, boolean isSelected) {
        HouseworkItem item = new HouseworkItem();
        item.setId(id);
        item.setSelected(isSelected);
        dao.updateSelected(item);
    }

    public void updataItem(int id, String name, boolean isSelected) {
        HouseworkItem item = new HouseworkItem();
        item.setId(id);
        item.setSelected(isSelected);
        item.setName(name);
        dao.updateItem(item);
    }

    public void deleteItem(int id) {
        dao.deleItem(id);
    }
}
