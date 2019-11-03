package com.example.ontapkiemtrathuchanh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "phamnhutduynguyen", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table tacgia(maTG text primary key, tenTG text)");
        sqLiteDatabase.execSQL("create table sach(maS text primary key, tenS text, maTG text " +
                "constraint maTG references tacgia(maTG))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists sach");
        sqLiteDatabase.execSQL("drop table if exists tacgia");
        onCreate(sqLiteDatabase);
    }

    public int insertTG(String ma, String ten){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTG", ma);
        contentValues.put("tenTG", ten);
        int rs = (int)sqLiteDatabase.insert("tacgia", null, contentValues);
        return rs;
    }
    public int insertS(String ma, String ten, String matg){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maS", ma);
        contentValues.put("tenS", ten);
        contentValues.put("maTG", matg);
        int rs = (int)sqLiteDatabase.insert("sach", null, contentValues);
        return rs;
    }
    public int updateTG(String ma, String ten){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTG", ma);
        contentValues.put("tenTG", ten);
        int rs = (int)sqLiteDatabase.update("tacgia", contentValues, "maTG=?", new String[]{ma});
        return rs;
    }
    public int updateS(String ma, String ten, String matg){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maS", ma);
        contentValues.put("tenS", ten);
        contentValues.put("maTG", matg);

        int rs = (int)sqLiteDatabase.update("sach", contentValues, "maS=?", new String[]{ma});
        return rs;
    }
    public int deleteTG(String ma){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int rs = (int)sqLiteDatabase.delete("tacgia", "maTG=?", new String[]{ma});
        return rs;
    }
    public int deleteS(String ma){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int rs = (int)sqLiteDatabase.delete("sach", "maS=?", new String[]{ma});
        return rs;
    }
    public ArrayList<tacgia> TimTGs(String ma){
        ArrayList<tacgia> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if(ma.equalsIgnoreCase("")){
            cursor = sqLiteDatabase.rawQuery("select * from tacgia", null);
        }else {
            cursor = sqLiteDatabase.rawQuery("select * from tacgia where maTG=?", new String[]{ma});
        }
        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            arrayList.add(new tacgia(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        return arrayList;
    }
    public ArrayList<sach> TimSs(String ma){
        ArrayList<sach> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if(ma.equalsIgnoreCase("")){
            cursor = sqLiteDatabase.rawQuery("select * from sach", null);
        }else {
            cursor = sqLiteDatabase.rawQuery("select * from sach where maS=?", new String[]{ma});
        }

        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            arrayList.add(new sach(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        return arrayList;
    }
}
