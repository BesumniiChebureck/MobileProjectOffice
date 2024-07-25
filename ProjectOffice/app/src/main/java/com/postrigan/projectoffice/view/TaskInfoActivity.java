package com.postrigan.projectoffice.view;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.postrigan.projectoffice.R;
import com.postrigan.projectoffice.RuntimeManager;
import com.postrigan.projectoffice.adapters.SpinnerPriorityAdapter;
import com.postrigan.projectoffice.adapters.SpinnerStatusAdapter;
import com.postrigan.projectoffice.data.DBHelper;
import com.postrigan.projectoffice.data.Task;
import com.postrigan.projectoffice.data.Priority;
import com.postrigan.projectoffice.data.Status;
import com.postrigan.projectoffice.dboperations.DbEmployee;
import com.postrigan.projectoffice.dboperations.DbPriority;
import com.postrigan.projectoffice.dboperations.DbStatus;
import com.postrigan.projectoffice.dboperations.DbBid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TaskInfoActivity extends AppCompatActivity {

    Task task;
    Spinner statusSpinner;
    Spinner prioritySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);

        task = RuntimeManager.selectedTask;
        statusSpinner = findViewById(R.id.status);
        prioritySpinner = findViewById(R.id.priority);

        setValues();
    }

    public void createBid(View view) {

        try {
            //Check employee access
            if (RuntimeManager.employee.get_id() != RuntimeManager.selectedTask.getEmployeeId()){
                Toast.makeText(this,
                        "У вас нет доступа к отправке на проверку не ваших задач",
                        Toast.LENGTH_LONG).show();
                return;
            }

            DBHelper helper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            if (Objects.equals( DbStatus.getById(db, task.getStatusId()).getName(), "На проверке")) {
                Toast.makeText(this,
                        "Эта задача уже находится на проверке",
                        Toast.LENGTH_LONG).show();
                return;
            }

            DbBid.createNew(db, task);
            ContentValues cv = new ContentValues();
            cv.put("status_id", 3);

            int result = db.update("task", cv, "_id = " + task.get_id(), null);

            if(result > 0){
                Toast.makeText(this,
                        "Заявка успешно создана",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "Не удалось сохранить заявку. Обратитесь к системному администратору",
                        Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(this,
                    ex.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void changeTaskInfo(View view) {

        try {
            //Check employee access
            if (RuntimeManager.employee.get_id() != RuntimeManager.selectedTask.getEmployeeId()){
                Toast.makeText(this,
                        "У вас нет доступа к редактированию не ваших задач",
                        Toast.LENGTH_LONG).show();
                return;
            }

            DBHelper helper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            if (Objects.equals(DbStatus.getById(db, task.getStatusId()).getName(), "На проверке")) {
                Toast.makeText(this,
                        "Вы не можете редактировать задачу, когда она находится на проверке",
                        Toast.LENGTH_LONG).show();
                return;
            }

            ContentValues cv = new ContentValues();
            cv.put("priority_id", ((Priority)prioritySpinner.getSelectedItem()).get_id() );
            cv.put("status_id", ((Status)statusSpinner.getSelectedItem()).get_id());

            int result = db.update("task", cv, "_id = " + task.get_id(), null);

            if(result > 0){
                Toast.makeText(this,
                        "Изменения успешно сохранены",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "Не удалось сохранить изменения. Обратитесь к системному администратору",
                        Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
        } catch (Exception ex){
            Toast.makeText(this,
                    ex.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    public void setValues() {

        try {
            DBHelper helper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            ((TextView) findViewById(R.id.taskName)).setText("Название: " + task.getName());

            if(task.getStartDate().getYear() != 2090)
                ((TextView) findViewById(R.id.dateStart)).setText(
                    "Дата начала: " + task.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            else
                ((TextView) findViewById(R.id.dateStart)).setText("Дата начала: отсутствует");

            if(task.getEndDate().getYear() != 2090)
                ((TextView) findViewById(R.id.dateEnd)).setText(
                    "Дата завершения: " + task.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            else
                ((TextView) findViewById(R.id.dateEnd)).setText("Дата завершения: отсутствует");

            ((TextView) findViewById(R.id.employee)).setText(
                    "Исполнитель: " + DbEmployee.getById(db, task.getEmployeeId()).getFullName());

            // Spinners start
            SpinnerStatusAdapter statusAdapter = new SpinnerStatusAdapter(this,
                    R.layout.spinner_item,
                    DbStatus.getAll(db));

            SpinnerPriorityAdapter priorityAdapter = new SpinnerPriorityAdapter(this,
                    R.layout.spinner_item,
                    DbPriority.getAll(db));

            statusSpinner.setAdapter(statusAdapter);
            prioritySpinner.setAdapter(priorityAdapter);

            statusSpinner.setSelection(task.getStatusId() - 1);
            prioritySpinner.setSelection(task.getPriorityId() - 1);
            // Spinners end
        } catch (Exception ex){
            Toast.makeText(this,
                    ex.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}