package com.example.p05_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ModifySongActivity extends AppCompatActivity {
    Button btnUpdate, btnDelete, btnCancel;
    EditText etTitle, etSinger, etYear;
    TextView tvID;
    Song data;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        tvID = findViewById(R.id.tvID);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        rg = findViewById(R.id.radiogroupstars);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");
        tvID.setText(data.getId()+ "");
        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(data.getYear()+ "");
        int star = data.getStars();
        if(star == 1){
            rg.check(R.id.radioButton);
        }
        else if(star == 2){
            rg.check(R.id.radioButton2);
        }
        else if(star == 3){
            rg.check(R.id.radioButton3);
        }
        else if(star == 4){
            rg.check(R.id.radioButton4);
        }
        else if(star == 5){
            rg.check(R.id.radioButton5);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(ModifySongActivity.this);
                data.setSingers(etSinger.getText().toString());
                data.setTitle(etTitle.getText().toString());
                int intYear = Integer.parseInt(etYear.getText().toString());
                data.setYear(intYear);
                data.setStars(getStars());
                dbh.updateSong(data);
                dbh.close();
                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(ModifySongActivity.this);
                dbh.deleteNote(data.getId());
                dbh.close();
                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
