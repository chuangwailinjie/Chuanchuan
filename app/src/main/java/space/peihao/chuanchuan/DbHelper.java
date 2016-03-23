package space.peihao.chuanchuan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by niit on 2016/1/6.
 */
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists tb_record" +
                "(_id integer primary key autoincrement," +
                "score integer)");

        db.execSQL("create table if not exists tb_record1" +
                "(_id integer primary key autoincrement," +
                "score integer)");

        db.execSQL("create table if not exists tb_record2" +
                "(_id integer primary key autoincrement," +
                "score integer)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
