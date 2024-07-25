package com.postrigan.projectoffice.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.postrigan.projectoffice.R;
import com.postrigan.projectoffice.RuntimeManager;
import com.postrigan.projectoffice.adapters.BidAdapter;
import com.postrigan.projectoffice.adapters.TaskAdapter;
import com.postrigan.projectoffice.data.Bid;
import com.postrigan.projectoffice.data.DBHelper;
import com.postrigan.projectoffice.data.Task;

import java.util.ArrayList;

public class BidFragment extends Fragment {
    private final BidActivity activity;
    private final SQLiteDatabase db;
    private final ArrayList<Bid> bids;

    public BidFragment(BidActivity activity, SQLiteDatabase db, ArrayList<Bid> bids) {
        super(R.layout.fragment_bid);
        this.activity = activity;
        this.db = db;
        this.bids = bids;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bid,
                container, false);
        ListView saveListView = rootView.findViewById(R.id.content);
        BidAdapter adapter = new BidAdapter(activity, R.layout.bid_item, bids, db);

        saveListView.setOnItemClickListener((parent, view, position, id) -> {

            DBHelper helper = new DBHelper(activity.getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            db.delete("bid", "_id = " + adapter.getItem(position).get_id(), null);

            Intent intent = new Intent(activity, TaskActivity.class);
            startActivity(intent);
        });

        saveListView.setAdapter(adapter);
        return rootView;
    }
}
