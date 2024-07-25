package com.postrigan.projectoffice.dboperations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.postrigan.projectoffice.data.Task;
import com.postrigan.projectoffice.data.Employee;
import com.postrigan.projectoffice.data.Priority;
import com.postrigan.projectoffice.data.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DbTask {
    public static @Nullable Task getById(SQLiteDatabase db, int _id) {
        Cursor result = db.rawQuery(
                "select * from task where _id = " + _id, null);

        if (!result.moveToNext()) {
            return null;
        }

        if(result.getString(3) == null && result.getString(4) == null){
            return new Task(result.getInt(0),
                    result.getInt(1),
                    result.getString(2),
                    null,
                    null,
                    result.getInt(5),
                    result.getInt(6));
        }

        if(result.getString(3) == null){
            return new Task(result.getInt(0),
                    result.getInt(1),
                    result.getString(2),
                    null,
                    LocalDate.parse(result.getString(4)),
                    result.getInt(5),
                    result.getInt(6));
        }

        if(result.getString(4) == null){
            return new Task(result.getInt(0),
                    result.getInt(1),
                    result.getString(2),
                    LocalDate.parse(result.getString(3)),
                    null,
                    result.getInt(5),
                    result.getInt(6));
        }

        return new Task(result.getInt(0),
                result.getInt(1),
                result.getString(2),
                LocalDate.parse(result.getString(3)),
                LocalDate.parse(result.getString(4)),
                result.getInt(5),
                result.getInt(6));
    }

    public static @Nullable List<Task> getTasks(SQLiteDatabase db) {
        Cursor result = db.rawQuery(
                "select * from task", null);

        List<Task> tasks = new ArrayList<>();

        while (result.moveToNext()) {
            tasks.add(DbTask.getById(db, result.getInt(0)));
        }

        if (tasks.size() == 0) {
            return null;
        }

        return tasks;
    }

    public static @Nullable List<Task> getTasks(SQLiteDatabase db,
                                                LocalDate date) {
        Cursor result = db.rawQuery(
                "select * from task where start_date > '" + date.toString() + "'", null);

        List<Task> tasks = new ArrayList<>();

        while (result.moveToNext()) {
            tasks.add(DbTask.getById(db, result.getInt(0)));
        }

        if (tasks.size() == 0) {
            return null;
        }

        return tasks;
    }

    public static @Nullable List<Task> getTasks(SQLiteDatabase db,
                                                Status status, LocalDate date) {
        Cursor result = db.rawQuery(
                "select * from task where status_id = " + status.get_id()
                        + " and start_date > '" + date.toString() + "'", null);

        List<Task> tasks = new ArrayList<>();

        while (result.moveToNext()) {
            tasks.add(DbTask.getById(db, result.getInt(0)));
        }

        if (tasks.size() == 0) {
            return null;
        }

        return tasks;
    }

    public static @Nullable List<Task> getTasks(SQLiteDatabase db,
                                                Priority priority,
                                                Status status, LocalDate date) {
        Cursor result = db.rawQuery(
                "select * from task where priority_id = " + priority.get_id()
                        + " and status_id = " + status.get_id()
                        + " and start_date > '" + date.toString() + "'", null);

        List<Task> tasks = new ArrayList<>();

        while (result.moveToNext()) {
            tasks.add(DbTask.getById(db, result.getInt(0)));
        }

        if (tasks.size() == 0) {
            return null;
        }

        return tasks;
    }

    public static @Nullable List<Task> getTasks(SQLiteDatabase db,
                                                    Employee employee, Priority priority,
                                                    Status status, LocalDate date) {
        Cursor result;

        if(employee == null && priority == null && status == null){
            result = db.rawQuery(
                    "select * from task where start_date is null or start_date > '" + date.toString() + "'", null);
        } else if(employee == null && priority == null && status != null){
            result = db.rawQuery(
                    "select * from task where status_id = " + status.get_id()
                            + " and (start_date is null or start_date > '" + date.toString() + "')", null);
        } else if (employee == null && priority != null && status == null) {
            result = db.rawQuery(
                    "select * from task where priority_id = " + priority.get_id()
                            + " and (start_date is null or start_date > '" + date.toString() + "')", null);
        } else if (employee != null && priority == null && status == null) {
            result = db.rawQuery(
                    "select * from task where employee_id = " + employee.get_id()
                            + " and (start_date is null or start_date > '" + date.toString() + "')", null);
        } else if (employee == null && priority != null && status != null) {
            result = db.rawQuery(
                    "select * from task where priority_id = " + priority.get_id()
                            + " and status_id = " + status.get_id()
                            + " and (start_date is null or start_date > '" + date.toString() + "')", null);
        } else if (employee != null && priority != null && status == null) {
            result = db.rawQuery(
                    "select * from task where priority_id = " + priority.get_id()
                            + " and employee_id = " + employee.get_id()
                            + " and (start_date is null or start_date > '" + date.toString() + "')", null);
        } else if (employee != null && priority == null && status != null) {
            result = db.rawQuery(
                    "select * from task where employee_id = " + employee.get_id()
                            + " and status_id = " + status.get_id()
                            + " and (start_date is null or start_date > '" + date.toString() + "')", null);
        } else {
            result = db.rawQuery(
                    "select * from task where priority_id = " + priority.get_id()
                            + " and employee_id = " + employee.get_id()
                            + " and status_id = " + status.get_id()
                            + " and (start_date is null or start_date > '" + date.toString() + "')", null);
        }

        List<Task> tasks = new ArrayList<>();

        while (result.moveToNext()) {
            tasks.add(DbTask.getById(db, result.getInt(0)));
        }

        if (tasks.size() == 0) {
            return null;
        }

        return tasks;
    }
}
