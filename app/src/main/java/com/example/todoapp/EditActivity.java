package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText etItem;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etItem = findViewById(R.id.etItem);
        saveButton = findViewById(R.id.saveButton);

        //tap on an item opens up new activity i.e. edit activity
        getSupportActionBar().setTitle("Edit item");
        etItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        //to preserve when user is done editing
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //create intent which will contain results
                Intent intent = new Intent();

                //pass the edited data
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, etItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                //set result of intent
                setResult(RESULT_OK, intent);
                //finish activity, close screen and go back
                finish();
            }
        });
    }
}