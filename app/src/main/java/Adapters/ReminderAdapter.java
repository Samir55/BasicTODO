package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ahmedsamir.remindersapp.R;

import java.util.List;

import Models.Reminder;

public class ReminderAdapter extends ArrayAdapter<Reminder> {

    private int layoutResource;

    public ReminderAdapter(Context context, int layoutResource, List<Reminder> reminders) {
        super(context, layoutResource, reminders);
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        Reminder reminder = getItem(position);

        if (reminder != null) {
            TextView rightTextView = (TextView) view.findViewById(R.id.rightTextView);

            if (rightTextView != null) {
                rightTextView.setText(reminder.getText());
            }
        }

        return view;
    }
}