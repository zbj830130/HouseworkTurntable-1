package dao.HouseworkTurntable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.HouseworkTurntable.HouseworkItem;
import models.HouseworkTurntable.TurntableCountItem;

/**
 * Created by zhangbojin on 3/02/17.
 */

public class ChartDao {
    private ChartDBHelper dbHelper = null;
    private static SQLiteDatabase db = null;
    private static String sql = null;
    private static final String tab_name = "TurntableCount";

    public ChartDao(Context context) {
        dbHelper = new ChartDBHelper(context);
    }

    public void save(TurntableCountItem item) {
        db = dbHelper.getWritableDatabase();
        sql = "insert into " + tab_name + "(houseworkId,houseworkName,CountNum) values(?,?,?)";
        db.execSQL(sql, new Object[]{item.getHouseworkId(), item.getHouseworkName(), item.getCountNum()});
        db.close();
    }

    public int getItemCount() {
        db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("select count(1) as CountNum from " + tab_name + "", null);
        int count = 0;
        while (c.moveToNext()) {
            count = c.getInt(c.getColumnIndex("CountNum"));
        }
        c.close();
        db.close();

        return count;
    }

    public ArrayList<TurntableCountItem> queryTurntableCountItems() {
        ArrayList<TurntableCountItem> items = new ArrayList<TurntableCountItem>();
        db = dbHelper.getWritableDatabase();

        try {
            Cursor c = db.rawQuery("select * from " + tab_name + "", null);
            while (c.moveToNext()) {
                TurntableCountItem item = new TurntableCountItem();
                item.setId(c.getInt(c.getColumnIndex("_id")));
                item.setHouseworkId(c.getInt(c.getColumnIndex("houseworkId")));
                item.setHouseworkName(c.getString(c.getColumnIndex("houseworkName")));
                item.setCountNum(c.getInt(c.getColumnIndex("CountNum")));
                items.add(item);
            }
            c.close();
        } catch (Exception exp) {

        } finally {
            db.close();
        }
        return items;
    }

    public void updateHWName(TurntableCountItem item) {
        db = dbHelper.getWritableDatabase();
        sql = "update " + tab_name + " set houseworkName=? where houseworkId=?";
        db.execSQL(sql, new Object[]{item.getHouseworkName(), item.getHouseworkId()});
        db.close();
    }

    public void updateCountNum(int hwId) {
        db = dbHelper.getWritableDatabase();
        sql = "update " + tab_name + " set CountNum=CountNum+1 where houseworkId=?";
        db.execSQL(sql, new Object[]{hwId});
        db.close();
    }
}
