package com.example.todolist.view;

import android.os.Bundle;

import com.example.todolist.R;
import com.example.todolist.contracts.BasePresenter;
import com.example.todolist.contracts.MainContract;
import com.example.todolist.databinding.ActivityScrollingBinding;
import com.example.todolist.models.Task;
import com.example.todolist.presenters.MainPresenter;
import com.example.todolist.view.adapters.TasksAdapter;
import com.example.todolist.view.dialogs.AddTaskDialog;
import com.example.todolist.view.dialogs.TaskOptionsDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener, AddTaskDialog.AddTaskDialogListener {

    private MainContract.Presenter presenter;
    private ActivityScrollingBinding binding;
    private TasksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.toolbar.setTitle(R.string.action_actived_tasks);
        setSupportActionBar(binding.toolbar);
        binding.btnAddTask.setOnClickListener(this);
        setPresenter(new MainPresenter(this));
        binding.innerLayout.rvTasks.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_tasks) {
            presenter.showActivedTasks();
            binding.toolbarLayout.setTitle(getString(R.string.action_actived_tasks));
            return true;
        } else if (id == R.id.action_show_done_tasks) {
            presenter.showFinishedtasks();
            binding.toolbarLayout.setTitle(getString(R.string.action_finished_tasks));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_task:
                AddTaskDialog addTaskDialog = AddTaskDialog.newInstance(this);
                addTaskDialog.show(getSupportFragmentManager(), "add_dialog");
                break;
        }
    }

    @Override
    public void updateList(ArrayList<Task> activeTasks, boolean showDoneTasks) {
        binding.toolbarLayout.setTitle(showDoneTasks ? getString(R.string.action_finished_tasks) : getString(R.string.action_actived_tasks));
        if (activeTasks != null && !activeTasks.isEmpty()) {
            binding.innerLayout.txtEmptyState.setVisibility(View.GONE);
            binding.innerLayout.rvTasks.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new TasksAdapter(activeTasks, task -> {
                    TaskOptionsDialog taskOptionsDialog = TaskOptionsDialog.newInstance(task, (action, task1) -> presenter.performTaskOption(action, task1));
                    taskOptionsDialog.show(getSupportFragmentManager(), TaskOptionsDialog.class.getSimpleName());
                });
                binding.innerLayout.rvTasks.setAdapter(adapter);
            } else {
                adapter.renewAdapter(activeTasks);
            }
        } else {
            binding.innerLayout.txtEmptyState.setVisibility(View.VISIBLE);
            binding.innerLayout.rvTasks.setVisibility(View.GONE);
            binding.innerLayout.txtEmptyState.setText(Objects.equals(binding.toolbarLayout.getTitle(), getString(R.string.action_finished_tasks)) ? R.string.finished_tasks_empty_state : R.string.tasks_empty_state);
        }
    }

    @Override
    public void showUpdateTaskDialog(Task task) {
        AddTaskDialog editTaskDialog = AddTaskDialog.newInstance(task, this);
        editTaskDialog.show(getSupportFragmentManager(), "edit_dialog");
    }

    @Override
    public void setPresenter(BasePresenter T) {
        this.presenter = (MainContract.Presenter) T;
    }

    @Override
    public void onTaskAdded(String taskDescription) {
        presenter.addTask(taskDescription);
    }

    @Override
    public void onTaskEdited(Task task) {
        presenter.editTask(task);
    }
}
