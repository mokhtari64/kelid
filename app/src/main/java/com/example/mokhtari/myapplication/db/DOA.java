package com.example.mokhtari.myapplication.db;

import android.content.Context;
import android.database.Cursor;

import com.example.mokhtari.myapplication.model.Property;


/**
 * Created by Mahdi on 15/07/2016.
 */
public class DOA {
    Mysql mysql;
    DOA(Context context){
        mysql=new Mysql(context);
    }

    void insertProperty(Property property){
        String query="insert into property (name,email) values('"+property.name+"','"+property.email+"')";
        mysql.getWritableDatabase().execSQL(query);

    }

    Property[] getProprty(){
        String query="select * from property";
        Cursor cursor = mysql.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        Property[] a=new Property[cursor.getCount()];
        int i=0;
        while (!cursor.isAfterLast()){
            a[i]=new Property();
            a[i].name=cursor.getString(1);
            a[i].email=cursor.getString(2);
            cursor.moveToNext();
            i++;

        }
        return a;
    }


}
