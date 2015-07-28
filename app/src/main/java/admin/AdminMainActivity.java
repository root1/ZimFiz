package admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.zimfiz.zimfiz.MainActivity;
import com.app.zimfiz.zimfiz.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.Constants;


/**
 * Created by neokree on 12/12/14.
 */
public class AdminMainActivity extends ActionBarActivity {
    TextView mSchools;
    TextView mTransactions;
    TextView mUsers;
    RequestQueue mVolleyQueue;
    JsonObjectRequest jsonObjRequest;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        mSchools = (TextView) findViewById(R.id.number_schools);
        mTransactions = (TextView) findViewById(R.id.number_trans);
        mUsers = (TextView) findViewById(R.id.number_users);
        mVolleyQueue = Volley.newRequestQueue(this);
        mProgress = new ProgressDialog(AdminMainActivity.this);
        mProgress.setMessage("Please wait...");

        makehttprequest();

    }

    private void makehttprequest() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.COUNT,
                loginSuccessListener(),
                loginErrorListener()) {

        };
        mVolleyQueue.add(jsonObjRequest);
    }



    private Response.ErrorListener loginErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (mProgress.isShowing())
                    mProgress.dismiss();
                new AlertDialog.Builder(AdminMainActivity.this)
                        .setCancelable(false)
                        .setMessage("Network unavailable. Please try again later")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which ) {

                            }

                        })
                        .show();
            }
        };
    }

    private Response.Listener<String> loginSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject responses =new JSONObject(response) ;
                    String status = responses.getString("schools");
                    if(status.equals("") || status.equals(null)){
                        String message = responses.getString("Error getting data from the server");
                        if (mProgress.isShowing())
                            mProgress.dismiss();
                        new AlertDialog.Builder(AdminMainActivity.this)
                                .setTitle(status)
                                .setCancelable(false)
                                .setMessage(message)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which ) {

                                    }

                                })
                                .show();
                    }else{


                        String schools = responses.getString("schools");
                        String users = responses.getString("users");
                        String transaction_log = responses.getString("transaction_log");
                        String banks = responses.getString("banks");

                        if (mProgress.isShowing())
                            mProgress.dismiss();
                        mSchools.setText(schools);
                        mUsers.setText(users);
                        mTransactions.setText(transaction_log);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        if(id == R.id.schools){

            startActivity(new Intent(AdminMainActivity.this, Schools.class));
        }
        if(id == R.id.add_school){

            startActivity(new Intent(AdminMainActivity.this, AddSchool.class));
        }
        if(id == R.id.banks){

            startActivity(new Intent(AdminMainActivity.this, Banks.class));
        }
        if(id == R.id.add_school_account){

            startActivity(new Intent(AdminMainActivity.this, SchoolsAddAccount.class));
        }
        if(id == R.id.view_enquiries){

            startActivity(new Intent(AdminMainActivity.this, Enquiries.class));
        }

        return super.onOptionsItemSelected(item);

    }
}
