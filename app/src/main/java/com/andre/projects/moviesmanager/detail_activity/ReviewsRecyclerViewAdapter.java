package com.andre.projects.moviesmanager.detail_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andre.projects.moviesmanager.R;
import com.andre.projects.moviesmanager.detail_activity.utils.Review;

import java.util.ArrayList;
import java.util.List;

//Class that creates and manages the adapter for the recyclerview that presents all the reviews
public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder> {

    private List<Review> mDataReview;

    public ReviewsRecyclerViewAdapter() {
        this.mDataReview = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater;
        mInflater = LayoutInflater.from(parent.getContext());
        View v = mInflater.inflate(R.layout.review_rv_content, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bind(mDataReview.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataReview.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.review_name);
            content = (TextView) itemView.findViewById(R.id.review_content);
        }

        public void bind(Review review) {
            author.setText(review.getAuthor());
            content.setText(review.getContent());
        }

    }

    public void setReviews(List<Review> reviews) {
        this.mDataReview = reviews;
        notifyDataSetChanged();
    }

}
