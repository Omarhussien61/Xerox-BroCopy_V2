package com.tariqs.xerox;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MyDatePickerFragment extends DialogFragment {
   String Y;
   String M;
   String D;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    public DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                  Context c;
                    Y = String.valueOf(view.getYear());
                    M = String.valueOf((view.getMonth()+1));
                    D = String.valueOf(view.getDayOfMonth());
                    Intent intent = new Intent("custom-message");
                    intent.putExtra("Date",Y+"/"+M+"/"+D);
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                    Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                            " / " + (view.getMonth()+1) +
                            " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                }
            };
}
