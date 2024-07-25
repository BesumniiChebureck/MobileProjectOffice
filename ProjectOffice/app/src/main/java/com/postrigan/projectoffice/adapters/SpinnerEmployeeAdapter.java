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
import com.postrigan.projectoffice.data.Employee;

import java.util.List;

public class SpinnerEmployeeAdapter extends ArrayAdapter<Employee> {
    private final int item;
    private final List<Employee> employees;

    public SpinnerEmployeeAdapter(@NonNull Context context, int resource,
                                  @NonNull List<Employee> employees) {
        super(context, resource, employees);
        item = resource;
        this.employees = employees;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(item, parent, false);
        }

        final Employee employee = employees.get(position);
        TextView name = convertView.findViewById(R.id.name);
        name.setText(employee.getFullName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(item, parent, false);
        }

        final Employee employee = employees.get(position);
        TextView name = convertView.findViewById(R.id.name);
        name.setText(employee.getFullName());

        return convertView;
    }
}
