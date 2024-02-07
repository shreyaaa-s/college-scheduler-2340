package com.example.collegescheduler.ui.exams;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentExamsBinding;

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

import java.util.ArrayList;
import java.util.jar.Attributes;

//changes

import androidx.lifecycle.ViewModelProvider;


public class ExamsFragment extends Fragment {

    private ExamsViewModel examsViewModel;
    private FragmentExamsBinding binding;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listview;
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listview = root.findViewById(R.id.ListViewExam);
        button = root.findViewById((R.id.buttonExam));


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
    }

    private void addItem(View v, View root) {
        EditText Nameinput = root.findViewById(R.id.editTextExamName);
        String NameText = Nameinput.getText().toString();

        EditText Timeinput = root.findViewById(R.id.editTextExamTime);
        String TimeText = Timeinput.getText().toString();

        EditText Locationinput = root.findViewById(R.id.editTextExamLocation);
        String LocationText = Locationinput.getText().toString();

        if (!(NameText.equals("")) && !(TimeText.equals("")) && !(LocationText.equals(""))) {
            examsViewModel.addItem("Class: " + NameText + "\n" + "Time: " + TimeText + "\n" + "Location: " + LocationText);
            Nameinput.setText("");
            Timeinput.setText("");
            Locationinput.setText("");
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Fill in missing fields", Toast.LENGTH_LONG).show();
        }
    }

    private void editItem(int position) {
        String selectedItem = examsViewModel.getItems().get(position);
        String[] parts = selectedItem.split("\n");

        EditText Nameinput = getView().findViewById(R.id.editTextExamName);
        Nameinput.setText(parts[0]);

        EditText Timeinput = getView().findViewById(R.id.editTextExamTime);
        Timeinput.setText(parts[1]);

        EditText Locationinput = getView().findViewById(R.id.editTextExamLocation);
        Locationinput.setText(parts[2]);

        examsViewModel.removeItem(position);
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}