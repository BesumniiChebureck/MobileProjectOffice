package com.postrigan.projectoffice.adapters;

import android.annotation.SuppressLint;
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
import com.postrigan.projectoffice.data.Bid;
import com.postrigan.projectoffice.data.Task;
import com.postrigan.projectoffice.dboperations.DbEmployee;
import com.postrigan.projectoffice.dboperations.DbTask;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BidAdapter extends ArrayAdapter<Bid> {
    private int item;
    private final List<Bid> bids;
    private final SQLiteDatabase db;

    public BidAdapter(@NonNull Context context, int resource, @NonNull List<Bid> bids, SQLiteDatabase db) {
        super(context, resource, bids);
        item = resource;
        this.bids = bids;
        this.db = db;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(item, parent, false);
        }

        final Bid bid = bids.get(position);
        final Task task = DbTask.getById(db, bid.getTaskId());

        TextView bidName = convertView.findViewById(R.id.bidName);
        TextView bidDate = convertView.findViewById(R.id.bidDate);
        TextView taskStartDate = convertView.findViewById(R.id.taskStartDate);
        TextView employeeBid = convertView.findViewById(R.id.employeeBid);

        bidName.setText("Заявка по " + task.getName());
        bidDate.setText(bid.getBidDate()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        if(task.getStartDate().getYear() != 2090)
            taskStartDate.setText("Задача началась: " +
                    task.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        else
            taskStartDate.setText("Задача началась: -");

        employeeBid.setText(DbEmployee.getById(db, bid.getEmployeeId()).getFullName());

        return convertView;
    }
}
