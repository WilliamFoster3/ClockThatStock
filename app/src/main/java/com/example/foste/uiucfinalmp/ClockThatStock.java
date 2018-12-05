package com.example.foste.uiucfinalmp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class ClockThatStock extends AppCompatActivity {
    private Button invis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_that_stock);

        invis = (Button) findViewById(R.id.btn_invis);
        invis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNext();
            }
        });
    }
public void openNext() {
        Intent intent = new Intent(this, InnerClock.class);
        startActivity(intent);
}
}
