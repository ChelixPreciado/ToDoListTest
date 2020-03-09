package com.example.todolist.usecases;

import android.content.Context;

import com.example.todolist.models.Task;
import com.example.todolist.repositories.TaskRepositoryImpl;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskUseCases implements TaskUseCasesContract {

    private TaskRepositoryImpl taskRepository;
    private TaskUsesCasesCallbacks casesCallbacks;

    private Disposable activeTaskDisposable;
    private Disposable receiveNewTaskDisposable;
    private Disposable markAsDoneDisposable;
    private Disposable editTaskDisposable;
    private Disposable deleteTaskDisposable;

    public TaskUseCases(Context context, TaskUsesCasesCallbacks casesCallbacks) {
        taskRepository = new TaskRepositoryImpl(context);
        this.casesCallbacks = casesCallbacks;
    }

    @Override
    public void receiveNewTask(String task) {
        receiveNewTaskDisposable = Single.just(taskRepository.addTask(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((aBoolean, throwable) -> {
                    if (throwable == null & aBoolean) {
                        getActiveTasks();
                    }
                });
    }

    @Override
    public void getActiveTasks() {
        activeTaskDisposable = Single.just(taskRepository.getStoredTasks())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((tasks, throwable) -> {
                    if (throwable == null && tasks != null) {
                        ArrayList<Task> activeTasks = new ArrayList<>();
                        for (Task t : tasks) {
                            if (!t.isDone()) {
                                activeTasks.add(t);
                            }
                        }
                        casesCallbacks.getActiveTasks(activeTasks);
                    }
                 });
    }

    @Override
    public void getFinishedTasks() {
        activeTaskDisposable = Single.just(taskRepository.getStoredTasks())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((tasks, throwable) -> {
                    if (throwable == null && tasks != null) {
                        ArrayList<Task> doneTasks = new ArrayList<>();
                        for (Task t : tasks) {
                            if (t.isDone()) {
                                doneTasks.add(t);
                            }
                        }
                        casesCallbacks.getDoneTasks(doneTasks);
                    }
                });
    }

    @Override
    public void markTaskAsDone(Task task) {
        markAsDoneDisposable = Single.just(taskRepository.markTaskAsDone(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((aBoolean, throwable) -> {
                    if (throwable == null && aBoolean) {
                        getActiveTasks();
                    }
                });
    }

    @Override
    public void editTask(Task task) {
        editTaskDisposable = Single.just(taskRepository.editTask(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((aBoolean, throwable) -> {
                    if (throwable == null && aBoolean) {
                        getActiveTasks();
                    }
                });
    }

    @Override
    public void deleteTask(final Task task) {
        deleteTaskDisposable = Single.just(taskRepository.deleteTask(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((aBoolean, throwable) -> {
                    if (throwable == null && aBoolean) {
                        if (task.isDone()) {
                            getFinishedTasks();
                        } else {
                            getActiveTasks();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        activeTaskDisposable.dispose();
        markAsDoneDisposable.dispose();
        receiveNewTaskDisposable.dispose();
        editTaskDisposable.dispose();
        deleteTaskDisposable.dispose();
    }
}
