package com.andre.projects.moviesmanager.main_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andre.projects.moviesmanager.main_activity.utils.Movie;
import com.andre.projects.moviesmanager.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Movie> mData;
    private LayoutInflater mInflater;
    private static ItemClickListener mClickListener;

    public RecyclerViewAdapter() {
        this.mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.recycler_view_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mAnimeImageButton;

        public ViewHolder(View v){
            super(v);
            mAnimeImageButton = v.findViewById(R.id.animeImage);
            mAnimeImageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

        public void bind(Movie movie){
            Picasso.with(mAnimeImageButton.getContext()).load("https://image.tmdb.org/t/p/w500"+movie.getPosterPath()).into(mAnimeImageButton);
        }

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setMovies (List<Movie> movies){
        this.mData = movies;
        notifyDataSetChanged();
    }

}
