package com.chinhtd.quanlyxe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_ADD extends AppCompatActivity {
    EditText editText_ten;
    EditText editText_hang;
    EditText editText_nam;
    EditText editText_gia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editText_ten = findViewById(R.id.edt_ten);
        editText_hang = findViewById(R.id.edt_hang);
        editText_nam= findViewById(R.id.edt_nam);
        editText_gia = findViewById(R.id.edt_Gia);

        Button button = findViewById(R.id.btn_LuuLai);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()==true){
                    String ten = editText_ten.getText().toString();
                    String hang = editText_hang.getText().toString();
                    String nam = editText_nam.getText().toString();
                    String gia = editText_gia.getText().toString();

                    int nam_1 = Integer.parseInt(nam);
                    double gia_1 = Double.parseDouble(gia);

                    xe xe = new xe(ten,hang,nam_1,gia_1);
                    Intent intent = new Intent();
                    intent.putExtra("xe1",xe);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private boolean validate() {
        if (editText_ten.getText().toString().equals("")){
            Toast.makeText(this, "Ban Chua nhap ten", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editText_hang.getText().toString().equals("")){
            Toast.makeText(this, "Ban Chua nhap hang sx", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editText_nam.getText().toString().equals("")){
            Toast.makeText(this, "Ban Chua nhap nam sx", Toast.LENGTH_SHORT).show();
            return false;
        }
        int nam;
        try {
             nam = Integer.parseInt(editText_nam.getText().toString());
        }catch (Exception e){
            Toast.makeText(this, "Nam phai la int", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nam<1980 || nam>2023){
            Toast.makeText(this, "Nam phai trong khoang tu 1980 - 2023", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editText_gia.getText().toString().equals("")){
            Toast.makeText(this, "Ban Chua nhap gia", Toast.LENGTH_SHORT).show();
            return false;
        }
        double gia;
        try {
            gia = Double.parseDouble(editText_gia.getText().toString());
        }catch (Exception e){
            Toast.makeText(this, "Gia ban phai la double", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gia<=0){
            Toast.makeText(this, "Gia >0", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}