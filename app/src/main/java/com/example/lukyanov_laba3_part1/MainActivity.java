package com.example.lukyanov_laba3_part1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3;
    EditText etFio;
    DBHelper dbHelper;
    String [] fiosplit;
    int id = 5;
    public static final int DATABASE_VERSION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(this);
        etFio = (EditText) findViewById(R.id.etFio);
        etFio.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
       // database.delete(DBHelper.TABLE_STUDENTS, null, null);
    }

    @Override
    public void onClick(View v) {
        //получение ФИО
        fiosplit =  etFio.getText().toString().split(" ", 3);
        //получение даты
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String time = format.format(new Date());
        //
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //обработка нажатий
        switch (v.getId()) {
            //вывод
            case R.id.button1:
                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                break;
            //добавление
            case R.id.button2:
                id++;
                contentValues.put(DBHelper.KEY_FNAME, fiosplit[0]);
                contentValues.put(DBHelper.KEY_FNAME, fiosplit[1]);
                contentValues.put(DBHelper.KEY_FNAME, fiosplit[2]);
                contentValues.put(DBHelper.KEY_TIME, time);
                database.insert(DBHelper.TABLE_STUDENTS, null, contentValues);
                break;
            //замена ФИО
            case R.id.button3:
                contentValues.put(DBHelper.KEY_FNAME, "Иванов");
                contentValues.put(DBHelper.KEY_LNAME, "Иван");
                contentValues.put(DBHelper.KEY_SNAME, "Иванович");
                contentValues.put(DBHelper.KEY_TIME, time);
                int updCount = database.update(DBHelper.TABLE_STUDENTS, contentValues, DBHelper.KEY_ID + "= ?", new String[] {String.valueOf(id)});
                Log.d("mLog", "updates row count = " + updCount);
                break;
        }
    }
}