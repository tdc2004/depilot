package com.chinhtd.quanlyxe1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<xe> list = new ArrayList<>();
    XeAdapter adapter;

    ActivityResultLauncher<Intent> getData = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                xe xe = (com.chinhtd.quanlyxe1.xe) intent.getSerializableExtra("xe1");
                list.add(xe);
                ghiFile();
                adapter.notifyDataSetChanged();
            }
        }
    });

    String FILE_NAME = "chinh.txt";

    public void ghiFile() {
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void docFile(){
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            list = (ArrayList<xe>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }else if (id == R.id.add){
            Intent intent = new Intent(getApplicationContext(), Activity_ADD.class);
            getData.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.list_view);
        docFile();
        if (list.size() == 0){
            list.add(new xe("Toyota Camry", "Toyota", 2012, 120000));
            list.add(new xe("BMW X5", "BMW", 2012, 120000));
            list.add(new xe("Ford Mustang", "Ford", 2012, 120000));
            list.add(new xe("Honda Civic", "Honda", 2012, 120000));
            list.add(new xe("Mercedes-Benz S-Class", "Mercedes-Benz", 2012, 120000));
        }
         adapter = new XeAdapter(this, list);
        listView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản Lý Xe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.baseline_arrow_back_24));

//        Button button = findViewById(R.id.btn_add);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Activity_ADD.class);
//                getData.launch(intent);
//            }
//        });
    }

    public class XeAdapter extends BaseAdapter {

        Context context;
        ArrayList<xe> list;

        public XeAdapter(Context context, ArrayList<xe> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_lv, parent, false);
            TextView name = convertView.findViewById(R.id.tv_ten);
            TextView hang = convertView.findViewById(R.id.tv_hang);
            TextView nam = convertView.findViewById(R.id.tv_namsx);
            TextView gia = convertView.findViewById(R.id.tv_giaBan);

            Button button = convertView.findViewById(R.id.btn_xoa);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setMessage("Ban co chac chan muon xoa khong??").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.remove(position);
                            ghiFile();
                            notifyDataSetChanged();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
            });

            name.setText(list.get(position).ten);
            hang.setText(list.get(position).HangSx);
            nam.setText(list.get(position).NamSX + "");
            gia.setText(list.get(position).giaBan + "");

            return convertView;
        }
    }
}