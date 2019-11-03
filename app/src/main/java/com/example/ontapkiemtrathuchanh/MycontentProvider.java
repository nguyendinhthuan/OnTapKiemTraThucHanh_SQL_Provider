package com.example.ontapkiemtrathuchanh;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

public class MycontentProvider extends ContentProvider {
    static final String URL = "content://com.example.ontapkiemtrathuchanh/data";
    static final UriMatcher uriMatcher;
    static HashMap<String, String> MAP;
    private SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.ontapkiemtrathuchanh","data", 1);
        uriMatcher.addURI("com.example.ontapkiemtrathuchanh","data" + "/#", 2);
    }

    @Override
    public boolean onCreate() {
        DBHelper dbHelper = new DBHelper(getContext());
        db = dbHelper.getWritableDatabase();
        if(db == null) return false;
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteQueryBuilder sql = new SQLiteQueryBuilder();
        if(s1.equalsIgnoreCase("tenTG")) {
            sql.setTables("tacgia");
            switch (uriMatcher.match(uri)) {
                case 1:
                    sql.setProjectionMap(MAP);
                    break;
                case 2:
                    sql.appendWhere("maTG=" + uri.getPathSegments().get(1));
            }
        }else if(s1.equalsIgnoreCase("tenS")){
            sql.setTables("sach");
            switch (uriMatcher.match(uri)) {
                case 1:
                    sql.setProjectionMap(MAP);
                    break;
                case 2:
                    sql.appendWhere("maS=" + uri.getPathSegments().get(1));
            }
        }
        Cursor cursor = sql.query(db, strings, s, strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int row1, row2;
        row1 = (int)db.insert("tacgia", null, contentValues);
        row2 = (int)db.insert("sach", null, contentValues);
        if(row1 > 0){
            Uri uri1 = ContentUris.withAppendedId(Uri.parse(URL), row1);
            getContext().getContentResolver().notifyChange(uri1, null);
            return uri1;
        }else{
            Uri uri2 = ContentUris.withAppendedId(Uri.parse(URL), row2);
            getContext().getContentResolver().notifyChange(uri2, null);
            return uri2;
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int count = 0;
        if(s.equalsIgnoreCase("maTG=?")){
            count = db.delete("tacgia", s, strings);
        }else if(s.equalsIgnoreCase("maS=?")){
            count = db.delete("sach", s, strings);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int count = 0;
        if(s.equalsIgnoreCase("maTG=?")){
            count = db.update("tacgia", contentValues, s, strings);
        }else if(s.equalsIgnoreCase("maS=?")){
            count = db.update("sach", contentValues, s, strings);
        }
        return count;
    }
}
