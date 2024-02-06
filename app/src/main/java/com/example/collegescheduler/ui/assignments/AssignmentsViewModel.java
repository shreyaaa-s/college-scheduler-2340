package com.example.collegescheduler.ui.assignments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Array;
import java.util.ArrayList;

public class AssignmentsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private ArrayList<String> items = new ArrayList<>();

    public AssignmentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is assignments fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void addItem(String item){
        items.add(item);
    }

    public void removeItem(int position){
        items.remove(position);
    }

    public ArrayList<String> getItems(){
        return items;
    }
}