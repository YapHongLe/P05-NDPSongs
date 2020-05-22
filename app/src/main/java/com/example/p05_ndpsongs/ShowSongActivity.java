package com.example.p05_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ShowSongActivity extends AppCompatActivity {
    ListView lv;
    SongArrayAdapter saa;
    ArrayList<Song> al;
    Button btnFilter;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        lv = (ListView) this.findViewById(R.id.lv);
        btnFilter = findViewById(R.id.btnFilter);
        spinner = findViewById(R.id.dynamic_spinner);
        DBHelper dbh = new DBHelper(this);
        final ArrayList<Song> songs = dbh.getAllSong();

        saa = new SongArrayAdapter(this, R.layout.row, songs);
        lv.setAdapter(saa);
        al = new ArrayList<Song>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Song data = songs.get(position);
                Intent i = new Intent(ShowSongActivity.this,
                        ModifySongActivity.class);
                i.putExtra("data", data);
                startActivityForResult(i, 9);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(ShowSongActivity.this);
                al.clear();
                al.addAll(dbh.getAllSong("5"));
                dbh.close();
                saa.notifyDataSetChanged();
            }
        });

        final ArrayList<String> alYear = new ArrayList<String>();
        for(int i = 0; i<al.size(); i ++){
            alYear.add(al.get(i).getYear()+"");
        }
        final ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,alYear);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = spinner.getSelectedItem().toString();
                DBHelper dbh = new DBHelper(ShowSongActivity.this);
                al.clear();
                al.addAll(dbh.getAllSong(selected));
                dbh.close();
                aa.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper dbh = new DBHelper(ShowSongActivity.this);
            al.clear();
            al.addAll(dbh.getAllSong());
            dbh.close();
            saa = new SongArrayAdapter(this, R.layout.row, al);
            lv.setAdapter(saa);
        }

    }
}
