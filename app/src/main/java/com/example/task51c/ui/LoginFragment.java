package com.example.task51c.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.task51c.R;
import com.example.task51c.data.AppDatabase;
import com.example.task51c.data.SessionManager;
import com.example.task51c.data.User;

public class LoginFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SessionManager sessionManager = new SessionManager(requireContext());
        if (sessionManager.isLoggedIn()) {
            navigator().openIStreamHome(null);
            return;
        }

        EditText usernameInput = view.findViewById(R.id.input_login_username);
        EditText passwordInput = view.findViewById(R.id.input_login_password);

        view.findViewById(R.id.button_login).setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            User user = AppDatabase.getInstance(requireContext()).userDao().login(username, password);
            if (user == null) {
                Toast.makeText(requireContext(), R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                return;
            }

            sessionManager.login(user.id);
            Toast.makeText(requireContext(), getString(R.string.welcome_user, user.fullName),
                    Toast.LENGTH_SHORT).show();
            navigator().openIStreamHome(null);
        });

        view.findViewById(R.id.button_go_signup).setOnClickListener(v -> navigator().openSignup());
        view.findViewById(R.id.button_back_from_login).setOnClickListener(v -> navigator().openLanding());
    }
}
