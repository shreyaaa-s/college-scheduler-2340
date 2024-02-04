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

public class ClassesFragment extends Fragment {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;

    private FragmentClassesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        ClassesViewModel classesViewModel =
//                new ViewModelProvider(this).get(ClassesViewModel.class);

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textClasses;
//        classesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        listView = root.findViewById(R.id.listview);
        button = root.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(root, view);
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

        return root;
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Class removed", Toast.LENGTH_LONG).show();

                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }

        });
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
            itemsAdapter.add(courseText + "\n" + instructorText + "\n" + timeText);
            courseInput.setText("");
            instructorInput.setText("");
            timeInput.setText("");
            Toast.makeText(getActivity().getApplicationContext(), "Added new class", Toast.LENGTH_LONG).show();
            itemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}