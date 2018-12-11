package com.example.foste.uiucfinalmp;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;




import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


import org.json.JSONObject;

public class InnerClock extends AppCompatActivity {
    private Button inner;
//    private int years;
//    InnerClock(int years) {
//    this.years = years;
//    }
   // LineGraphSeries<DataPoint> series;

    private static final String TAG = "Lab12:Main";

    // request queue for our network requests
    private static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_inner_clock);
        final Button startAPICall = findViewById(R.id.Api);
        buttoner(startAPICall,"https://api.iextrading.com/1.0/stock/AAPL/batch?types=quote");
        final Button googleStart = findViewById(R.id.google);
        buttoner(googleStart, "https://api.iextrading.com/1.0/stock/GOOGL/batch?types=quote");
        final Button microSoftStart = findViewById(R.id.microsoftButton);
        buttoner(microSoftStart, "https://api.iextrading.com/1.0/stock/MSFT/batch?types=quote" );
        final Button amazonStart = findViewById(R.id.amazon);
        buttoner(amazonStart, "https://api.iextrading.com/1.0/stock/AMZN/batch?types=quote");
//        final Button fbStart = findViewById(R.id.fb);
//        buttoner(fbStart, "https://api.iextrading.com/1.0/stock/FB/batch?types=quote");
        inner = (Button) findViewById(R.id.btn_inner);
        inner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNext();
            }
        });
//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        series = new LineGraphSeries<DataPoint>();
//        for (int i = 0; i < 500; i++) {
//            x = x + 0.1;
//            y = Math.sin(x);
//            series.appendData(new DataPoint(x ,y), true, 500);
//        }
//        graph.addSeries(series);

    }

    public void openNext() {
        Intent intent = new Intent(this, ClockThatStock.class);
        startActivity(intent);
    }
    void startAPICall (String setURL) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    setURL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            TextView responseText = findViewById(R.id.textView12);
                            TextView responseTextLow = findViewById(R.id.textView13);
                            TextView responseTextShouldYouInvest = findViewById(R.id.textView14);
                            TextView responseTextCompany = findViewById(R.id.textView9);
                            TextView responseTextChangePercent = findViewById(R.id.changePercent);
                            try {
                                /**
                                 * {
                                 *     "quote": {
                                 *         "changePercent": "-0.44"
                                 *     }
                                 * }
                                 */
                                String dataHigh = response.getJSONObject("quote").getString("high");
                                String dataLow = response.getJSONObject("quote").getString("low");
                                String dataSymbol = response.getJSONObject("quote").getString("symbol");
                                String sharePriceData = response.getJSONObject("quote").getString("peRatio");
                                Double percentChange = response.getJSONObject("quote").getDouble("extendedChangePercent");

                                responseTextShouldYouInvest.setText("pe Ratio: " + sharePriceData);
                                responseText.setText("Maximum: " + dataHigh);
                                responseTextLow.setText("Minimum: " + dataLow);
                                responseTextCompany.setTextColor(Color.BLACK);
                                responseTextCompany.setText("Company: " + dataSymbol);
                                responseTextChangePercent.setBackgroundColor(Color.BLACK);
                                responseTextChangePercent.setTextColor(Color.WHITE);
                                responseTextChangePercent.setText("Change Percent: " + percentChange);
                                double y;
                                double x = 0.0;
                                GraphView graph = (GraphView) findViewById(R.id.graph);
                                graph.removeAllSeries();
                                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
                                graph.setTitle(dataSymbol);
                                graph.setTitleColor(Color.GREEN);
                                for (int i = 0; i < 500; i++) {
                                    x = x + 0.1;
                                    y =  percentChange * x;
                                    series.appendData(new DataPoint(x ,y), true, 500);
                                }
                                graph.addSeries(series);
                            } catch (Exception e) {
                                Log.d(TAG, "error");
                            }
                            Log.d(TAG, String.format("%s\n", responseText));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void buttoner(Button b, final String setURL) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "buttoner in action");
                startAPICall(setURL);
            }
        });
    }
}
