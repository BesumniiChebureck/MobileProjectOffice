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
import com.postrigan.projectoffice.data.Status;

import java.util.List;

public class SpinnerStatusAdapter extends ArrayAdapter<Status> {
    private final int item;
    private final List<Status> statuses;

    public SpinnerStatusAdapter(@NonNull Context context, int resource,
                                    @NonNull List<Status> statuses) {
        super(context, resource, statuses);
        item = resource;
        this.statuses = statuses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(item, parent, false);
        }

        final Status status = statuses.get(position);
        TextView name = convertView.findViewById(R.id.name);
        name.setText(status.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(item, parent, false);
        }

        final Status status = statuses.get(position);
        TextView name = convertView.findViewById(R.id.name);
        name.setText(status.getName());

        return convertView;
    }
}
