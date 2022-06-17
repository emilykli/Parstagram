package com.example.parstagram.Profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.parstagram.Feed.DetailActivity;
import com.example.parstagram.Posting.Post;
import com.example.parstagram.R;
import com.parse.ParseFile;

import java.util.List;

public class profilePostsAdapter extends RecyclerView.Adapter<profilePostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public profilePostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.itemView.setTag(post);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivProfileGallery;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileGallery = itemView.findViewById(R.id.ivProfileGallery);

            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(image.getUrl()).override(220, 220).into(ivProfileGallery);
            }
        }

        @Override
        public void onClick(View view) {
            final Post post = (Post) view.getTag();
            if (post != null) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("post", post);
                context.startActivity(i);
            }
        }
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public void addPost(Post p) {
        posts.add(p);
        notifyDataSetChanged();
    }

}
