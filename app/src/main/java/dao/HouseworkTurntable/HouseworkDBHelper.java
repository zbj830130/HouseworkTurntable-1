package dao.HouseworkTurntable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhangbojin on 1/02/17.
 */

public class HouseworkDBHelper extends SQLiteOpenHelper {
    private static final String db_name = "turntable.db";//database name
    private static int NUMBER = 5;//current database version
    private static final String table_name = "housework";//table name
    private static String sql = null;//sql

    public HouseworkDBHelper(Context context) {
        super(context, db_name, null, NUMBER);//数据库文件保存在当前应用所在包名:<包>/database/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            sql = "CREATE TABLE " + table_name + " (" +
                    "id            INTEGER         PRIMARY KEY ," +
                    "name        VARCHAR(50)        NOT NULL ," +
                    "isSelected  Boolean            NULL )";

            db.execSQL(sql);
        } catch (Exception ext) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("Alter table " + table_name + " ADD Column isSelected  Boolean   NULL");
    }
}
