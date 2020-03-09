package com.example.todolist.view.dialogs;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.R;
import com.example.todolist.databinding.DialogAddTaskBinding;
import com.example.todolist.models.Task;

public class AddTaskDialog extends BaseDialog implements View.OnClickListener {

    private static final String EDIT_TASK_TAG = "EDIT_TASK_TAG";

    private DialogAddTaskBinding viewBinding;
    private AddTaskDialogListener listener;
    private Task editTask;

    public static AddTaskDialog newInstance(AddTaskDialogListener listener) {
        AddTaskDialog dialog = new AddTaskDialog();
        dialog.setListener(listener);
        return dialog;
    }

    public static AddTaskDialog newInstance(Task task, AddTaskDialogListener listener) {
        AddTaskDialog dialog = new AddTaskDialog();
        Bundle b = new Bundle();
        b.putParcelable(EDIT_TASK_TAG, task);
        dialog.setArguments(b);
        dialog.setListener(listener);
        return dialog;
    }

    public void setListener(AddTaskDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DialogAddTaskBinding.inflate(getLayoutInflater());
        if (getArguments() != null) {
            editTask = getArguments().getParcelable(EDIT_TASK_TAG);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinding.btnAddTask.setOnClickListener(this);
        viewBinding.btnCancelAdd.setOnClickListener(this);
        if (editTask != null) {
            viewBinding.txtTaskDescription.setText(editTask.getDescription());
        }
    }

    @Override
    protected View getLayout() {
        return viewBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_task:
                if (editTask == null) {
                    listener.onTaskAdded(viewBinding.txtTaskDescription.getText().toString());
                } else {
                    editTask.setDescription(viewBinding.txtTaskDescription.getText().toString());
                    listener.onTaskEdited(editTask);
                }
                dismiss();
                break;
            case R.id.btn_cancel_add:
                dismiss();
                break;
        }
    }

    public interface AddTaskDialogListener {
        void onTaskAdded(String taskDescription);
        void onTaskEdited(Task task);
    }
}
