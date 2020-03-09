package com.example.todolist.presenters;

import com.example.todolist.contracts.MainContract;
import com.example.todolist.models.Task;
import com.example.todolist.usecases.TaskUseCases;
import com.example.todolist.usecases.TaskUsesCasesCallbacks;
import com.example.todolist.view.MainActivity;

import java.util.ArrayList;

public class MainPresenter implements MainContract.Presenter, TaskUsesCasesCallbacks {

    private MainContract.View view;
    private TaskUseCases taskUseCase;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        taskUseCase = new TaskUseCases(((MainActivity) view).getBaseContext(), this);
    }

    @Override
    public void onStart() {
        taskUseCase.getActiveTasks();
    }

    @Override
    public void onDestroy() {
        taskUseCase.onDestroy();
    }

    @Override
    public void addTask(String taskDescription) {
        taskUseCase.receiveNewTask(taskDescription);
    }

    @Override
    public void editTask(Task task) {
        taskUseCase.editTask(task);
    }

    @Override
    public void performTaskOption(int option, Task task) {
        switch (option) {
            case MainContract.TASK_ACTION_DONE:
                    taskUseCase.markTaskAsDone(task);
                break;
            case MainContract.TASK_ACTION_EDIT:
                view.showUpdateTaskDialog(task);
                break;
            case MainContract.TASK_ACTION_DELETE:
                    taskUseCase.deleteTask(task);
                break;
        }
    }

    @Override
    public void showActivedTasks() {
        taskUseCase.getActiveTasks();
    }

    @Override
    public void showFinishedtasks() {
        taskUseCase.getFinishedTasks();
    }

    @Override
    public void getActiveTasks(ArrayList<Task> activeTasks) {
        view.updateList(activeTasks, false);
    }

    @Override
    public void getDoneTasks(ArrayList<Task> doneTasks) {
        view.updateList(doneTasks, true);
    }
}
