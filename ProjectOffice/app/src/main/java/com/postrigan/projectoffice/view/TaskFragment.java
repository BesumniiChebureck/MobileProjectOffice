package com.postrigan.projectoffice.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.postrigan.projectoffice.R;
import com.postrigan.projectoffice.RuntimeManager;
import com.postrigan.projectoffice.adapters.TaskAdapter;
import com.postrigan.projectoffice.data.Task;

import java.util.ArrayList;

public class TaskFragment extends Fragment {
    private final TaskActivity activity;
    private final SQLiteDatabase db;
    private final ArrayList<Task> tasks;

    public TaskFragment(TaskActivity activity, SQLiteDatabase db, ArrayList<Task> tasks) {
        super(R.layout.fragment_task);
        this.activity = activity;
        this.db = db;
        this.tasks = tasks;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task,
                container, false);
        ListView saveListView = rootView.findViewById(R.id.list);
        TaskAdapter adapter = new TaskAdapter(activity, R.layout.task_item, tasks, db);

        saveListView.setOnItemClickListener((parent, view, position, id) -> {
            RuntimeManager.selectedTask = adapter.getItem(position);
            Intent intent = new Intent(activity, TaskInfoActivity.class);
            startActivity(intent);
        });

        saveListView.setAdapter(adapter);
        return rootView;
    }
}
