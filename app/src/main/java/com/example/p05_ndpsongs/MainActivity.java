package com.example.p05_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnShowlist;
    EditText etSinger, etTitle, etYear;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowlist = findViewById(R.id.btnShowList);
        etSinger = findViewById(R.id.etSinger);
        etTitle = findViewById(R.id.etTitle);
        etYear = findViewById(R.id.etYear);
        rg = findViewById(R.id.radiogroupstars);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString().trim();
                if(title.length() == 0)
                    return;

                DBHelper dbh = new DBHelper(MainActivity.this);
                if(dbh.isExistingSong(etTitle.getText().toString())){
                    Toast.makeText(MainActivity.this, "Same song already exists", Toast.LENGTH_LONG).show();
                }else{
                    String dataTitle = etTitle.getText().toString();
                    String dataSinger = etSinger.getText().toString();
                    int stars = getStars();
                    String year = etYear.getText().toString();
                    int intYear = Integer.parseInt(year);
                    dbh.insertSong(dataSinger, dataTitle, intYear, stars);
                    Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_LONG).show();

                }

            }
        });

        btnShowlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ShowSongActivity.class);
                startActivity(i);
            }
        });

    }

    private int getStars() {
        int stars = 1;
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.radioButton:
                stars = 1;
                break;
            case R.id.radioButton2:
                stars = 2;
                break;
            case R.id.radioButton3:
                stars = 3;
                break;
            case R.id.radioButton4:
                stars = 4;
                break;
            case R.id.radioButton5:
                stars = 5;
                break;
        }
        return stars;
    }
}
