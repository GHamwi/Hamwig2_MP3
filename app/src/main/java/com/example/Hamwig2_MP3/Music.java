package com.example.Hamwig2_MP3;

/**************** Created by George B. Hamwi Homework 5 *******************/

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class Music extends Fragment {

    ListView mListView;
    ArrayList<String> musics;
    ArrayAdapter<String> mArrayAdapter;
    String[] songs;


    public Music() {
        /* Required empty public constructor */
    }

    /* to display menu in action bar */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     /* Inflate the layout for this fragment */
        View view =  inflater.inflate(R.layout.fragment_music, container, false);
        mListView = view.findViewById(R.id.musicListView);

        askStoragePermissions();

        return view;
    }

    /* Creates a Arraylist to find the MP3 files on the device */
    public ArrayList<File> findMusics(File file){

        ArrayList<File> musicLists = new ArrayList<File>();

        File[] files = file.listFiles();

        for(File singleFile: files ){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                musicLists.addAll(findMusics(singleFile));
            }else{
                if(singleFile.getName().endsWith(".mp3") ||
                   singleFile.getName().endsWith(".m4a") ||
                   singleFile.getName().endsWith(".wav") ||
                   singleFile.getName().endsWith(".m4b")){
                    musicLists.add(singleFile);
                }
            }
        }
        /* Displays list of the songs */
        return musicLists;
    }

    /* Displays the songs that are in these file formats */
    public void display(){

        final ArrayList<File> allSongs = findMusics(Environment.getExternalStorageDirectory());
        songs = new String[allSongs.size()];

        for(int i=0;i<allSongs.size();i++){
            songs[i] = allSongs.get(i).getName().replace
                    (".mp3","").replace
                    (".m4a","").replace
                    (".wav","").replace
                    (".m4b","");
        }

        mArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,songs);
        mListView.setAdapter(mArrayAdapter);

        /* When a song is clicked it will start playing */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String songName = mListView.getItemAtPosition(position).toString();
                Intent play = new Intent(getActivity(),Player.class);
                play.putExtra("songs",allSongs).putExtra
                             ("songName",songName).putExtra
                             ("position",position);
                startActivity(play);
            }
        });
    }

    /* Asks the user to give permission to the app to access files that are on the device  */
    public void askStoragePermissions(){
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                display();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
            }
        }).check();
    }
}