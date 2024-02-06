package com.example.collegescheduler.ui.classes;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;

public class ClassesViewHolder extends RecyclerView.ViewHolder {
    TextView classname;
    TextView instructor;
    TextView time;
    View view;

    ClassesViewHolder(View itemView) {
        super(itemView);
        classname = (TextView)itemView.findViewById(R.id.classname_text);
        instructor = (TextView)itemView.findViewById(R.id.instructor_text);
        time = (TextView)itemView.findViewById(R.id.time_text);
        view = itemView;
    }


}
