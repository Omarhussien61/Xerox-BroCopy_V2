package com.tariqs.xerox;

import android.view.View;

public class Interface {
    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view,int position);
    }

}
