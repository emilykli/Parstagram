package com.example.parstagram.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.Feed.PostsAdapter;
import com.example.parstagram.Posting.Post;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class profileFragment extends Fragment {
    public static final String TAG = "profileFragment";

    private RecyclerView rvGrid;
    private TextView tvUsername;
    private ImageView ivProfilePic;
    private profilePostsAdapter profileAdapter;
    public List<Post> profilePosts;

    public profileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvGrid = view.findViewById(R.id.rvGrid);
        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        profilePosts = new ArrayList<>();
        profileAdapter = new profilePostsAdapter(getContext(), profilePosts);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);

        rvGrid.setAdapter(profileAdapter);
        rvGrid.setLayoutManager(gridLayoutManager);

        tvUsername.setText(ParseUser.getCurrentUser().getUsername());
        ParseFile profilePic = (ParseFile) ParseUser.getCurrentUser().get("profilePicture");
        if (profilePic != null){
            Glide.with(getContext()).load(profilePic.getUrl()).circleCrop().into(ivProfilePic);
        }
        else {
            Glide.with(getContext()).load(R.drawable.default_pfp).circleCrop().into(ivProfilePic);
        }
        profileAdapter.notifyDataSetChanged();
        queryPosts();
    }



    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                profileAdapter.clear();
                profileAdapter.addAll(posts);
                profileAdapter.notifyDataSetChanged();
            }
        });
    }
}
