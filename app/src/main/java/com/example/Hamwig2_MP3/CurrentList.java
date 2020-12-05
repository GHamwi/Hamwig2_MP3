package com.example.Hamwig2_MP3;

/**************** Created by George B. Hamwi Homework 5 *******************/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;

public class CurrentList extends AppCompatActivity {
    Toolbar mListToolbar;
    ArrayList<File> currentSongs;
    ListView mListView;
    String songs[];
    ArrayAdapter<String> mArrayAdapter;

    /* gets the current list of the songs */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_list);
        mListToolbar = findViewById(R.id.mListToolbar);
        setSupportActionBar(mListToolbar);
        getSupportActionBar().setTitle("Now Playing");
        mListView = findViewById(R.id.currentList);

        /* Views the current list of the songs   */
        Intent songData = getIntent();
        currentSongs = (ArrayList) songData.getParcelableArrayListExtra("songsList");
        songs = new String[currentSongs.size()];

        /* Grabs the songs that are downloaded on the device in MP3 file form */
        for(int i = 0; i < currentSongs.size();i++){
            songs[i] = currentSongs.get(i).getName().replace
                    (".mp3","").replace
                    (".m4a","").replace
                    (".wav","").replace
                    (".m4b","");Log.i
                    ("Song Name: ",currentSongs.get(i).getName());
        }

        mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,songs);
        mListView.setAdapter(mArrayAdapter);
    }
}
