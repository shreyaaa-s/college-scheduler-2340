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

public class ExamsFragment extends Fragment {

    private FragmentExamsBinding binding;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listview;
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //ExamsViewModel examsViewModel =
               // new ViewModelProvider(this).get(ExamsViewModel.class);

        binding = FragmentExamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.ListViewExam;
        //examsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        listview = root.findViewById(R.id.ListViewExam);
        button = root.findViewById((R.id.buttonExam));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v, root);
            }
        });
        items = new ArrayList<>();
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
                Toast.makeText(context,"Exam details removed",Toast.LENGTH_LONG).show();
                items.remove(position);
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

        if(!(NameText.equals("")) && !(TimeText.equals("")) && !(LocationText.equals(""))){
            itemsAdapter.add(NameText + "\n" + TimeText + "\n" + LocationText);
            Nameinput.setText("");
            Timeinput.setText("");
            Locationinput.setText("");
        } else{
            Toast.makeText(getActivity().getApplicationContext(), "Fill in missing fields",Toast.LENGTH_LONG).show();
        }
    }

    private void editItem(int position) {
        // Fetch the item to edit
        String selectedItem = items.get(position);

        // Split the item into name, time, and location
        String[] parts = selectedItem.split("\n");

        // Set the values of EditText fields to the selected item's values
        EditText Nameinput = getView().findViewById(R.id.editTextExamName);
        Nameinput.setText(parts[0]);

        EditText Timeinput = getView().findViewById(R.id.editTextExamTime);
        Timeinput.setText(parts[1]);

        EditText Locationinput = getView().findViewById(R.id.editTextExamLocation);
        Locationinput.setText(parts[2]);

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