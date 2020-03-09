package com.example.todolist.repositories;

import com.example.todolist.models.Task;

import java.util.ArrayList;

public interface TaskRepository {

    boolean addTask(String task);

    ArrayList<Task> getStoredTasks();

    boolean markTaskAsDone(Task task);

    boolean editTask(Task task);

    boolean deleteTask(Task task);
}
