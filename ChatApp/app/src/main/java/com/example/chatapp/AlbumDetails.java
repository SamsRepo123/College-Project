package com.example.chatapp;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.chatapp.MusicPlayerActivity.musicFiles;

//TODO Albums are not working watch for Album fragment part 11
public class AlbumDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView albumPhoto;
    String albumName;
    ArrayList<MusicFiles> albumSongs = new ArrayList<>();
    com.example.chatapp.AlbumDetailsAdapter albumDetailsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        recyclerView = findViewById(R.id.recyclerView);
        albumPhoto = findViewById(R.id.albumPhoto);
        albumName = getIntent().getStringExtra("albumName");
        int j = 0;
        for(int i = 0; i < musicFiles.size() ; i ++  ){
            if(albumName.equals(musicFiles.get(i).getAlbum())){
                albumSongs.add(j,musicFiles.get(i));
                j ++;
            }
        }
        byte[] image = getAlbumArt((albumSongs.get(0).getPath()));
        if(image != null ){
            Glide.with(this)
                    .load(image)
                    .into(albumPhoto);
        }
        else{
            Glide.with(this)
                    .load(R.drawable.tekina1)
                    .into(albumPhoto);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!(albumSongs.size() < 1)){
        albumDetailsAdapter = new com.example.chatapp.AlbumDetailsAdapter(this, albumSongs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        }
    }

    private byte[] getAlbumArt(String uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture() ;
        retriever.release();
        return art;
    }
}