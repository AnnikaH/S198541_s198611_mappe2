package com.example.s198541_s198611_mappe2;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerDialog extends DialogPreference {
    Context context;
    TimePickerDialog timePickerDialog;
    TimePicker timePicker;
    private final static String TAG = "TIME_DIALOG_PREFERENCE";
    private Listener timeListener;
    String key;

    public TimePickerDialog (Context context) {
        this(context, null);
        this.context=context;

    }

    public TimePickerDialog (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context=context;
    }

    public TimePickerDialog (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        timeListener = (Listener) context;
        this.context = context;
        setPositiveButtonText(getContext().getString(R.string.set));
        setNegativeButtonText(getContext().getString(R.string.cancel));

    }

    public void setListener(Listener listener){
        timeListener = listener;
    }

    @Override
    public View onCreateDialogView(){
        timePicker = new TimePicker(context);
        timePicker.setIs24HourView(true);
        return timePicker;
    }

    public void onDialogClosed(boolean positiveResult){
        if(timeListener != null && positiveResult){
            timeListener.setTime(key, timePicker.getHour(), timePicker.getMinute());
        }
    }

    public interface Listener {
        void setTime(String key,int hour,int minute);
    }
}