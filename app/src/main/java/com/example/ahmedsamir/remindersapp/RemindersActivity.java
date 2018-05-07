package com.example.ahmedsamir.remindersapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapters.ReminderAdapter;
import DBHelper.DBHelper;
import Models.Reminder;

public class RemindersActivity extends AppCompatActivity {
    // SQLite database helper object.
    DBHelper dbHelper = new DBHelper(this);

    List<Reminder> reminders = new ArrayList<>();

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

        // Fetch from the database.
        reminders = dbHelper.getAllReminders();

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
        // Get the reminder position(index) in the array.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Integer reminderIndex = info.position;

        // Get the reminder object.
        Reminder reminder = reminders.get(reminderIndex);

        switch (item.getItemId()) {
            case R.id.edit_reminder:
                System.out.println(item + " index in the array" + reminderIndex);
                createDialogBox(this, "Edit Reminder", "Edit", true, reminder);
                return true;
            case R.id.delete_reminder:
                System.out.println("Delete the reminder of id: " + reminder.getId() + " " + reminder.getText());
                dbHelper.deleteReminder(reminder.getId());
                reloadAllData();
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
            createDialogBox(this, "Add Reminder", "Add", false, null);
        } else if (id == R.id.action_exit) {
            // Destroy the activity.
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void createDialogBox(Context context, String title, String buttonTitle, final Boolean isUpdate, final Reminder reminder) {
        // Include text box and checkbox view.
        final View inputView = View.inflate(context, R.layout.dialog_box, null);

        // Create AlertDialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        if (isUpdate) {
            EditText reminderEditText = inputView.findViewById(R.id.reminder_text);
            CheckBox isImportantCheckBox = inputView.findViewById(R.id.important);
            isImportantCheckBox.setChecked(reminder.getImportant() == 1);
            reminderEditText.setText(reminder.getText());
        }

        // Add text area and checkbox.
        builder.setView(inputView);

        // Set up the buttons.
        builder.setPositiveButton(buttonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the written text by the user
                EditText reminderEditText = inputView.findViewById(R.id.reminder_text);
                String reminderText = reminderEditText.getText().toString();

                // Get the checkbox.
                CheckBox isImportantCheckBox = inputView.findViewById(R.id.important);
                Integer isImportant = isImportantCheckBox.isChecked() ? 1 : 0;

                if (isUpdate) {
                    reminder.setText(reminderText);
                    reminder.setImportant(isImportant);
                    dbHelper.updateReminder(reminder);
                } else
                    dbHelper.addReminder(new Reminder(reminderText, isImportant));

                // Re-fetch from the database and notify.
                reminders = dbHelper.getAllReminders();

                todoList = (ListView) findViewById(R.id.list);

                // Refresh the reminders list.
                reloadAllData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * helper to show what happens when all data is new
     */
    private void reloadAllData() {
        reminders = dbHelper.getAllReminders();

        // Update data in our adapter.
        remindersAdapter.clear();

        remindersAdapter.addAll(reminders);

        // Fire the event.
        remindersAdapter.notifyDataSetChanged();
    }

}
