package DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Models.Reminder;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATA_BASE = "todo_app";
    public static final Integer DATA_BASE_VERSION = 1;
    public static final String TABLE_REMINDERS = "reminders";

    public static final String FIELD_REMINDERS_ID = "_id";
    public static final String FIELD_REMINDERS_TEXT = "text";
    public static final String FIELD_REMINDERS_IMPORTANT = "important";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(Context context) {
        super(context, DATA_BASE, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_REMINDERS + "( "
                + FIELD_REMINDERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_REMINDERS_TEXT + " TEXT, "
                + FIELD_REMINDERS_IMPORTANT + " INTEGER)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);

        // Create tables again.
        onCreate(sqLiteDatabase);
    }

    /**
     * Add a new reminder in the database.
     *
     * @param reminder
     */
    public void addReminder(Reminder reminder) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // Adding reminder text.
        values.put(FIELD_REMINDERS_TEXT, reminder.getText());

        // Adding reminder importance.
        values.put(FIELD_REMINDERS_IMPORTANT, reminder.getImportant());

        // Inserting Row.
        db.insert(TABLE_REMINDERS, null, values);

        // Closing database connection.
        db.close();
    }

    /**
     * Get all reminders stored in the database in an array list.
     *
     * @return ArrayList
     */
    public List<Reminder> getAllReminders() {
        List<Reminder> remindersList = new ArrayList<Reminder>();

        // Running the select all reminders query.
        String selectQuery = "SELECT  * FROM " + TABLE_REMINDERS;

        // Getting an instance (object) of SQLite database.
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows (reminders) and push each reminder to the remindersList.

        if (cursor.moveToFirst()) {
            do {

                Reminder reminder = new Reminder();
                reminder.setId(cursor.getInt(0));
                reminder.setText(cursor.getString(1));
                reminder.setImportant(cursor.getInt(2));

                // Push this reminder to the remindersList.
                remindersList.add(reminder);
            } while (cursor.moveToNext());
        }

        return remindersList;
    }


    /**
     * Update a reminder in the database.
     *
     * @param reminder
     */
    public void updateReminder(Reminder reminder) {

        // Getting an instance (object) of SQLite database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating a new ContentValues object and add the updated reminder info (text and isImportant).
        ContentValues values = new ContentValues();
        values.put(FIELD_REMINDERS_TEXT, reminder.getText());
        values.put(FIELD_REMINDERS_IMPORTANT, reminder.getImportant());

        // Running the update query.
        db.update(TABLE_REMINDERS, values, FIELD_REMINDERS_ID + " = ?",
                new String[]{String.valueOf(reminder.getId())});
    }

    public void deleteReminder(Integer reminderID) {
        // Getting an instance (object) of SQLite database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create the delete query.
        String where = FIELD_REMINDERS_ID + " = " + reminderID.toString();
        System.out.println("Query " + where);
        // Running the delete query.
        System.out.println(db.delete(TABLE_REMINDERS, where, null));
    }
}
