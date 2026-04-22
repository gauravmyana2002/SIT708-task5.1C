package com.example.task51c.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.task51c.R;
import com.example.task51c.data.AppDatabase;
import com.example.task51c.data.PlaylistItem;
import com.example.task51c.data.SessionManager;
import com.example.task51c.data.User;

import java.util.regex.Pattern;

public class IStreamHomeFragment extends BaseFragment {
    private static final String ARG_PRESET_URL = "arg_preset_url";
    private static final Pattern VIDEO_ID_PATTERN = Pattern.compile("^[A-Za-z0-9_-]{11}$");

    public static IStreamHomeFragment newInstance(String presetUrl) {
        IStreamHomeFragment fragment = new IStreamHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRESET_URL, presetUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_istream_home, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SessionManager sessionManager = new SessionManager(requireContext());
        int userId = sessionManager.getLoggedInUserId();
        if (userId == -1) {
            navigator().openLogin();
            return;
        }

        AppDatabase database = AppDatabase.getInstance(requireContext());
        User currentUser = database.userDao().getById(userId);

        EditText urlInput = view.findViewById(R.id.input_video_url);
        TextView welcomeView = view.findViewById(R.id.text_istream_welcome);
        WebView webView = view.findViewById(R.id.webview_player);

        welcomeView.setText(getString(R.string.logged_in_as, currentUser == null ? "User" : currentUser.fullName));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setUserAgentString(
                "Mozilla/5.0 (Linux; Android 14; Mobile) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        String presetUrl = getArguments() == null ? null : getArguments().getString(ARG_PRESET_URL);
        if (!TextUtils.isEmpty(presetUrl)) {
            urlInput.setText(presetUrl);
            playVideo(webView, presetUrl);
        }

        view.findViewById(R.id.button_play_video).setOnClickListener(v -> {
            String url = urlInput.getText().toString().trim();
            playVideo(webView, url);
        });

        view.findViewById(R.id.button_add_playlist).setOnClickListener(v -> {
            String url = urlInput.getText().toString().trim();
            ParsedYouTubeTarget target = parseYouTubeTarget(url);
            if (target == null) {
                Toast.makeText(requireContext(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
                return;
            }
            database.playlistDao().insert(new PlaylistItem(userId, url, target.getStorageValue(),
                    System.currentTimeMillis()));
            Toast.makeText(requireContext(), R.string.playlist_added, Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.button_my_playlist).setOnClickListener(v -> navigator().openPlaylist());
        view.findViewById(R.id.button_open_external).setOnClickListener(v -> {
            String url = normalizeUrl(urlInput.getText().toString().trim());
            ParsedYouTubeTarget target = parseYouTubeTarget(url);
            if (target == null) {
                Toast.makeText(requireContext(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(target.toWatchUrl())));
        });
        view.findViewById(R.id.button_logout_home).setOnClickListener(v -> {
            sessionManager.logout();
            navigator().openLogin();
        });
        view.findViewById(R.id.button_back_to_landing_from_istream)
                .setOnClickListener(v -> navigator().openLanding());
    }

    private void playVideo(WebView webView, String url) {
        ParsedYouTubeTarget target = parseYouTubeTarget(normalizeUrl(url));
        if (target == null) {
            Toast.makeText(requireContext(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
            return;
        }

        String embedUrl = target.toEmbedUrl()
                + (target.toEmbedUrl().contains("?") ? "&" : "?")
                + "autoplay=1&playsinline=1&enablejsapi=1"
                + "&origin=https://www.youtube.com&widget_referrer=https://www.youtube.com";
        String html = "<!DOCTYPE html><html><head>"
                + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "<style>html,body{margin:0;padding:0;background:#0f172a;height:100%;overflow:hidden;}"
                + "#player{position:absolute;inset:0;border:0;width:100%;height:100%;}</style>"
                + "</head><body>"
                + "<iframe id='player' type='text/html' src='" + embedUrl + "' "
                + "allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' "
                + "referrerpolicy='origin' allowfullscreen></iframe>"
                + "</body></html>";
        webView.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "utf-8", null);
    }

    @Nullable
    private ParsedYouTubeTarget parseYouTubeTarget(String url) {
        String normalizedUrl = normalizeUrl(url);
        if (TextUtils.isEmpty(normalizedUrl)) {
            return null;
        }

        Uri uri = Uri.parse(normalizedUrl);
        String host = uri.getHost();
        if (host == null) {
            return null;
        }

        String normalizedHost = host.toLowerCase();
        if (!normalizedHost.contains("youtube.com") && !normalizedHost.contains("youtu.be")) {
            return null;
        }

        String playlistId = uri.getQueryParameter("list");
        String videoId = uri.getQueryParameter("v");

        if (normalizedHost.contains("youtu.be")) {
            String pathSegment = uri.getLastPathSegment();
            if (isValidVideoId(pathSegment)) {
                videoId = pathSegment;
            }
        }

        if (!isValidVideoId(videoId)) {
            String path = uri.getPath();
            if (!TextUtils.isEmpty(path)) {
                String[] segments = path.split("/");
                for (int i = 0; i < segments.length; i++) {
                    String segment = segments[i];
                    if (("shorts".equals(segment) || "embed".equals(segment) || "live".equals(segment))
                            && i + 1 < segments.length && isValidVideoId(segments[i + 1])) {
                        videoId = segments[i + 1];
                        break;
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(playlistId) && isValidPlaylistId(playlistId)) {
            return new ParsedYouTubeTarget(videoId, playlistId);
        }

        if (isValidVideoId(videoId)) {
            return new ParsedYouTubeTarget(videoId, null);
        }

        return null;
    }

    @Nullable
    private String normalizeUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String trimmed = url.trim();
        if (trimmed.startsWith("//")) {
            return "https:" + trimmed;
        }
        if (!trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
            return "https://" + trimmed;
        }
        return trimmed;
    }

    private boolean isValidVideoId(@Nullable String value) {
        return !TextUtils.isEmpty(value) && VIDEO_ID_PATTERN.matcher(value).matches();
    }

    private boolean isValidPlaylistId(@Nullable String value) {
        return !TextUtils.isEmpty(value) && value.length() >= 10;
    }

    private static class ParsedYouTubeTarget {
        @Nullable
        private final String videoId;
        @Nullable
        private final String playlistId;

        ParsedYouTubeTarget(@Nullable String videoId, @Nullable String playlistId) {
            this.videoId = videoId;
            this.playlistId = playlistId;
        }

        String toEmbedUrl() {
            if (!TextUtils.isEmpty(playlistId) && !TextUtils.isEmpty(videoId)) {
                return "https://www.youtube.com/embed/" + videoId + "?list=" + playlistId;
            }
            if (!TextUtils.isEmpty(playlistId)) {
                return "https://www.youtube.com/embed/videoseries?list=" + playlistId;
            }
            return "https://www.youtube.com/embed/" + videoId;
        }

        String getStorageValue() {
            if (!TextUtils.isEmpty(playlistId) && TextUtils.isEmpty(videoId)) {
                return "playlist:" + playlistId;
            }
            return videoId;
        }

        String toWatchUrl() {
            if (!TextUtils.isEmpty(playlistId) && !TextUtils.isEmpty(videoId)) {
                return "https://www.youtube.com/watch?v=" + videoId + "&list=" + playlistId;
            }
            if (!TextUtils.isEmpty(playlistId)) {
                return "https://www.youtube.com/playlist?list=" + playlistId;
            }
            return "https://www.youtube.com/watch?v=" + videoId;
        }
    }
}
