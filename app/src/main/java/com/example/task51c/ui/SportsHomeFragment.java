package com.example.task51c.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task51c.R;
import com.example.task51c.model.NewsItem;
import com.example.task51c.model.SportsRepository;
import com.example.task51c.ui.adapter.FeaturedAdapter;
import com.example.task51c.ui.adapter.NewsAdapter;

import java.util.List;

public class SportsHomeFragment extends BaseFragment {
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sports_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView featuredRecycler = view.findViewById(R.id.recycler_featured);
        RecyclerView newsRecycler = view.findViewById(R.id.recycler_news);
        EditText filterInput = view.findViewById(R.id.input_filter);

        FeaturedAdapter featuredAdapter = new FeaturedAdapter(item -> navigator().openSportsDetail(item));
        featuredAdapter.submitList(SportsRepository.getFeaturedStories());
        featuredRecycler.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        featuredRecycler.setAdapter(featuredAdapter);
        featuredRecycler.setNestedScrollingEnabled(false);

        newsAdapter = new NewsAdapter(item -> navigator().openSportsDetail(item));
        newsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        newsRecycler.setAdapter(newsAdapter);
        newsRecycler.setNestedScrollingEnabled(false);
        updateNewsList("");

        filterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateNewsList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        view.findViewById(R.id.button_bookmarks).setOnClickListener(v -> navigator().openBookmarks());
        view.findViewById(R.id.button_open_istream_from_sports)
                .setOnClickListener(v -> navigator().openLogin());
        view.findViewById(R.id.button_back_home).setOnClickListener(v -> navigator().openLanding());
    }

    private void updateNewsList(String query) {
        List<NewsItem> items = SportsRepository.filterStories(query);
        newsAdapter.submitList(items);
    }
}
