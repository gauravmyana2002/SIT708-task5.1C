package com.example.task51c;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.task51c.model.NewsItem;
import com.example.task51c.ui.BookmarksFragment;
import com.example.task51c.ui.IStreamHomeFragment;
import com.example.task51c.ui.LandingFragment;
import com.example.task51c.ui.LoginFragment;
import com.example.task51c.ui.PlaylistFragment;
import com.example.task51c.ui.SignupFragment;
import com.example.task51c.ui.SportsDetailFragment;
import com.example.task51c.ui.SportsHomeFragment;

public class MainActivity extends AppCompatActivity implements MainActivityNavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            replaceRoot(new LandingFragment(), false);
        }
    }

    @Override
    public void openLanding() {
        replaceRoot(new LandingFragment(), false);
    }

    @Override
    public void openSportsHome() {
        replaceRoot(new SportsHomeFragment(), true);
    }

    @Override
    public void openSportsDetail(NewsItem item) {
        replaceRoot(SportsDetailFragment.newInstance(item), true);
    }

    @Override
    public void openBookmarks() {
        replaceRoot(new BookmarksFragment(), true);
    }

    @Override
    public void openLogin() {
        replaceRoot(new LoginFragment(), true);
    }

    @Override
    public void openSignup() {
        replaceRoot(new SignupFragment(), true);
    }

    @Override
    public void openIStreamHome(String presetUrl) {
        replaceRoot(IStreamHomeFragment.newInstance(presetUrl), true);
    }

    @Override
    public void openPlaylist() {
        replaceRoot(new PlaylistFragment(), true);
    }

    private void replaceRoot(Fragment fragment, boolean addToBackStack) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        } else {
            getSupportFragmentManager().popBackStack(null,
                    androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        transaction.commit();
    }
}
