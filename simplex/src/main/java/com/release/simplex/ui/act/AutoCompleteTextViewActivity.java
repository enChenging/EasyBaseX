package com.release.simplex.ui.act;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.release.simplex.R;
import com.release.simplex.ui.adapter.AutoCompleteAdapter;
import com.release.simplex.widget.CustomAutoCompleteTextView;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class AutoCompleteTextViewActivity extends AppCompatActivity {


    private CustomAutoCompleteTextView userName;
    private AutoCompleteAdapter adapter;
    String[] strArray = {"1", "2", "3", "13", "19"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete_text_view);
        userName = findViewById(R.id.userName);
        View cl = findViewById(R.id.cl);


        initAutoComplete();

        cl.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                cl.setFocusable(true);
                cl.setFocusableInTouchMode(true);
                cl.requestFocus();

                return false;
            }
        });
    }


    private void initAutoComplete() {

        adapter = new AutoCompleteAdapter(AutoCompleteTextViewActivity.this, Arrays.asList(strArray));
        userName.setAdapter(adapter);
    }
}