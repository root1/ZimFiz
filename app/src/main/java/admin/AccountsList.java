package admin;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.app.zimfiz.zimfiz.AppController;
import com.app.zimfiz.zimfiz.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Constants;


public class AccountsList extends ActionBarActivity{
    private Toolbar toolbar;

    ProgressBar mProgress;
    RequestQueue mVolleyQueue;
    ListView mListView;
    EfficientAdapter mAdapter;

    EfficientAdapter mContext;
    private Handler mHandler = new Handler();
    private static final String TAG_REQUEST = "MY_TAG";
    private static final String TAG = "TAG";
    private List<SchoolsList> mDataList;
    private class SchoolsList {
        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        private String school_id;

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSchool_address() {
            return school_address;
        }

        public void setSchool_address(String school_address) {
            this.school_address = school_address;
        }

        public String getSchool_image() {
            return school_image;
        }

        public void setSchool_image(String school_image) {
            this.school_image = school_image;
        }

        public String getSchool_bank_id() {
            return school_bank_id;
        }

        public void setSchool_bank_id(String school_bank_id) {
            this.school_bank_id = school_bank_id;
        }

        public String getSchool_account_name() {
            return school_account_name;
        }

        public void setSchool_account_name(String school_account_name) {
            this.school_account_name = school_account_name;
        }

        public String getSchool_account_number() {
            return school_account_number;
        }

        public void setSchool_account_number(String school_account_number) {
            this.school_account_number = school_account_number;
        }



        private String school_name;
        private String school_address;
        private String school_image;
        private String school_bank_id;
        private String school_account_name;
        private String school_account_number;

        public String getSchool_bank_name() {
            return school_bank_name;
        }

        public void setSchool_bank_name(String school_bank_name) {
            this.school_bank_name = school_bank_name;
        }

        private String school_bank_name;
    }
    String sSchoolId, email, phone, name;
    String schoolType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_school);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
         sSchoolId = extras.getString("sSchoolId");
         schoolType = extras.getString("type");

        email = extras.getString("email");
        phone = extras.getString("phone");
        name = extras.getString("name");

        mListView = (ListView) findViewById(R.id.ListView01);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);

        callJsonArrayRequest(sSchoolId);
        mDataList = new ArrayList<SchoolsList>();
        mAdapter = new EfficientAdapter(this);
        mListView.setAdapter(mAdapter);


    }
    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
        if(mProgress != null)
            mProgress.setVisibility(View.GONE);
    }
    private void callJsonArrayRequest(final String sSchoolId) {
        showProgress();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.GETSCHOOLACCOUNTS,
                createMyReqSuccessListenerMerchant(),
                createMyReqErrorListener()) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("school_id", sSchoolId);
                return params;
            };

        };
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
        //mVolleyQueue.add(jsonObjRequest);
    }

    private void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    private void stopProgress() {
        mProgress.setVisibility(View.GONE);
    }
    private  class EfficientAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public EfficientAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return mDataList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int i, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.school_list_item, parent, false);
                holder = new ViewHolder();
                holder.schoolName = (TextView)convertView.findViewById(R.id.schoolName);
                holder.schoolAddress = (TextView)convertView.findViewById(R.id.schoolAddress);
                holder.schoolImage = (ImageView)convertView.findViewById(R.id.schoolImage);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.schoolName.setText(mDataList.get(i).school_name);
            holder.schoolAddress.setText(mDataList.get(i).school_address);
            Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/RobotoSlab-Light.ttf");
            ColorGenerator generator = ColorGenerator.DEFAULT;
            int color = generator.getRandomColor();
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.WHITE)
                    .toUpperCase()
                    .fontSize(30)
                    .useFont(custom_font)
                    .endConfig()
                    .buildRound(String.valueOf(mDataList.get(i).school_name.charAt(0)), color);
            Picasso.with(AccountsList.this)
                    .load(mDataList.get(i).school_image)
                    .placeholder(drawable)
                    .error(drawable)
                    .into(holder.schoolImage);


            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int pos, long arg3) {
//                    Intent myIntent = new Intent(AccountsList.this, EditContact.class);
//                    Bundle extras = new Bundle();
//                    extras.putString("sSchoolId", mDataList.get(pos).getSchool_id());
//                    myIntent.putExtras(extras);
//                    startActivity(myIntent);

                }
            });

            return convertView;

        }
        class ViewHolder {
            TextView schoolName;
            TextView schoolAddress;
            ImageView schoolImage;

        }

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_account, menu);
        return true;
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        if(id == R.id.add_account){

            Intent myIntent = new Intent(AccountsList.this, CreateSchoolAccount.class);
            Bundle extras = new Bundle();

            extras.putString("sSchoolId", sSchoolId);
            extras.putString("schoolType", schoolType);

            extras.putString("email", email);
            extras.putString("phone", phone);
            extras.putString("name", name);

            myIntent.putExtras(extras);
            startActivity(myIntent);

        }

        return super.onOptionsItemSelected(item);

    }
    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                stopProgress();
            }
        };
    }

    private Response.Listener<String> createMyReqSuccessListenerMerchant() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stopProgress();
                try {
                    JSONObject responses =new JSONObject(response) ;
                    //JSONObject homedata = new JSONObject(responses.getString("home"));
                    if(responses.has("details")) {

                        try {
                            JSONArray items = responses.getJSONArray("details");

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObj = items.getJSONObject(i);
                                SchoolsList model = new SchoolsList();
                                model.setSchool_name(jsonObj.getString("school_name"));
                                model.setSchool_image(jsonObj.getString("school_image"));
                                model.setSchool_address(jsonObj.getString("user_phone"));

                                mDataList.add(model);

                            }
                        }catch (Exception e) {

                        }

                    }
                    mAdapter.notifyDataSetChanged();

                }catch (JSONException e) {
                    // TODO Auto-generated catch block

                }
            }
        };
    }
}
