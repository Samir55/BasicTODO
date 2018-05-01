package com.example.ahmedsamir.remindersapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.ReminderAdapter;
import Models.Reminder;

public class RemindersActivity extends AppCompatActivity {
    // The data in part one is saved here for now, @Samir55 TODO Add SQLite
    ArrayList<Reminder> reminders = new ArrayList<>();

    // The to-do (reminders) list view
    ListView todoList;

    // The custom adapter for Reminder Model class found in Models package.
    ArrayAdapter remindersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        // Get the to do list list view.
        todoList = (ListView) findViewById(R.id.list);

        // Create the adapter.
        remindersAdapter = new ReminderAdapter(RemindersActivity.this, R.layout.reminder_list_item, reminders);
        todoList.setAdapter(remindersAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoList.setLongClickable(true);

        // On holding press an item, create this menu.
        todoList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                getMenuInflater().inflate(R.menu.edit_reminder, menu);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_reminder:
                // TODO @Samir55 add edit to sqllite
                return true;
            case R.id.delete_reminder:
                return true;
            default:
                return onContextItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_reminder) {
            // Include Text box and checkbox view.
            final View inputView = View.inflate(this, R.layout.dialog_box, null);

            // Create AlertDialog box.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Reminder");

            // Add textarea and checkbox.
            builder.setView(inputView);

            // Set up the buttons.
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Get the written text by the user
                    EditText reminderEditText = inputView.findViewById(R.id.reminder_text);
                    String reminderText = reminderEditText.getText().toString();

                    // Get the checkbox.
                    CheckBox isImportantCheckBox = inputView.findViewById(R.id.important);
                    Boolean isImportant = isImportantCheckBox.isChecked();

                    reminders.add(new Reminder(reminderText, isImportant));

                    todoList = (ListView) findViewById(R.id.list);
                    remindersAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        } else if (id == R.id.action_exit) {
            // Destroy the activity.
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
