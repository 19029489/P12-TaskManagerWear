package com.example.p12_taskmanagerwear;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText etName, etDesc, etTime;
    Button btnAddTask, btnCancel;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        etName = findViewById(R.id.editTextName);
        etDesc = findViewById(R.id.editTextDesc);
        etTime = findViewById(R.id.editTextTime);
        btnAddTask = findViewById(R.id.buttonAddTask);
        btnCancel = findViewById(R.id.buttonCancel);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(AddActivity.this);
                String taskName = "";
                if (etName.getText().toString().equalsIgnoreCase("")){
                    etName.setError("Please enter task name");
                } else {
                    taskName = etName.getText().toString();
                }
                String taskDesc = "";
                if (etDesc.getText().toString().equalsIgnoreCase("")){
                    etDesc.setError("Please enter description");
                } else {
                    taskDesc = etDesc.getText().toString();
                }

                int taskTime = 0;
                if (etTime.getText().toString().equalsIgnoreCase("") || Integer.parseInt(etTime.getText().toString()) == 0){
                    etTime.setError("Please enter time more than 0");
                } else {
                    taskTime = Integer.parseInt(etTime.getText().toString());
                }

                if (!(taskName.equals("") && taskDesc.equals("") && taskTime == 0)){
                    id = db.insertTask(taskName, taskDesc);
                    if (id != -1){
                        Toast.makeText(AddActivity.this, "Inserted Successfully",
                                Toast.LENGTH_LONG).show();
                    }
                }

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, taskTime);

                Intent intent = new Intent(AddActivity.this, ScheduledNotificationReceiver.class);
                intent.putExtra("id", id);
                intent.putExtra("task_name", taskName);
                intent.putExtra("task_desc", taskDesc);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, Integer.parseInt("" + id), intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}