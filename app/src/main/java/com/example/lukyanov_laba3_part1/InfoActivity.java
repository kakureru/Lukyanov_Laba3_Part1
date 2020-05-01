package com.example.lukyanov_laba3_part1;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    TextView textView;
    DBHelper dbHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        textView = findViewById(R.id.textView);

        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_STUDENTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int fIndex = cursor.getColumnIndex(DBHelper.KEY_FNAME);
            int lIndex = cursor.getColumnIndex(DBHelper.KEY_LNAME);
            int sIndex = cursor.getColumnIndex(DBHelper.KEY_SNAME);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            do {
                textView.setText(textView.getText() + "ID: " + cursor.getInt(idIndex) +
                        "\nФ: " + cursor.getString(fIndex) +
                        "\nИ: " + cursor.getString(lIndex) +
                        "\nО: " + cursor.getString(sIndex) +
                        "\nВремя добавления записи: " + cursor.getString(timeIndex) + "\n\n");
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                     //   ", fio = " + cursor.getString(fioIndex) +
                        ", time = " + cursor.getString(timeIndex));
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        cursor.close();
    }
}
