package com.example.collegescheduler.ui.exams;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentExamsBinding;

import android.app.DatePickerDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.jar.Attributes;

//changes

import androidx.lifecycle.ViewModelProvider;


public class ExamsFragment extends Fragment {

    private ExamsViewModel examsViewModel;
    private FragmentExamsBinding binding;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listview;
    private Button button;
    private Button datePickerButton;
    private TextView editDateText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listview = root.findViewById(R.id.ListViewExam);
        button = root.findViewById((R.id.buttonExam));
        datePickerButton = root.findViewById(R.id.datePickerButton);
        editDateText = root.findViewById(R.id.editDateText);


        examsViewModel = new ViewModelProvider(this).get(ExamsViewModel.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v, root);
                itemsAdapter.notifyDataSetChanged();
            }
        });
        ArrayList<String> items = examsViewModel.getItems();
        itemsAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, items);
        listview.setAdapter(itemsAdapter);
        setUpListViewListener();

        return root;
    }

    private void setUpListViewListener() {
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Exam details removed", Toast.LENGTH_LONG).show();
                examsViewModel.removeItem(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "Editing exam details",Toast.LENGTH_LONG).show();
                editItem(position);
            }
        });
        datePickerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDatePickerDialog();
            }
        });
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

    private void addItem(View v, View root) {
        EditText Nameinput = root.findViewById(R.id.editTextExamName);
        String NameText = Nameinput.getText().toString();

        EditText Timeinput = root.findViewById(R.id.editTextExamTime);
        String TimeText = Timeinput.getText().toString();

        EditText Dateinput = root.findViewById(R.id.editDateText);
        String DateText = Dateinput.getText().toString();

        EditText Locationinput = root.findViewById(R.id.editTextExamLocation);
        String LocationText = Locationinput.getText().toString();

        if (!(NameText.equals("")) && !(TimeText.equals("")) && !(LocationText.equals(""))) {
            examsViewModel.addItem("CLASS: " + "\n" + NameText + "\n" + "TIME: "+ "\n" + TimeText +"\n" + "DATE: "+"\n" + DateText + "\n" + "LOCATION: "+ "\n" + LocationText);
            Nameinput.setText("");
            Timeinput.setText("");
            Dateinput.setText("");
            Locationinput.setText("");
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Fill in missing fields", Toast.LENGTH_LONG).show();
        }
    }

    private void editItem(int position) {
        String selectedItem = examsViewModel.getItems().get(position);
        String[] parts = selectedItem.split("\n");

        EditText Nameinput = getView().findViewById(R.id.editTextExamName);
        Nameinput.setText(parts[1]);

        EditText Timeinput = getView().findViewById(R.id.editTextExamTime);
        Timeinput.setText(parts[3]);

        EditText Dateinput = getView().findViewById(R.id.editTextExamLocation);
        Dateinput.setText(parts[5]);

        EditText Locationinput = getView().findViewById(R.id.editTextExamLocation);
        Locationinput.setText(parts[7]);

        examsViewModel.removeItem(position);
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}