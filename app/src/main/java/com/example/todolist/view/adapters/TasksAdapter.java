package com.example.todolist.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databinding.ItemTaskCardBinding;
import com.example.todolist.models.Task;
import com.example.todolist.utils.DateFormat;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskCardViewHolder> {

    private ArrayList<Task> tasks;
    private TasksAdapterListener listener;

    public TasksAdapter(ArrayList<Task> tasks, TasksAdapterListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskCardBinding itemTaskCardBinding = ItemTaskCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskCardViewHolder(itemTaskCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskCardViewHolder holder, int position) {
        Task t = tasks.get(position);
        holder.binding.txtTaskDescription.setText(t.getDescription());
        holder.binding.txtTaskCreatedDate.setText(DateFormat.getFormattedDate(t.getDate()));
        holder.binding.btnTaskOptions.setOnClickListener(view -> {
            listener.taskOptionSelected(t);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void renewAdapter(ArrayList<Task> updatedTasks) {
        tasks.clear();
        tasks.addAll(updatedTasks);
        notifyDataSetChanged();
    }

    class TaskCardViewHolder extends RecyclerView.ViewHolder {

        ItemTaskCardBinding binding;

        TaskCardViewHolder(ItemTaskCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface TasksAdapterListener {
        void taskOptionSelected(Task task);
    }
}
