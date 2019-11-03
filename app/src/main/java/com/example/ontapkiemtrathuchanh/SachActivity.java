package com.example.ontapkiemtrathuchanh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class SachActivity extends AppCompatActivity {

    private Button btnThem, btnXoa, btnSua, btnTim;
    private EditText etMaS, etTenS, etMaTG;
    private GridView gridView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<sach> saches;
    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);

        helper = new DBHelper(this);
        btnSua = (Button)findViewById(R.id.buttonSua);
        btnThem = (Button)findViewById(R.id.buttonThem);
        btnTim = (Button)findViewById(R.id.buttonTim);
        btnXoa = (Button)findViewById(R.id.buttonXoa);
        etMaTG = (EditText)findViewById(R.id.editTextMaTG);
        etTenS = (EditText)findViewById(R.id.editTextTenS);
        etMaS = (EditText)findViewById(R.id.editTextMaS);
        gridView = (GridView)findViewById(R.id.danhsachS);

//        capnhatGV(etMaS.getText().toString());
            capnhatProvider();
//        btnThem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(helper.insertS(etMaS.getText().toString(), etTenS.getText().toString(), etMaTG.getText().toString())>0){
//                    Toast.makeText(getBaseContext(), "them ok", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getBaseContext(), "them error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        btnXoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(helper.deleteS(etMaS.getText().toString())>0){
//                    Toast.makeText(getBaseContext(), "xoa ok", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getBaseContext(), "xoa error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        btnSua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(helper.updateS(etMaS.getText().toString(), etTenS.getText().toString(), etMaTG.getText().toString())>0){
//                    Toast.makeText(getBaseContext(), "sua ok", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getBaseContext(), "sua error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        btnTim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                capnhatGV(etMaS.getText().toString());
//            }
//        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("maS", etMaS.getText().toString());
                contentValues.put("tenS", etTenS.getText().toString());
                contentValues.put("maTG", etMaTG.getText().toString());
                Uri table = Uri.parse("content://com.example.ontapkiemtrathuchanh/data");
                Uri insert = getContentResolver().insert(table, contentValues);
                capnhatProvider();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("maS", etMaS.getText().toString());
                contentValues.put("tenS", etTenS.getText().toString());
                contentValues.put("maTG", etMaTG.getText().toString());
                Uri table = Uri.parse("content://com.example.ontapkiemtrathuchanh/data");
                if(getContentResolver().update(table, contentValues, "maS=?", new String[]{etMaS.getText().toString()})>0){
                    Toast.makeText(getBaseContext(), "sua ok", Toast.LENGTH_SHORT).show();
                }
                capnhatProvider();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri table = Uri.parse("content://com.example.ontapkiemtrathuchanh/data");
                if(getContentResolver().delete(table, "maS=?", new String[]{etMaS.getText().toString()})>0){
                    Toast.makeText(getBaseContext(), "xoa ok", Toast.LENGTH_SHORT).show();
                }
                capnhatProvider();
            }
        });
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capnhatProvider();
            }
        });
    }
    public void capnhatGV(String ma){
        arrayList = new ArrayList<>();
        saches = new ArrayList<>();
        saches = helper.TimSs(ma);
        for(sach tg: saches){
            arrayList.add(tg.getMaSach());
            arrayList.add(tg.getTenSach());
            arrayList.add(tg.getMaTG());
        }
        arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, arrayList);
        gridView.setAdapter(arrayAdapter);
    }
    public void capnhatProvider(){
        arrayList = new ArrayList<>();
        Uri table = Uri.parse("content://com.example.ontapkiemtrathuchanh/data");
        Cursor cursor = getContentResolver().query(table, null, null, null, "tenS");
        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            arrayList.add(cursor.getString(0));
            arrayList.add(cursor.getString(1));
            arrayList.add(cursor.getString(2));
            cursor.moveToNext();
        }
        arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, arrayList);
        gridView.setAdapter(arrayAdapter);
    }
}
