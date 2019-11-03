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

public class TacGiaActivity extends AppCompatActivity {

    private Button btnThem, btnXoa, btnSua, btnTim;
    private EditText etMaTG, etTenTG;
    private GridView gridView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<tacgia> tacgias;
    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tac_gia);

        helper = new DBHelper(this);
        btnSua = (Button)findViewById(R.id.buttonSua);
        btnThem = (Button)findViewById(R.id.buttonThem);
        btnTim = (Button)findViewById(R.id.buttonTim);
        btnXoa = (Button)findViewById(R.id.buttonXoa);
        etMaTG = (EditText)findViewById(R.id.editTextMaTG);
        etTenTG = (EditText)findViewById(R.id.editTextTenTG);
        gridView = (GridView)findViewById(R.id.danhsachTG);

//        capnhatGV(etMaTG.getText().toString());
        capnhatProvider();

//        btnThem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(helper.insertTG(etMaTG.getText().toString(), etTenTG.getText().toString())>0){
//                    Toast.makeText(getBaseContext(), "them ok", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getBaseContext(), "them error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        btnXoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(helper.deleteTG(etMaTG.getText().toString())>0){
//                    Toast.makeText(getBaseContext(), "xoa ok", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getBaseContext(), "xoa error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        btnSua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(helper.updateTG(etMaTG.getText().toString(), etTenTG.getText().toString())>0){
//                    Toast.makeText(getBaseContext(), "sua ok", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getBaseContext(), "sua error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        btnTim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                capnhatGV(etMaTG.getText().toString());
//            }
//        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("maTG", etMaTG.getText().toString());
                contentValues.put("tenTG", etTenTG.getText().toString());
                Uri table = Uri.parse("content://com.example.ontapkiemtrathuchanh/data");
                Uri insert = getContentResolver().insert(table, contentValues);
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("maTG", etMaTG.getText().toString());
                contentValues.put("tenTG", etTenTG.getText().toString());
                Uri table = Uri.parse("content://com.example.ontapkiemtrathuchanh/data");
                if(getContentResolver().update(table, contentValues, "maTG=?", new String[]{etMaTG.getText().toString()})>0){
                    Toast.makeText(getBaseContext(), "sua ok", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri table = Uri.parse("content://com.example.ontapkiemtrathuchanh/data");
                if(getContentResolver().delete(table, "maTG=?", new String[]{etMaTG.getText().toString()})>0){
                    Toast.makeText(getBaseContext(), "xoa ok", Toast.LENGTH_SHORT).show();
                }
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
        tacgias = new ArrayList<>();
        tacgias = helper.TimTGs(ma);
        for(tacgia tg: tacgias){
            arrayList.add(tg.getMaTG());
            arrayList.add(tg.getTenTG());
        }
        arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, arrayList);
        gridView.setAdapter(arrayAdapter);
    }
    public void capnhatProvider(){
        arrayList = new ArrayList<>();
        Uri table = Uri.parse("content://com.example.ontapkiemtrathuchanh/data");
        Cursor cursor = getContentResolver().query(table, null, null, null, "tenTG");
        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            arrayList.add(cursor.getString(0));
            arrayList.add(cursor.getString(1));
            cursor.moveToNext();
        }
        arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, arrayList);
        gridView.setAdapter(arrayAdapter);
    }
}
