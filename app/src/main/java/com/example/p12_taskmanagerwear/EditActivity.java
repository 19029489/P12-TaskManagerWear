package com.example.p12_taskmanagerwear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText etName, etDesc;
    Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent i = getIntent();
        String name = i.getStringExtra("taskName");
        String desc = i.getStringExtra("taskDesc");
        int id = i.getIntExtra("id", 0);

        etName.setText(name);
        etDesc.setText(desc);

        DBHelper db = new DBHelper(EditActivity.this);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteTask(id);
                Toast.makeText(EditActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                Intent o = new Intent(EditActivity.this, MainActivity.class);
                startActivity(o);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().equalsIgnoreCase("") || etDesc.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(EditActivity.this, "Please fill in all the blanks", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateTask(etName.getText().toString(), etDesc.getText().toString(), id);
                    Toast.makeText(EditActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                    Intent o = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(o);
                }
            }
        });
    }
}