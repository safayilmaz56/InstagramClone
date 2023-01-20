package com.example.instagramclon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclon.R;
import com.example.instagramclon.databinding.RecycleRowBinding;
import com.example.instagramclon.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private ArrayList<Post> postArrayList;

    public PostAdapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }

    class PostHolder extends RecyclerView.ViewHolder{
        RecycleRowBinding recycleRowBinding;

        public PostHolder(RecycleRowBinding recycleRowBinding) {
            super(recycleRowBinding.getRoot());
            this.recycleRowBinding=recycleRowBinding;
        }
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleRowBinding recycleRowBinding=RecycleRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recycleRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.recycleRowBinding.fullNameUsernameText
                .setText(postArrayList.get(position).userName);
        holder.recycleRowBinding.userCommentText.setText(postArrayList.get(position).comment);
        holder.recycleRowBinding.postDateText.setText(postArrayList.get(position).date);
        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.recycleRowBinding.userPostImage);
        if (postArrayList.get(position).pphoto != null) {
            Picasso.get().load(postArrayList.get(position).pphoto).into(holder.recycleRowBinding.userPphoto);
        }     else {

        holder.recycleRowBinding.userPphoto.setImageResource(R.drawable.defaultpp);
    }
}



    @Override
    public int getItemCount() {
        return postArrayList.size();
    }


}
