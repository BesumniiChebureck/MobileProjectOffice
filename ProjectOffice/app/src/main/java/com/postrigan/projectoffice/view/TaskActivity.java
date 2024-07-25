package com.postrigan.projectoffice.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.postrigan.projectoffice.R;
import com.postrigan.projectoffice.adapters.SpinnerEmployeeAdapter;
import com.postrigan.projectoffice.adapters.SpinnerPriorityAdapter;
import com.postrigan.projectoffice.adapters.SpinnerStatusAdapter;
import com.postrigan.projectoffice.adapters.TaskAdapter;

import com.postrigan.projectoffice.data.DBHelper;
import com.postrigan.projectoffice.data.Employee;
import com.postrigan.projectoffice.data.Priority;
import com.postrigan.projectoffice.data.Status;
import com.postrigan.projectoffice.data.Task;
import com.postrigan.projectoffice.dboperations.DbEmployee;
import com.postrigan.projectoffice.dboperations.DbPriority;
import com.postrigan.projectoffice.dboperations.DbStatus;
import com.postrigan.projectoffice.dboperations.DbTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class TaskActivity extends AppCompatActivity {

    LocalDate date = LocalDate.now().minusDays(7);
    TaskFragment taskFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        DBHelper helper;
        SQLiteDatabase db;

        try {
            helper = new DBHelper(getApplicationContext());
            db = helper.getReadableDatabase();
        } catch (Exception ex){
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Spinners start
        Spinner employee = findViewById(R.id.employee);
        Spinner status = findViewById(R.id.status);
        Spinner priority = findViewById(R.id.priority);

        SpinnerEmployeeAdapter employeeAdapter = new SpinnerEmployeeAdapter(this,
                R.layout.spinner_item,
                DbEmployee.getAll(db));

        SpinnerStatusAdapter statusAdapter = new SpinnerStatusAdapter(this,
                R.layout.spinner_item,
                DbStatus.getAll(db));

        SpinnerPriorityAdapter priorityAdapter = new SpinnerPriorityAdapter(this,
                R.layout.spinner_item,
                DbPriority.getAll(db));

        employee.setAdapter(employeeAdapter);
        status.setAdapter(statusAdapter);
        priority.setAdapter(priorityAdapter);
        // Spinners end

        //Calendar start
        TextView datePresent = findViewById(R.id.datePresent);
        datePresent.setText(String.format("%d-%d-%d", date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        //Calendar end

        //Sort by priority start
        CheckBox sort = findViewById(R.id.cbSort);
        sort.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (taskFragment == null) {
                return;
            }

            ListView tasks = findViewById(R.id.list);

            if (isChecked) {
                ((TaskAdapter) tasks.getAdapter()).sort(Comparator.comparing(Task::getStartDate));
            } else {
                ((TaskAdapter) tasks.getAdapter()).sort(Comparator.comparing(Task::get_id));
            }
        });
        //Sort by priority end
    }

    public void findTasks(View view) {

        try {
        DBHelper helper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

            Spinner employee = findViewById(R.id.employee);
            Spinner status = findViewById(R.id.status);
            Spinner priority = findViewById(R.id.priority);

            //Upload tasks start

            @Nullable ArrayList<Task> tasks;

            CheckBox cbEmployee = findViewById(R.id.cbEmployee);
            CheckBox cbStatus = findViewById(R.id.cbStatus);
            CheckBox cbPriority = findViewById(R.id.cbPriority);

            Employee filterEmployee;
            Status filterStatus;
            Priority filterPriority;

            if(!cbEmployee.isChecked()){
                filterEmployee = null;
            } else {
                filterEmployee = (Employee) employee.getSelectedItem();
            }

            if (!cbStatus.isChecked()) {
                filterStatus = null;
            } else {
                filterStatus = (Status) status.getSelectedItem();
            }

            if (!cbPriority.isChecked()) {
                filterPriority = null;
            } else {
                filterPriority = (Priority) priority.getSelectedItem();
            }

            tasks = (ArrayList<Task>) DbTask.getTasks(db,
                    filterEmployee,
                    filterPriority,
                    filterStatus,
                    date);

            //Upload tasks end

            if (tasks == null) {
                Toast.makeText(this,
                        "Нет задач с указанными параметрами",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            TaskFragment fragment = new TaskFragment(this, db, tasks);
            taskFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tasks, fragment)
                    .commit();

        } catch (Exception ex){
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void selectDate(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {
            TextView dateText = findViewById(R.id.datePresent);
            dateText.setText(String.format("%d-%d-%d", year, month + 1, dayOfMonth));
            date = LocalDate.of(year, month + 1, dayOfMonth);
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener,
                date.getYear(),
                date.getMonthValue() - 1,
                date.getDayOfMonth());

        datePickerDialog.setTitle("Выберите дату начала выполнения");
        datePickerDialog.show();
    }

    public void getOwnBids(View view) {
        Intent intent = new Intent(this, BidActivity.class);
        startActivity(intent);
    }
}