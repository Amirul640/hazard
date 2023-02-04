package com.example.hazardcrowdsourcing;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminView extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;

    private String JSON_STRING;
    public static final String TAG_JSON_ARRAY="result";
    public static final String EMP_ID = "emp_id";
    public static final String TAG_ID = "id";
    public static final String TAG_HAZARD = "hazard";
    public static final String TAG_REPORTER = "people";
    public static final String TAG_DATE = "date";
    public static final String TAG_TIME = "time";
    public static final String TAG_LAT = "lat";
    public static final String TAG_LNG = "lng";
    public static final String URL_GET_ALL = "https://projectict600.000webhostapp.com/information/viewall.php"; //link webhost
    public static final String URL_DELETE = "https://projectict600.000webhostapp.com/information/delete.php";

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        Intent intent = getIntent();

        id = intent.getStringExtra(EMP_ID);

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                Toast.makeText(AdminView.this,"Clicked", Toast.LENGTH_LONG).show();
                confirmDelete();
            }
        });
        getJSON();

    }

    private void adding() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(TAG_ID);
                String hazard = jo.getString(TAG_HAZARD);
                String dated = jo.getString(TAG_DATE);
                String timed = jo.getString(TAG_TIME);
                String reporter = jo.getString(TAG_REPORTER);
                String lat = jo.getString(TAG_LAT);
                String lng = jo.getString(TAG_LNG);

                HashMap<String, String> admin = new HashMap<>();
                admin.put(TAG_ID, id);
                admin.put(TAG_HAZARD, "Type of Hazard : "+hazard);
                admin.put(TAG_DATE, "Date Reported : "+dated);
                admin.put(TAG_TIME,"Time Reported : " +timed);
                admin.put(TAG_REPORTER,"Reporter Name : "+reporter);
                admin.put(TAG_LAT,"Latitude : " +lat+"°");
                admin.put(TAG_LNG,"Longitude : " +lng+"°");

                list.add(admin);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                AdminView.this, list, R.layout.list_item,
                new String[]{TAG_ID,TAG_HAZARD,TAG_DATE,TAG_TIME,TAG_REPORTER,TAG_LAT,TAG_LNG},
                new int[]{R.id.id,R.id.hazard, R.id.dated,R.id.timed, R.id.people,R.id.lat,R.id.lng});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AdminView.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                adding();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, AdminView.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(TAG_ID).toString();
        intent.putExtra(EMP_ID,empId);
        startActivity(intent);
    }
   public void onBackPressed(){
      Intent a = new Intent(Intent.ACTION_MAIN);
      a.addCategory(Intent.CATEGORY_HOME);
      a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(a);
  }
 private void delete(){
     class DeleteEmployee extends AsyncTask<Void,Void,String> {
         ProgressDialog loading;

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             loading = ProgressDialog.show(AdminView.this, "Deleting...", "Wait...", false, false);
         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
             loading.dismiss();
             Toast.makeText(AdminView.this, s, Toast.LENGTH_LONG).show();
         }

         @Override
         protected String doInBackground(Void... params) {
             RequestHandler rh = new RequestHandler();
             String s = rh.sendGetRequestParam(URL_DELETE, id);
             return s;
         }
     }

     DeleteEmployee de = new DeleteEmployee();
     de.execute();
 }
   private void deletedate(){
        StringRequest request = new StringRequest(Request.Method.POST, "https://projectict600.000webhostapp.com/information/delete.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("Data Deleted")){
                            Toast.makeText(AdminView.this,"Data Deleted Successfully",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(AdminView.this,"Data Not Deleted",Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminView.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                return  super.getParams();
            }
        };
    }
 private void confirmDelete(){
     AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
     alertDialogBuilder.setMessage("Are you sure you want to delete ?");

     alertDialogBuilder.setPositiveButton("Yes",
             new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface arg0, int arg1) {
                     delete();
                     startActivity(new Intent(AdminView.this,AdminView.class));
                 }
             });

     alertDialogBuilder.setNegativeButton("No",
             new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface arg0, int arg1) {

                 }
             });

     AlertDialog alertDialog = alertDialogBuilder.create();
     alertDialog.show();
 }

    @Override
    public void onClick(View v) {

    }
}