package com.example.hazardcrowdsourcing;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Administrator extends AppCompatActivity {

    TextView etdated,masa1,dated,time1;
    EditText typeofhazard,reporter,latitude,longitude;
    Button bback,ssubmit;

    DatePickerDialog.OnDateSetListener setListener;

    int t1hour,t1mins,t2hour,t2mins;

    RequestQueue queue;
    final String URL="https://projectict600.000webhostapp.com/information/api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        queue = Volley.newRequestQueue(getApplicationContext());


        ssubmit = findViewById(R.id.submit);

        typeofhazard = findViewById(R.id.typehazard);
        typeofhazard.setError("enter type of hazard");
        reporter = findViewById(R.id.reportername);
        reporter.setError("enter your name");

        latitude = findViewById(R.id.latt);
        latitude.setError("enter correct latitude");
        longitude = findViewById(R.id.lngg);
        longitude.setError("enter correct longitude");


        time1 = findViewById(R.id.time);
        time1.setError("enter time");

        dated = findViewById(R.id.dates);
        dated.setError("enter date");


        ssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeRequest();

            }

        });

        masa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Administrator.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour = hourOfDay;
                        t1mins = minute;

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, t1hour, t1mins);

                        masa1.setText(DateFormat.format("hh:mm aa", calendar));
                     }
                    }, 12, 0, false
                );

                timePickerDialog.updateTime(t1hour,t1mins);
                timePickerDialog.show();
            }
        });

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Administrator.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t2hour = hourOfDay;
                        t2mins = minute;

                        String time = t2hour + ":" + t2mins;
                        SimpleDateFormat f24hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = f24hours.parse(time);

                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");

                            time1.setText(f12Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },22,0,false);

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t2hour,t2mins);
                timePickerDialog.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Administrator.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                dated.setText(date);
            }
        };

        dated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Administrator.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        dated.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });
    }

    public void makeRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

            }
        }, errorListener){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                params.put("hazard",typeofhazard.getText().toString());
                params.put("time",time1.getText().toString());
                params.put("date",dated.getText().toString());
                params.put("people",reporter.getText().toString());
                params.put("lat",latitude.getText().toString());
                params.put("lng",longitude.getText().toString());

                return params;

            }

        };
        queue.add(stringRequest);

    }
    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

        }
    };
}