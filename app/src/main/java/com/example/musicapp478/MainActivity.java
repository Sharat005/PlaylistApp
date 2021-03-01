package com.example.musicapp478;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static android.util.Log.i;

public class MainActivity extends AppCompatActivity {

    private String[] songList;
    private String[] artistList;
    private String[] singerDetails;
    private String[] songDetails;

    private ArrayList<Integer> imageList = new ArrayList<>(
            Arrays.asList(R.drawable.heyyou, R.drawable.shape_of_you, R.drawable.hotel_california, R.drawable.stitches, R.drawable.mockingbird,R.drawable.blank_space, R.drawable.senorita,R.drawable.attention,R.drawable.without_me,R.drawable.not_afraid,R.drawable.roar,R.drawable.heathens));

    ArrayList<String> urlList;
    private int SelectedView;

    RecyclerView recycle;

    interface VideoListener {
        void onClick(View v, int position);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> url = Arrays.asList("https://www.youtube.com/watch?v=DEDjF2tLbhk", "https://www.youtube.com/watch?v=JGwWNGJdvx8", "https://www.youtube.com/watch?v=811QZGDysx0", "https://www.youtube.com/watch?v=VbfpW0pbvaU", "https://www.youtube.com/watch?v=S9bCLPwzSC0","https://www.youtube.com/watch?v=e-ORhEE9VVg","https://www.youtube.com/watch?v=Pkh8UtuejGw","https://www.youtube.com/watch?v=nfs8NYg7yQM","https://www.youtube.com/watch?v=ZAfAud_M_mg","https://www.youtube.com/watch?v=j5-yKhDd64s","https://www.youtube.com/watch?v=CevxZvSJLk8", "https://www.youtube.com/watch?v=UprcpdwuwCg");

        // Fetch resources from res values folder
        songList = getResources().getStringArray(R.array.songName);
        artistList = getResources().getStringArray(R.array.singerName);
        singerDetails = getResources().getStringArray(R.array.SingerDetails);
        songDetails = getResources().getStringArray(R.array.SongDetails);
        urlList = new ArrayList<>();
        urlList.addAll(url);

        recycle=(RecyclerView)findViewById(R.id.recyclerview);


        VideoListener listener = (View v, int position) -> {
            // Open the song's video in youtube
            Intent browser = new Intent();
            browser.setAction(Intent.ACTION_VIEW);
            browser.setData(Uri.parse(urlList.get(position)));
            browser.addCategory(Intent.CATEGORY_BROWSABLE);

            startActivity(browser);
        };


        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(songList, artistList, imageList, singerDetails, songDetails,  listener);
        recycle.setHasFixedSize(true);
        recycle.setAdapter(recyclerAdapter);

        recycle.setLayoutManager(new LinearLayoutManager(this));

        registerForContextMenu(recycle);
    }

    // helper function to change layout using Recyclerview's layout manager
    public void setLayoutStyle(RecyclerView recycle, String layoutName, int grid_number) {
        if (layoutName == "linear layout") {
            recycle.setLayoutManager(new LinearLayoutManager(this));
        } else if (layoutName == "grid layout") {
            recycle.setLayoutManager(new GridLayoutManager(this, grid_number));
        }
    }

    // Fetch options menu from res folder and create options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    // Switch between list view and grid view based on option selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SelectedView = item.getItemId();
        switch (item.getItemId()) {
            case R.id.list:
                setLayoutStyle(recycle, "linear layout", 0);
                return true;
            case R.id.grid:
                setLayoutStyle(recycle, "grid layout", 2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // Saving the current state .
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("SelectedView", SelectedView);
    }

    // Fetch saved instance stored in bundle when activity is restarted
    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        SelectedView = savedInstanceState.getInt("SelectedView");
        if(SelectedView == R.id.grid)
        {
            recycle.setLayoutManager(new GridLayoutManager(this, 2)); // setting to gridview on rotation.
        }
    }

}