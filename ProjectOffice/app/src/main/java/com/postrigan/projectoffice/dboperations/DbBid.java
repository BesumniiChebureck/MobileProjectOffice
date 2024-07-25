package com.postrigan.projectoffice.dboperations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.postrigan.projectoffice.RuntimeManager;
import com.postrigan.projectoffice.data.Task;
import com.postrigan.projectoffice.data.Bid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DbBid {
    public static @Nullable Bid getById(SQLiteDatabase db, int _id) {
        Cursor result = db.rawQuery(
                "select * from bid where _id = " + _id, null);

        if (!result.moveToNext()) {
            return null;
        }

        return new Bid(
                result.getInt(0),
                result.getInt(1),
                LocalDate.parse(result.getString(2)),
                result.getInt(3));
    }

    public static ArrayList<Bid> getAllByEmployee(SQLiteDatabase db) {
        Cursor result = db.rawQuery(
                "select * from bid where employee_id = " + RuntimeManager.employee.get_id(), null);

        ArrayList<Bid> employeeBids = new ArrayList<>();

        while (result.moveToNext()) {
            employeeBids.add(new Bid(
                    result.getInt(0),
                    result.getInt(1),
                    LocalDate.parse(result.getString(2)),
                    result.getInt(3))
            );
        }

        return employeeBids;
    }

    public static void createNew(SQLiteDatabase db, Task task) {
        db.execSQL("insert into bid (task_id, bid_date, employee_id) values " +
                "(" + task.get_id() + ", '" + LocalDate.now() + "', " + RuntimeManager.employee.get_id() + ");");
    }
}
