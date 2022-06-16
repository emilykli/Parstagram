package com.example.parstagram.Feed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parstagram.Posting.Post;
import com.example.parstagram.R;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private TextView tvUsername;
    private ImageView ivPostImage;
    private TextView tvDescription;
    private TextView tvTime;

    private Date createdAt;
    private String timeAgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if (getIntent().getExtras() != null) {
            Post post = (Post) getIntent().getParcelableExtra("post");

            tvUsername = findViewById(R.id.tvUsername);
            tvUsername.setText(post.getUser().getUsername());

            ivPostImage = findViewById(R.id.ivPostImage);
            Glide.with(this).load(post.getImage().getUrl()).into(ivPostImage);

            tvDescription = findViewById(R.id.tvDescription);
            tvDescription.setText(post.getDescription());

            createdAt = post.getCreatedAt();
            timeAgo = Post.calculateTimeAgo(createdAt);
            tvTime = findViewById(R.id.tvTime);
            tvTime.setText(timeAgo);
        }
    }
}