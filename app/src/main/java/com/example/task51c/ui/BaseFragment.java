package com.example.task51c.ui;

import androidx.fragment.app.Fragment;

import com.example.task51c.MainActivityNavigator;

public abstract class BaseFragment extends Fragment {
    protected MainActivityNavigator navigator() {
        return (MainActivityNavigator) requireActivity();
    }
}
