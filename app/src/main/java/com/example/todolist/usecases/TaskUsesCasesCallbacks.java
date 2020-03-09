package com.example.todolist.usecases;

import com.example.todolist.models.Task;

import java.util.ArrayList;

public interface TaskUsesCasesCallbacks {

    void getActiveTasks(ArrayList<Task> activeTasks);
    void getDoneTasks(ArrayList<Task> doneTasks);

}
