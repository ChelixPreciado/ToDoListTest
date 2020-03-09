package com.example.todolist.usecases;

import com.example.todolist.models.Task;

public interface TaskUseCasesContract {

    void receiveNewTask(String task);

    void getActiveTasks();

    void getFinishedTasks();

    void markTaskAsDone(Task task);

    void editTask(Task task);

    void deleteTask(Task task);

    void onDestroy();

}
