package com.example.parstagram.Feed;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.Posting.Post;
import com.example.parstagram.R;
import com.parse.ParseFile;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
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

        private ImageView ivProfilePicture;
        private TextView tvUsername;
        private ImageView ivPostImage;
        private TextView tvDescription;
        private TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            tvTime = itemView.findViewById(R.id.tvTime);

            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(image.getUrl()).into(ivPostImage);
            }

            ParseFile profilePicture = (ParseFile) post.getUser().get("profilePicture");
            if (profilePicture != null){
                Glide.with(context).load(profilePicture.getUrl()).circleCrop().into(ivProfilePicture);
            }
            else {
                Glide.with(context).load(R.drawable.default_pfp).circleCrop().into(ivProfilePicture);
            }

            Date createdAt = post.getCreatedAt();
            String timeAgo = Post.calculateTimeAgo(createdAt);
            tvTime.setText(timeAgo);
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
