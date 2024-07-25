package com.postrigan.projectoffice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.postrigan.projectoffice.R;
import com.postrigan.projectoffice.data.Priority;

import java.util.List;

public class SpinnerPriorityAdapter extends ArrayAdapter<Priority> {
    private final int item;
    private final List<Priority> priorities;

    public SpinnerPriorityAdapter(@NonNull Context context, int resource,
                                @NonNull List<Priority> priorities) {
        super(context, resource, priorities);
        item = resource;
        this.priorities = priorities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(item, parent, false);
        }

        final Priority priority = priorities.get(position);
        TextView name = convertView.findViewById(R.id.name);
        name.setText(priority.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(item, parent, false);
        }

        final Priority priority = priorities.get(position);
        TextView name = convertView.findViewById(R.id.name);
        name.setText(priority.getName());

        return convertView;
    }
}
