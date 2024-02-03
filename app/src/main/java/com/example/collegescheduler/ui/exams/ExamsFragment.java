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
    }

    private void addItem(View v, View root) {
        EditText input = root.findViewById(R.id.editTextExam);
        String itemText = input.getText().toString();

        if(!(itemText.equals(""))){
            itemsAdapter.add(itemText);
            input.setText("");
        } else{
            Toast.makeText(getActivity().getApplicationContext(), "Please enter text",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}