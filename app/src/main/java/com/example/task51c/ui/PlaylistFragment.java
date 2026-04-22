package com.example.task51c.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task51c.R;
import com.example.task51c.data.AppDatabase;
import com.example.task51c.data.PlaylistItem;
import com.example.task51c.data.SessionManager;
import com.example.task51c.ui.adapter.PlaylistAdapter;

import java.util.List;

public class PlaylistFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SessionManager sessionManager = new SessionManager(requireContext());
        int userId = sessionManager.getLoggedInUserId();
        if (userId == -1) {
            navigator().openLogin();
            return;
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_playlist);
        TextView emptyView = view.findViewById(R.id.text_empty_playlist);
        List<PlaylistItem> items = AppDatabase.getInstance(requireContext()).playlistDao().getPlaylistForUser(userId);

        PlaylistAdapter adapter = new PlaylistAdapter(item -> navigator().openIStreamHome(item.videoUrl));
        adapter.submitList(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        emptyView.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(items.isEmpty() ? View.GONE : View.VISIBLE);

        view.findViewById(R.id.button_back_from_playlist)
                .setOnClickListener(v -> requireActivity().onBackPressed());
        view.findViewById(R.id.button_logout_playlist).setOnClickListener(v -> {
            sessionManager.logout();
            navigator().openLogin();
        });
    }
}
