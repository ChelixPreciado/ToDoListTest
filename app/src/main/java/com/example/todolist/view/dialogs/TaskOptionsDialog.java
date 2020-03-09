package com.example.todolist.view.dialogs;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.R;
import com.example.todolist.contracts.MainContract;
import com.example.todolist.databinding.DialogTaskOptionsBinding;
import com.example.todolist.models.Task;

public class TaskOptionsDialog extends BaseDialog implements View.OnClickListener {

    private DialogTaskOptionsBinding binding;
    private Task task;
    private TaskOptionsDialogListener listener;

    private static final String TASK_DESCRIPTION_TAG = "TASK_DESCRIPTION_TAG";

    public static TaskOptionsDialog newInstance(Task task, TaskOptionsDialogListener listener) {
        TaskOptionsDialog dialog = new TaskOptionsDialog();
        Bundle args = new Bundle();
        args.putParcelable(TASK_DESCRIPTION_TAG, task);
        dialog.setArguments(args);
        dialog.setListener(listener);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogTaskOptionsBinding.inflate(getLayoutInflater());
        if (getArguments() != null) {
            task = getArguments().getParcelable(TASK_DESCRIPTION_TAG);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (task != null)
            binding.txtTaskDescr.setText(task.getDescription());
        if (!task.isDone()) {
            binding.btnMarkAsDone.setOnClickListener(this);
            binding.txtMarkAsDone.setOnClickListener(this);
            binding.btnEdit.setOnClickListener(this);
            binding.txtEditTask.setOnClickListener(this);
        }
        binding.btnDelete.setOnClickListener(this);
        binding.txtDeleteTask.setOnClickListener(this);

        binding.btnMarkAsDone.setVisibility(task.isDone() ? View.GONE : View.VISIBLE);
        binding.txtMarkAsDone.setVisibility(task.isDone() ? View.GONE : View.VISIBLE);
        binding.btnEdit.setVisibility(task.isDone() ? View.GONE : View.VISIBLE);
        binding.txtEditTask.setVisibility(task.isDone() ? View.GONE : View.VISIBLE);
    }

    @Override
    protected View getLayout() {
        return binding.getRoot();
    }

    public void setListener(TaskOptionsDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_mark_as_done:
            case R.id.txt_mark_as_done:
                listener.performAction(MainContract.TASK_ACTION_DONE, task);
                break;
            case R.id.btn_edit:
            case R.id.txt_edit_task:
                listener.performAction(MainContract.TASK_ACTION_EDIT, task);
                break;
            case R.id.btn_delete:
            case R.id.txt_delete_task:
                listener.performAction(MainContract.TASK_ACTION_DELETE, task);
                break;
        }
        dismiss();
    }

    public interface TaskOptionsDialogListener {
        void performAction(int action, Task task);
    }
}
