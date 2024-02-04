package com.example.collegescheduler.ui.todo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentClassesBinding;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    private ArrayList<String> task;
    private ArrayAdapter<String> taskAdapter;
    private ArrayList<String> details;
    private ArrayAdapter<String> detailsAdapter;
    private ListView listview;
    private Button button;

    private FragmentClassesBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listview = root.findViewById(R.id.listview);
        button = root.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(root, view);
            }
        });

        task = new ArrayList<>();
        taskAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, task);
        listview.setAdapter(taskAdapter);
        setUpListViewListener();

        return root;
    }

    private void setUpListViewListener() {
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Task removed", Toast.LENGTH_LONG).show();
                task.remove(position);
                taskAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void addItem(View root, View view) {
        EditText taskInput = root.findViewById(R.id.editTaskText);
        String taskText = taskInput.getText().toString();
        EditText detailsInput = root.findViewById(R.id.editDetailsTextMultiLine);
        String detailsText = detailsInput.getText().toString();

        if(taskText.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a task", Toast.LENGTH_LONG).show();
        } else if(detailsText.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter task details", Toast.LENGTH_LONG).show();
        } else {
            taskAdapter.add(taskText + "\n" + detailsText);
            taskInput.setText("");
            detailsAdapter.add(taskText + "\n" + detailsText);
            detailsInput.setText("");
//            Toast.makeText(getActivity().getApplicationContext(), taskText, Toast.LENGTH_LONG).show();
            taskAdapter.notifyDataSetChanged();
            detailsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
