package com.example.nurulmadihah.mahukerja.SQLHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {
    SQLiteHelper(Context context,
                 String title,
                 SQLiteDatabase.CursorFactory factory,
                 int version){
        super(context, title, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String title, String skill, String payment, String contacperson, String deskripsi, String pemasang, int status, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO RECORD VALUES(NULL, ?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,title);
        statement.bindString(2,skill);
        statement.bindString(3,payment);
        statement.bindString(4,contacperson);
        statement.bindString(5,deskripsi);
        statement.bindString(6,pemasang);
        statement.bindLong(7,(long)status);
        statement.bindBlob(8,image);

        statement.executeInsert();
    }
    public void updateData (String title, String skill, String payment, String contacperson, String deskripsi, String pemasang, int status, byte[] image, int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE RECORD SET title=?, skill=?, payment=?,contacperson=?,deskripsi=?,pemasang=?,status=?, image=? WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1,title);
        statement.bindString(2,skill);
        statement.bindString(3,payment);
        statement.bindString(4,contacperson);
        statement.bindString(5,deskripsi);
        statement.bindString(6,pemasang);
        statement.bindLong(7,(long)status);
        statement.bindBlob(8,image);
        statement.bindDouble(9, (double)id);

        statement.execute();
        database.close();
    }

    public void deleteData (int id){
       SQLiteDatabase database = getWritableDatabase();
       String sql = "DELETE FROM RECORD WHERE id=?;";
       SQLiteStatement statement = database.compileStatement(sql);
       statement.clearBindings();
       statement.bindDouble(1,(double)id);
       statement.execute();
       database.close();
    }
    public Cursor getData (String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }



    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase){

    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1){

    }
}
