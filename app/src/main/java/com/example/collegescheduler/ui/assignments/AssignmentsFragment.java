package com.example.collegescheduler.ui.assignments;

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
import com.example.collegescheduler.databinding.FragmentAssignmentsBinding;

import java.util.ArrayList;

public class AssignmentsFragment extends Fragment {

    private FragmentAssignmentsBinding binding;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(binding.getRoot());
        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.listView);
        button = root.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view, root);
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

//        AssignmentsViewModel assignmentsViewModel =
//                new ViewModelProvider(this).get(AssignmentsViewModel.class);
//
//        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textAssignments;
//        assignmentsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Assignment Removed", Toast.LENGTH_LONG).show();

                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editItem(position);
            }
        });
    }

    private void addItem(View view, View root) {
        EditText nameInput = root.findViewById(R.id.editNameText);
        String nameText = nameInput.getText().toString();
        EditText dateInput = root.findViewById(R.id.editDateText);
        String dateText = dateInput.getText().toString();
        EditText classInput = root.findViewById(R.id.editClassText);
        String classText = classInput.getText().toString();

        if(nameText.equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Please enter assignment name.", Toast.LENGTH_LONG);
        } else if(dateText.equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Please enter assignment due date.", Toast.LENGTH_LONG);
        } else if(classText.equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Please enter class.", Toast.LENGTH_LONG);
        } else{
            itemsAdapter.add(nameText + "\n" + dateText + "\n" + classText);
            nameInput.setText("");
            dateInput.setText("");
            classInput.setText("");
            Toast.makeText(getActivity().getApplicationContext(), "Added new class", Toast.LENGTH_LONG).show();
            itemsAdapter.notifyDataSetChanged();
        }
    }

    private void editItem(int position){
        // Fetch the item to edit
        String selectedItem = items.get(position);

        // Split the item into name, time, and location
        String[] parts = selectedItem.split("\n");

        // Set the values of EditText fields to the selected item's values
        EditText Nameinput = getView().findViewById(R.id.editNameText);
        Nameinput.setText(parts[0]);

        // Remove the selected item from the list
        items.remove(position);
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}