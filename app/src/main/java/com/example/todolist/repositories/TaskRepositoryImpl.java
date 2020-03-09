package com.example.todolist.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.todolist.models.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TaskRepositoryImpl implements TaskRepository {

    private static final String TASKS_MANAGER = "TASKS_MANAGER";
    private static final String TASKS_TAG = "TASKS_TAG";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public TaskRepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(TASKS_MANAGER, 0);
        editor = sharedPreferences.edit();
    }

    @Override
    public boolean addTask(String task) {
        try {
            ArrayList<Task> tasks = getStoredTasks();
            tasks.add(new Task(task, System.currentTimeMillis()));
            editor.putString(TASKS_TAG, new Gson().toJson(tasks));
            editor.apply();
            return true;
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "error insertando tarea: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public ArrayList<Task> getStoredTasks() {
        String jsonTasks = sharedPreferences.getString(TASKS_TAG, null);
        if (jsonTasks == null) {
            return new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            ArrayList<Task> tasks = new Gson().fromJson(jsonTasks, type);
            return tasks;
        }
    }

    @Override
    public boolean markTaskAsDone(Task task) {
        try {
            ArrayList<Task> tasks = getStoredTasks();
            tasks.get(tasks.indexOf(task)).setDone(true);
            editor.putString(TASKS_TAG, new Gson().toJson(tasks));
            editor.apply();
            return true;
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "error marcando tarea como terminada: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean editTask(Task task) {
        try {
            ArrayList<Task> tasks = getStoredTasks();
            tasks.get(tasks.indexOf(task)).setDescription(task.getDescription());
            editor.putString(TASKS_TAG, new Gson().toJson(tasks));
            editor.apply();
            return true;
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "error editando tarea: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteTask(Task task) {
        try {
            ArrayList<Task> tasks = getStoredTasks();
            tasks.remove(task);
            editor.putString(TASKS_TAG, new Gson().toJson(tasks));
            editor.apply();
            return true;
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "error eliminando tarea: " + ex.getMessage());
        }
        return false;
    }

}
