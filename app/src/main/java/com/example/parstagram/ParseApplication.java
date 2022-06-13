package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("0TbLsQDboEnvuEBcKyGYlbu2D6C0mF7SYcGMG87B")
                .clientKey("W46yDbHIQK609VdvREJvXGPn4KS8kLL1xK5Q3wkQ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
