package com.app.zimfiz.zimfiz;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.SchoolsDatabaseAdapter;
import classes.School;
import utils.Constants;

public class JsonRequests {
   // private static Context context;
    private static SchoolsDatabaseAdapter schoolsDatabase;

    private static RequestQueue mVolleyQueue;

    public static void schoolsDataRequest(Context context){
        schoolsDatabase = new SchoolsDatabaseAdapter(context);
        try{

            JsonArrayRequest movieReq = new JsonArrayRequest(Constants.GETALLSCHOOLS,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            setData(response);
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if( error instanceof NetworkError) {

                    } else if( error instanceof ServerError) {
                    } else if( error instanceof AuthFailureError) {
                    } else if( error instanceof ParseError) {
                    } else if( error instanceof NoConnectionError) {
                    } else if( error instanceof TimeoutError) {
                    }
                }
            });
            mVolleyQueue = Volley.newRequestQueue(context);
            mVolleyQueue.add(movieReq);
        }

        catch (Exception e) {
            e.printStackTrace();

        }

    }
    private static void setData(JSONArray response){
        Log.d("Data", response.toString());
        List<School> schoolsArray = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObj = response.getJSONObject(i);
                String school_id = jsonObj.getString("school_id");
                String school_name = jsonObj.getString("school_name");
                String school_address = jsonObj.getString("school_address");
                String school_image = jsonObj.getString("school_image");
                String school_bank_id = jsonObj.getString("school_bank_id");
                String school_account_name = jsonObj.getString("school_account_name");
                String school_account_number = jsonObj.getString("school_account_number");
                String school_current_fees = jsonObj.getString("school_current_fees");

                schoolsArray.add(new School(school_name, school_address, school_account_name,
                        school_account_number, school_id, school_bank_id, school_current_fees, school_image));


            }
            schoolsDatabase.insertData(schoolsArray);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        // schoolsListAdapter.notifyDataSetChanged();

    }

    public static void notificationsDataRequest(){

    }

    public static void banksDataRequest(){

    }
}
