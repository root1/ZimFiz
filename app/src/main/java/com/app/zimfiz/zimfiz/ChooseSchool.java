package com.app.zimfiz.zimfiz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import utils.AutoCompleteAdapter;
import utils.Constants;


public class ChooseSchool extends ActionBarActivity {
    AutoCompleteTextView school_one, school_two, school_three, school_four, school_five;
    ProgressDialog mProgress;
    RequestQueue mVolleyQueue;
    private static final String TAG = "TAG";
    ArrayList<String> searchArrayList;
    Button mContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_schools);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        school_one = (AutoCompleteTextView)findViewById(R.id.school_one);
        school_two = (AutoCompleteTextView)findViewById(R.id.school_two);
        school_three = (AutoCompleteTextView)findViewById(R.id.school_three);
        school_four = (AutoCompleteTextView)findViewById(R.id.school_four);
        school_five = (AutoCompleteTextView)findViewById(R.id.school_five);
        mContinue = (Button)findViewById(R.id.btncontinue);

        mVolleyQueue = Volley.newRequestQueue(this);
        mProgress = new ProgressDialog(ChooseSchool.this);
        mProgress.setMessage("Please wait...");
        searchArrayList = new ArrayList<>();

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Constants.GETALLSCHOOLS);
        if(entry != null){
            //Cache data available.
            try {
                String data = new String(entry.data, "UTF-8");
                Log.d("CACHE DATA", data);
                JSONArray jsonArray=new JSONArray(data);
                setData(jsonArray,true);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            makeSampleHttpRequest();
        }

        AutoCompleteAdapter adapter = new AutoCompleteAdapter(this,
                R.layout.layout, R.id.textView1, searchArrayList);

        school_one.setAdapter(adapter);
        school_two.setAdapter(adapter);
        school_three.setAdapter(adapter);
        school_four.setAdapter(adapter);
        school_five.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_form_one_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
        if (mProgress.isShowing())
            mProgress.dismiss();
    }
    private void makeSampleHttpRequest() {
        try{
            mProgress.show();
            JsonArrayRequest movieReq = new JsonArrayRequest(Constants.GETALLSCHOOLS,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            setData(response,false);
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

                    if (mProgress.isShowing())
                        mProgress.dismiss();
                }
            });
            AppController.getInstance().addToRequestQueue(movieReq);
            //mVolleyQueue.add(movieReq);
        }

        catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setData(JSONArray response,Boolean isCache){
        Log.d(TAG, response.toString());
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObj = response.getJSONObject(i);


                searchArrayList.add(jsonObj.getString("school_name"));


            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        if(!isCache){
            // Toast.makeText(getActivity(), "Cache not available..Loading from service", Toast.LENGTH_SHORT).show();
            if (mProgress.isShowing())
                mProgress.dismiss();
        }
    }

}
