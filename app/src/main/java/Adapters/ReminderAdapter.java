package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
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

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        Reminder reminder = getItem(position);

        if (reminder != null) {
            TextView rightTextView = (TextView) view.findViewById(R.id.rightTextView);
            View rectangleView = (View) view.findViewById(R.id.importantRectangle);

            if (rightTextView != null) {
                rightTextView.setText(reminder.getText());
            }

            if (reminder.getImportant() == 1) {
                rectangleView.setBackgroundColor(Color.parseColor("#E22C29"));
            } else{
                rectangleView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        return view;
    }
}