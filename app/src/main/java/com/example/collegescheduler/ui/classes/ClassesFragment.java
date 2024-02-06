package com.example.collegescheduler.ui.classes;

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
import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClassesFragment extends Fragment {

    private ArrayList<String> items;
    private ClassesViewModel classesViewModel;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private List<ClassesData> list;
    private RecyclerView recyclerView;



    private FragmentClassesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textClasses;
//        classesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        listView = root.findViewById(R.id.listview);
        button = root.findViewById(R.id.button);
        classesViewModel = new ViewModelProvider(this).get(ClassesViewModel.class);
        recyclerView
                = (RecyclerView)root.findViewById(
                R.id.recyclerView);

//        adapter
//                = new ClassesAdapter(
//                list, getApplication(),listiner);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(
//                new LinearLayoutManager(classes.this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(root, view);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        ArrayList<String> items = classesViewModel.getItems();
        itemsAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

        return root;
    }

    private void addItem(View root, View view) {
        EditText courseInput = root.findViewById(R.id.editCourseText);
        String courseText = courseInput.getText().toString();
        EditText instructorInput = root.findViewById(R.id.editInstructorText);
        String instructorText = instructorInput.getText().toString();
        EditText timeInput = root.findViewById(R.id.editTimeText);
        String timeText = timeInput.getText().toString();

        if(courseText.equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a class", Toast.LENGTH_LONG).show();
        } else if (instructorText.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter an instructor", Toast.LENGTH_LONG).show();
        } else if (timeText.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a time", Toast.LENGTH_LONG).show();
        } else{
            classesViewModel.addItem(courseText + "\n" + instructorText + "\n" + timeText);
            courseInput.setText("");
            instructorInput.setText("");
            timeInput.setText("");
            Toast.makeText(getActivity().getApplicationContext(), "Added new class", Toast.LENGTH_LONG).show();
        }


        list = getData(courseText, instructorText, timeText);

    }

    private void editItem(int position) {
        String selectedItem = items.get(position);

        String[] parts = selectedItem.split("\n");

        EditText courseInput = getView().findViewById(R.id.editCourseText);
        courseInput.setText(parts[0]);

        EditText instructorInput = getView().findViewById(R.id.editInstructorText);
        instructorInput.setText(parts[1]);

        EditText timeInput = getView().findViewById(R.id.editTimeText);
        timeInput.setText(parts[2]);

        Toast.makeText(getActivity(),"Course details retrieved",Toast.LENGTH_LONG).show();

        classesViewModel.removeItem(position);
        itemsAdapter.notifyDataSetChanged();
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context,"Course details removed",Toast.LENGTH_LONG).show();
                classesViewModel.removeItem(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        // Add click listener for editing an item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editItem(position);
            }
        });
    }


    private List<ClassesData> getData(String classname, String instructor, String time) {
        List<ClassesData> list = new ArrayList<>();
        list.add(new ClassesData(classname, instructor, time));
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}