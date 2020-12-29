 package com.example.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {

     public static final String KEY_ITEM_TEXT = "item_text";
     public static final String KEY_ITEM_POSITION = "item_position";
     public static final int EDIT_TEXT_CODE = 20;
    List<String> items;
    EditText taskitem;
    Button buttonAdd;
    RecyclerView taskList;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //task item entered into editText
        taskitem = findViewById(R.id.taskitem);
        buttonAdd = findViewById(R.id.buttonAdd);
        taskList = findViewById(R.id.taskList);

       // items = new ArrayList<>();
        //needs to be called once on starting the app
        loadItems();

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                //creating new activity
            Intent i = new Intent(MainActivity.this, EditActivity.class);
            //data to edit being passed using intents
            i.putExtra(KEY_ITEM_TEXT, items.get(position));
            i.putExtra(KEY_ITEM_POSITION, position);
            startActivityForResult(i,EDIT_TEXT_CODE);
            }
        };

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //delete the item from position
                items.remove(position);
                //notify adapter of deletion
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Task removed!",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        taskList.setAdapter(itemsAdapter);
        taskList.setLayoutManager(new LinearLayoutManager((this)));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = taskitem.getText().toString();
                //add item to model
                items.add(todoItem);
                //notify adapter of item to be inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                //clear edit text field
                taskitem.setText("");

                Toast.makeText(getApplicationContext(),"Task added!",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
             //retrieve edited text and position of updated item and then, replace it
             String itemText = data.getStringExtra(KEY_ITEM_TEXT);
             int position = data.getExtras().getInt(KEY_ITEM_POSITION);

             items.set(position, itemText);
             itemsAdapter.notifyItemChanged(position);
             saveItems();
             Toast.makeText(getApplicationContext(), "Item updated!", Toast.LENGTH_SHORT).show();
         } else {
             Log.w("MainActivity", "Unknown call to onActivityResult");
         }
     } 
     private File getDataFile(){

        return new File(getFilesDir(),"data.txt");
    }

    private void loadItems(){
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch(IOException e){
            Log.e("MainActivity","Error reading items", e);
            items = new ArrayList<>();
        }
    }


     private void saveItems(){
        try{
           FileUtils.writeLines(getDataFile(),items);
        }
        catch(IOException e){
            Log.e("MainActivity","Error writing items", e);
        }

    }
 }