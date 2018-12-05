package com.example.foste.uiucfinalmp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InnerClock extends AppCompatActivity {
    private Button inner;
//    private int years;
//    InnerClock(int years) {
//    this.years = years;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_clock);

        inner = (Button) findViewById(R.id.btn_inner);
        inner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNext();
            }
        });
    }

//    public void showGraph (/*api*/) {
//        for (int i = 0; i < years; i++) {
//
//    }
    public void openNext() {
        Intent intent = new Intent(this, ClockThatStock.class);
        startActivity(intent);
    }
}
