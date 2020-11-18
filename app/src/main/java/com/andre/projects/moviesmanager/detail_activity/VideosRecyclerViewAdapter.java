package com.andre.projects.moviesmanager.detail_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andre.projects.moviesmanager.R;
import com.andre.projects.moviesmanager.detail_activity.utils.Video;

import java.util.ArrayList;
import java.util.List;

//Class that creates and manages the adapter for the recyclerview that presents all the trailers, also implementing an onClick method for each element
public class VideosRecyclerViewAdapter extends RecyclerView.Adapter<VideosRecyclerViewAdapter.ViewHolder> {

    private List<Video> mDataVideo;
    private static ItemClickListener mClickListener;

    public VideosRecyclerViewAdapter() {
        this.mDataVideo = new ArrayList<>();
    }

    @NonNull
    @Override
    public VideosRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater;
        mInflater = LayoutInflater.from(parent.getContext());
        View v = mInflater.inflate(R.layout.video_rv_content, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bind(mDataVideo.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataVideo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.video_name);
            name.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

        public void bind(Video video){
            name.setText(video.getName());
        }

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setVideo(List<Video> videos) {
        this.mDataVideo = videos;
        notifyDataSetChanged();
    }

}
