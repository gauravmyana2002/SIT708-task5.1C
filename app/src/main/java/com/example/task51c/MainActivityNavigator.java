package com.example.task51c;

import com.example.task51c.model.NewsItem;

public interface MainActivityNavigator {
    void openLanding();

    void openSportsHome();

    void openSportsDetail(NewsItem item);

    void openBookmarks();

    void openLogin();

    void openSignup();

    void openIStreamHome(String presetUrl);

    void openPlaylist();
}
