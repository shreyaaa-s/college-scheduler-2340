package com.example.collegescheduler.ui.exams;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



import java.util.ArrayList; //changes

public class ExamsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private ArrayList<String> items = new ArrayList<>(); //changes

    public ExamsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is exams fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    //changes
    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(int position) {
        items.remove(position);
    }

    public ArrayList<String> getItems() {
        return items;
    }
}