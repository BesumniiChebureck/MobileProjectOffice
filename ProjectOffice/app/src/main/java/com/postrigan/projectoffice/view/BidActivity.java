package com.postrigan.projectoffice.view;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.postrigan.projectoffice.R;
import com.postrigan.projectoffice.adapters.BidAdapter;
import com.postrigan.projectoffice.data.Bid;
import com.postrigan.projectoffice.data.DBHelper;
import com.postrigan.projectoffice.data.Priority;
import com.postrigan.projectoffice.data.Status;
import com.postrigan.projectoffice.data.Task;
import com.postrigan.projectoffice.dboperations.DbBid;
import com.postrigan.projectoffice.dboperations.DbTask;

import java.util.ArrayList;

public class BidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);

        try{
            DBHelper helper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            BidAdapter bidAdapter = new BidAdapter(this,
                    R.layout.bid_item,
                    DbBid.getAllByEmployee(db),
                    db);

            ListView bidList = findViewById(R.id.bidList);
            bidList.setAdapter(bidAdapter);

            BidActivity activity = this;

            bidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Bid bid = DbBid.getById(db, position + 1);
                    Task task = DbTask.getById(db, bid.getTaskId());

                    //Update task status start
                    ContentValues cv = new ContentValues();
                    cv.put("status_id", 1);

                    db.update("task", cv, "_id = " + task.get_id(), null);
                    //Update task status end

                    db.delete("bid", "_id = " + bid.get_id(), null);

                    Intent intent = new Intent(activity, TaskActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception ex){
            Toast.makeText(this,
                    ex.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}