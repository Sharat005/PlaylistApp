package com.example.musicapp478;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    ArrayList<Integer> imagelist;
    MainActivity.VideoListener listener;

    private String[] songlist;
    private String[] artistlist;
    private String[] singerdetails;
    private String[] songdetails;
    private Context context;



    // constructor to assign initial values
    RecyclerAdapter(String[] songList, String[] artistList, ArrayList<Integer> imageList, String[] singerDetails, String[] songDetails,  MainActivity.VideoListener listener){
        this.songlist=songList;
        this.artistlist=artistList;
        this.imagelist=imageList;
        this.singerdetails = singerDetails;
        this.songdetails = songDetails;
        this.listener=listener;
        this.context = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {
        View selected_view;
        TextView song_name, song_artist;
        ImageView song_image;
        MainActivity.VideoListener listener;

        public String url;
        int position;

        int total_menu_items = 3;

        public ViewHolder(@NonNull View itemView, MainActivity.VideoListener listener) {
            super(itemView);
            song_name = (TextView) itemView.findViewById(R.id.title);
            song_artist = (TextView) itemView.findViewById(R.id.artist);
            song_image = (ImageView) itemView.findViewById(R.id.imageView);
            this.listener = listener;

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        // Creates the menu items
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            selected_view = v;
            position = getAdapterPosition();
            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.context_menu, menu);

            for (int i = 0; i < total_menu_items; i++) {
                menu.getItem(i).setOnMenuItemClickListener(onMenu);
            }
        }

        // Method to call listener which redirects to the video clip
        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }

        public MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener() {

            // Based on the context option selected redirect the user to respective page
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.watchVideo:
                        onClick(selected_view);
                        return true;

                    case R.id.songInfo:
                        url = songdetails[position];
                        Log.i("url value", " " + url);
                        goToWebPage(url);
                        return true;

                    case R.id.artistInfo:
                        url = singerdetails[position];
                        Log.i("url info", " " + singerdetails[position]);
                        goToWebPage(url);
                        return true;

                    default:
                        return false;

                }
            }
        };

        // helper function to redirect to wiki page
        public void goToWebPage(String url){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            selected_view.getContext().startActivity(intent);
        }
    }

    // creates the view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View vh=layoutInflater.inflate(R.layout.main_layout,parent,false);
        ViewHolder viewholder=new ViewHolder(vh,listener);
        return viewholder;
    }

    // To bind each viewholder to the adapter and set the position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.song_name.setText(songlist[position]);
        holder.song_artist.setText(artistlist[position]);
        holder.song_image.setImageResource(imagelist.get(position));
    }

    // Returns total number of items in the data
    @Override
    public int getItemCount() {
        return imagelist.size();
    }
}
