package com.postrigan.projectoffice.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.postrigan.projectoffice.R;
import com.postrigan.projectoffice.data.Task;
import com.postrigan.projectoffice.dboperations.DbEmployee;
import com.postrigan.projectoffice.dboperations.DbStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class TaskAdapter extends ArrayAdapter<Task> {
    private int item;
    private final List<Task> tasks;
    private final SQLiteDatabase db;

    public TaskAdapter(@NonNull Context context, int resource,
                         @NonNull List<Task> tasks, SQLiteDatabase db) {
        super(context, resource, tasks);
        item = resource;
        this.tasks = tasks;
        this.db = db;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(item, parent, false);
        }

        final Task task = tasks.get(position);

        TextView taskName = convertView.findViewById(R.id.taskName);
        TextView startDate = convertView.findViewById(R.id.startDate);
        TextView executiveEmployee = convertView.findViewById(R.id.executiveEmployee);
        TextView taskStatus = convertView.findViewById(R.id.taskStatus);

        taskName.setText(task.getName());

        if(task.getStartDate().getYear() != 2090)
            startDate.setText(task.getStartDate()
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        else
            startDate.setText("Отсутствует");

        executiveEmployee.setText(DbEmployee.getById(db, task.getEmployeeId()).getFullName());
        taskStatus.setText(DbStatus.getById(db, task.getStatusId()).getName());

        return convertView;
    }
}
