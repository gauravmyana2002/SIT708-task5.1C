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
import com.example.task51c.data.User;

public class SignupFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText fullNameInput = view.findViewById(R.id.input_signup_full_name);
        EditText usernameInput = view.findViewById(R.id.input_signup_username);
        EditText passwordInput = view.findViewById(R.id.input_signup_password);
        EditText confirmPasswordInput = view.findViewById(R.id.input_signup_confirm_password);

        view.findViewById(R.id.button_create_account).setOnClickListener(v -> {
            String fullName = fullNameInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();

            if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(username)
                    || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(requireContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(requireContext(), R.string.password_mismatch, Toast.LENGTH_SHORT).show();
                return;
            }
            if (AppDatabase.getInstance(requireContext()).userDao().findByUsername(username) != null) {
                Toast.makeText(requireContext(), R.string.username_taken, Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase.getInstance(requireContext()).userDao().insert(new User(fullName, username, password));
            Toast.makeText(requireContext(), R.string.account_created, Toast.LENGTH_SHORT).show();
            navigator().openLogin();
        });

        view.findViewById(R.id.button_back_from_signup)
                .setOnClickListener(v -> requireActivity().onBackPressed());
    }
}
