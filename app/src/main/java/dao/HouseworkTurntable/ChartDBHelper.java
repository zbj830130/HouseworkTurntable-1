package dao.HouseworkTurntable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhangbojin on 3/02/17.
 */

public class ChartDBHelper extends SQLiteOpenHelper {
    private static final String db_name = "turntable.db";//database name
    private static int NUMBER = 5;//current database version
    private static final String table_name = "TurntableCount";//table name
    private static String sql = null;//sql

    public ChartDBHelper(Context context) {
        super(context, db_name, null, NUMBER);//数据库文件保存在当前应用所在包名:<包>/database/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            sql = "CREATE TABLE " + table_name + " (" +
                    "_id            INTEGER         PRIMARY KEY ," +
                    "houseworkId        INTEGER        NOT NULL ," +
                    "houseworkName  VARCHAR(50)        NOT NULL ," +
                    "CountNum       INTEGER            NOT NULL )";

            db.execSQL(sql);
        } catch (Exception ext) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            sql = "CREATE TABLE " + table_name + " (" +
                    "_id            INTEGER         PRIMARY KEY ," +
                    "houseworkId        INTEGER        NOT NULL ," +
                    "houseworkName  VARCHAR(50)        NOT NULL ," +
                    "CountNum       INTEGER            NOT NULL )";

            db.execSQL(sql);
        } catch (Exception ext) {

        }
    }
}
