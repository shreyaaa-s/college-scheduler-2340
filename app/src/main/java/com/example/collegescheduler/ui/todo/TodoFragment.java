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
import com.example.collegescheduler.databinding.FragmentTodoBinding;
import com.example.collegescheduler.ui.exams.ExamsViewModel;

import java.util.ArrayList;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TodoFragment extends Fragment {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> details;
    private ArrayAdapter<String> detailsAdapter;
    private ListView listview;
    private Button button;
    private TodoFragmentViewModel todoViewModel;

    private FragmentTodoBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listview = root.findViewById(R.id.listview);
        button = root.findViewById(R.id.button);

        todoViewModel = new ViewModelProvider(this).get(TodoFragmentViewModel.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(root, view);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        ArrayList<String> items = todoViewModel.getItems();
        itemsAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, items);
        listview.setAdapter(itemsAdapter);
        setUpListViewListener();

        return root;
    }

    private void editItem(int position) {
        String selectedItem = todoViewModel.getItems().get(position);

        String[] parts = selectedItem.split("\n");

        EditText taskInput = getView().findViewById(R.id.editTaskText);
        taskInput.setText(parts[1]);

        EditText detailsInput = getView().findViewById(R.id.editDetailsTextMultiLine);
        detailsInput.setText(parts[3]);

        todoViewModel.removeItem(position);
        Toast.makeText(getActivity(), "Task retrieved", Toast.LENGTH_LONG).show();
        itemsAdapter.notifyDataSetChanged();
    }

    private void setUpListViewListener() {
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Task removed", Toast.LENGTH_LONG).show();
                todoViewModel.removeItem(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editItem(position);
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
            todoViewModel.addItem("TASK: "+ "\n" + taskText + "\n" + "TASK DETAILS: " + "\n" + detailsText);
            taskInput.setText("");
            detailsInput.setText("");
//            Toast.makeText(getActivity().getApplicationContext(), taskText, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
