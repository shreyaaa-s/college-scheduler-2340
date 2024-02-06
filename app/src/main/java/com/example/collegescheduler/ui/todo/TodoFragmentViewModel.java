package com.example.collegescheduler.ui.todo;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class TodoFragmentViewModel extends ViewModel {
    private ArrayList<String> items = new ArrayList<>();

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
