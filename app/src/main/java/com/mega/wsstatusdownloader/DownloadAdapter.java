package com.mega.wsstatusdownloader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mega.wsstatusdownloader.R;

import java.io.File;
import java.util.ArrayList;

public class DownloadAdapter extends  RecyclerView.Adapter<DownloadAdapter.DownloadStoryViewHolder>{

    private Context context;
    private ArrayList<Object> filesList;

    public DownloadAdapter(Context context, ArrayList<Object> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @NonNull
    @Override
    public DownloadStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.downlaod_card_row, null, false);
            return new DownloadStoryViewHolder(view);

    }

    public void removeItem(int position){
        filesList.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public void onBindViewHolder(@NonNull DownloadStoryViewHolder holder, int position) {

        final DownloadModel files = (DownloadModel) filesList.get(position);

        if (files.getDownloadUri().toString().endsWith(".mp4")) {
            holder.playIcon.setVisibility(View.VISIBLE);
        } else {
            holder.playIcon.setVisibility(View.INVISIBLE);
        }

        Glide.with(context)
                .load(files.getDownloadUri())
                .into(holder.saveImage);

        String videoPath = files.getDownloadPath();


        holder.playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoPath));
                intent.setDataAndType(Uri.parse(videoPath), "video/mp4");
                context.startActivity(intent);

            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (files.getDownloadUri().toString().endsWith(".mp4")) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("video/mp4");
                    Uri uri = Uri.parse(videoPath);
                    intent.putExtra(Intent.EXTRA_STREAM,uri);
                    context.startActivity(intent);

                } else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/png");
                    Uri uri = Uri.parse(videoPath);
                    intent.putExtra(Intent.EXTRA_STREAM,uri);
                    context.startActivity(intent);
                }

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               File file = new File(videoPath);
               if(file.exists()){
                   holder.card.setVisibility(View.GONE);

                   if(file.delete()){
                       Toast.makeText(v.getContext(), "File Deleted...", Toast.LENGTH_SHORT).show();
                   }else {
                       Toast.makeText(v.getContext(), "File Not Deleted...", Toast.LENGTH_SHORT).show();
                   }
               }
                   
            }
        });


    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public class DownloadStoryViewHolder extends RecyclerView.ViewHolder{
        ImageView playIcon,saveImage,share,delete;
        CardView card;

        public DownloadStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            playIcon=itemView.findViewById(R.id.playButton);
            saveImage=itemView.findViewById(R.id.mainImageView);
            share=itemView.findViewById(R.id.shareID);
            delete=itemView.findViewById(R.id.deleteID);
            card=itemView.findViewById(R.id.card);
        }
    }
}
