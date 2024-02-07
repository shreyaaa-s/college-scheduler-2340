package com.example.collegescheduler.ui.assignments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAssignmentsBinding;
import com.example.collegescheduler.ui.exams.ExamsViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.text.SimpleDateFormat;

public class AssignmentsFragment extends Fragment {

    private AssignmentsViewModel assignmentsViewModel;

    private FragmentAssignmentsBinding binding;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private DatePickerDialog datePickerDialogue;
//    private Button dateButton;
    private Button datePickerButton;
    private Button sortButtonDate;
    private Button sortButtonClass;
//    private Calendar calendar;
    private TextView editDateText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(binding.getRoot());
        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.listView);
        button = root.findViewById(R.id.button);
        datePickerButton = root.findViewById(R.id.datePickerButton);
        editDateText = root.findViewById(R.id.editDateText);
        sortButtonDate = root.findViewById(R.id.sortButtonDate);
        sortButtonClass = root.findViewById(R.id.sortButtonClass);

        assignmentsViewModel = new ViewModelProvider(this).get(AssignmentsViewModel.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addItem(view, root);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        //Sorting changes

        sortButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentsViewModel.sortArray(3);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        sortButtonClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentsViewModel.sortArray(5);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDatePickerDialog();
            }
        });

        ArrayList<String> items = assignmentsViewModel.getItems();
        itemsAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();


        return root;
    }


    private void showDatePickerDialog(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);
                        String formattedDate = dateFormat.format(selectedDate.getTime());
                        editDateText.setText(formattedDate);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return month + "/" + dayOfMonth + "/" + year;
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Assignment Removed", Toast.LENGTH_LONG).show();

                assignmentsViewModel.removeItem(position);
//                items.remove(position);
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
            Toast.makeText(getActivity().getApplicationContext(), "Please enter assignment name.", Toast.LENGTH_LONG).show();
        } else if(dateText.equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Please enter assignment due date.", Toast.LENGTH_LONG).show();
        } else if(classText.equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Please enter class.", Toast.LENGTH_LONG).show();
        } else{
//            itemsAdapter.add(nameText + "\n" + dateText + "\n" + classText);
            assignmentsViewModel.addItem("ASSIGNMENT: "+ "\n" + nameText + "\n"+ "DUE: "+ "\n" + dateText + "\n" + "CLASS: "+ "\n" + classText);
            nameInput.setText("");
            dateInput.setText("");
            classInput.setText("");
            Toast.makeText(getActivity().getApplicationContext(), "Added new class", Toast.LENGTH_LONG).show();
//            itemsAdapter.notifyDataSetChanged();
        }
    }

    private void editItem(int position){
        // Fetch the item to edit
        String selectedItem = assignmentsViewModel.getItems().get(position);

        // Split the item into name, time, and location
        String[] parts = selectedItem.split("\n");

        // Set the values of EditText fields to the selected item's values
        EditText nameInput = getView().findViewById(R.id.editNameText);
        nameInput.setText(parts[1]);

        EditText dateInput = getView().findViewById(R.id.editDateText);
        dateInput.setText(parts[3]);

        EditText classInput = getView().findViewById(R.id.editClassText);
        classInput.setText(parts[5]);

        // Remove the selected item from the list
        assignmentsViewModel.removeItem(position);
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}