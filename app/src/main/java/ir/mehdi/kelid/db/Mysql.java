package ir.mehdi.kelid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mahdi on 15/07/2016.
 */
public class Mysql extends SQLiteOpenHelper {

    String propertyTblquery="Create table property (id INTEGER primary key autoincrement,name text , email text)";
    public Mysql(Context context) {
        super(context, "kelid1", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(propertyTblquery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
