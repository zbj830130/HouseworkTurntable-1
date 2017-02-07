package dao.HouseworkTurntable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.HouseworkTurntable.HouseworkItem;

/**
 * Created by zhangbojin on 1/02/17.
 */

public class HouseworkDao {
    private HouseworkDBHelper dbHelper = null;
    private static SQLiteDatabase db = null;
    private static String sql = null;
    private static final String tab_name = "housework";

    public HouseworkDao(Context context) {
        dbHelper = new HouseworkDBHelper(context);
    }

    public int save(HouseworkItem item) {
        int id = 0;
        db = dbHelper.getWritableDatabase();
        sql = "insert into " + tab_name + "(name,isSelected) values(?,?)";
        db.execSQL(sql, new Object[]{item.getName(), item.isSelected()});

        Cursor c = db.rawQuery("select LAST_INSERT_ROWID() ", null);
        while (c.moveToNext()) {
            id = c.getInt(0);
        }
        db.close();

        return id;
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

    public ArrayList<HouseworkItem> queryHouseworkItems() {
        ArrayList<HouseworkItem> items = new ArrayList<HouseworkItem>();
        db = dbHelper.getWritableDatabase();

        try {
            Cursor c = db.rawQuery("select * from " + tab_name + "", null);
            while (c.moveToNext()) {
                HouseworkItem item = new HouseworkItem();
                item.setId(c.getInt(c.getColumnIndex("_id")));
                item.setName(c.getString(c.getColumnIndex("name")));
                item.setSelected(c.getInt(c.getColumnIndex("isSelected")) == 0 ? false : true);
                items.add(item);
            }
            c.close();
        } catch (Exception exp) {

        } finally {
            db.close();
        }
        return items;
    }

    public HouseworkItem getItem(int id) {
        db = dbHelper.getWritableDatabase();
        HouseworkItem item = new HouseworkItem();

        try {
            Cursor c = db.rawQuery("select * from " + tab_name + " where _id=?", new String[]{id + ""});
            while (c.moveToNext()) {
                item.setId(c.getInt(c.getColumnIndex("_id")));
                item.setName(c.getString(c.getColumnIndex("name")));
                item.setSelected(c.getInt(c.getColumnIndex("isSelected")) == 0 ? false : true);
                break;
            }
            c.close();
        } catch (Exception exp) {

        } finally {
            db.close();
        }
        return item;
    }

    public void updateSelected(HouseworkItem item) {
        db = dbHelper.getWritableDatabase();
        sql = "update " + tab_name + " set isSelected=? where _id=?";
        db.execSQL(sql, new Object[]{item.isSelected(), item.getId()});
        db.close();
    }

    public void updateItem(HouseworkItem item) {
        db = dbHelper.getWritableDatabase();
        sql = "update " + tab_name + " set name=?,isSelected=? where _id=?";
        db.execSQL(sql, new Object[]{item.getName(), item.isSelected(), item.getId()});
        db.close();
    }

    public void deleItem(int id) {
        db = dbHelper.getWritableDatabase();
        sql = "delete from " + tab_name + " where _id=?";
        db.execSQL(sql, new Object[]{id});
        db.close();
    }
}
