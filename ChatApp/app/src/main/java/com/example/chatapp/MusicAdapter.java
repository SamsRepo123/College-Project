package com.example.chatapp;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<com.example.chatapp.MusicAdapter.MySongsViewHolder> {

    private Context mContext;
    static ArrayList<MusicFiles> mfiles;
    MusicAdapter(Context mContext, ArrayList<MusicFiles> mfiles)
    {
        this.mfiles = mfiles;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public MySongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_items, parent,false);
        return new MySongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySongsViewHolder holder, int position) {

        holder.file_name.setText((mfiles.get(position).getTitle()));
        byte[] image = getAlbumArt(mfiles.get(position).getPath());
        if(image != null)
        {
            Glide.with(mContext).asBitmap()
                    .load(image)
                    .into(holder.album_art);
        }
        else{
    Glide.with(mContext)
            .load(R.drawable.tekina1)
            .into(holder.album_art);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
        holder.menuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener((item )-> {
                    switch(item.getGroupId()) {
                        case R.id.delete:
                            Toast.makeText(mContext, "Delete Clicked!!", Toast.LENGTH_LONG).show();
                            deleteFile(position, v);
                            break;

                    }
                    return true;
                });
            }
        });
    }
//TODO Delete file option is not working or not implemented properly
    private void deleteFile(int position, View  v) {
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Long.parseLong(mfiles.get(position).getId())); //content://

        File file = new File(mfiles.get(position).getPath());
        boolean deleted = file.delete(); //deletes files
        if(deleted){
            mContext.getContentResolver().delete(contentUri, null,null);
            mfiles.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mfiles.size());
        Snackbar.make(v, "File Deleted :" , Snackbar.LENGTH_LONG)
        .show();
    }
        else{
            //maybe file is stored in sdcard
            Snackbar.make(v, "Can't be deleted :" , Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public int getItemCount() {
        return mfiles.size();
    }


    public class MySongsViewHolder extends RecyclerView.ViewHolder {
        TextView file_name;
        ImageView album_art , menuMore;
        public MySongsViewHolder(@NonNull View itemView) {
            super(itemView);
            file_name = itemView.findViewById(R.id.music_file_name);
            album_art = itemView.findViewById(R.id.music_image);
           menuMore = itemView.findViewById(R.id.menuMore);
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
    void updateList(ArrayList<MusicFiles> musicFilesArrayList){
        mfiles = new ArrayList<>();
        mfiles.addAll(musicFilesArrayList);
        notifyDataSetChanged();
    }
}
