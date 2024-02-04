package com.example.collegescheduler.ui.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.collegescheduler.databinding.FragmentClassesBinding;

public class TodoFragment extends Fragment {

    private FragmentClassesBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}
