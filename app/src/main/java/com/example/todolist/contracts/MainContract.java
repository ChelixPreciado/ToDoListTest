package com.example.todolist.contracts;

import com.example.todolist.models.Task;

import java.util.ArrayList;

public interface MainContract {

    int TASK_ACTION_DONE = 12;
    int TASK_ACTION_EDIT = 34;
    int TASK_ACTION_DELETE = 56;

    interface Presenter extends BasePresenter {
        void addTask(String taskDescription);
        void editTask(Task task);
        void performTaskOption(int option, Task task);
        void showActivedTasks();
        void showFinishedtasks();
    }

    interface View extends BaseView<Presenter> {
        void updateList(ArrayList<Task> activeTasks, boolean showDoneTasks);
        void showUpdateTaskDialog(Task task);
    }

}
