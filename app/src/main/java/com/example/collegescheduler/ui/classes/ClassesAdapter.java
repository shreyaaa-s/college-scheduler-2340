package com.example.collegescheduler.ui.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesViewHolder> {
    List<ClassesData> list = Collections.emptyList();
    Context context;
    ClassesClickListener listener;

    public ClassesAdapter(List<ClassesData> list, Context context, ClassesClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ClassesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View photoView =
    }


}
