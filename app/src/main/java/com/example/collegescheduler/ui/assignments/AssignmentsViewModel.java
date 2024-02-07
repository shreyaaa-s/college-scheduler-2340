package com.example.collegescheduler.ui.assignments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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

    //sort changes
    public void sortArray(int tog) {
      // tog = 1 for sort by date, tog = 2 for sort by class
       Collections.sort(items, new Comparator<String>() {
          @Override
          public int compare(String o1, String o2) {
              String[] data1 = o1.split("\n");
              String[] data2 = o2.split("\n");
              return data1[tog].compareTo(data2[tog]);
          }
      });

    }

}