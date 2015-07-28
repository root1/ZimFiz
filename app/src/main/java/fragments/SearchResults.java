package fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.zimfiz.zimfiz.AppController;
import com.app.zimfiz.zimfiz.HighSchoolPayment;
import com.app.zimfiz.zimfiz.R;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Constants;


/**
 * Created by neokree on 12/12/14.
 */
public class SearchResults extends ActionBarActivity {
    ProgressBar mProgress;

    ListView mListView;
    EfficientAdapter mAdapter;
    private Handler mHandler = new Handler();
    private static final String TAG_REQUEST = "MY_TAG";
    private static final String TAG = "TAG";
    private List<Schools> mDataList;

    private class Schools {
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

        public String getSchool_current_fees() {
            return school_current_fees;
        }

        public void setSchool_current_fees(String school_current_fees) {
            this.school_current_fees = school_current_fees;
        }

        private String school_name;
        private String school_address;
        private String school_image;
        private String school_bank_id;
        private String school_account_name;
        private String school_account_number;
        private String school_current_fees;

        public String getSchool_bank_name() {
            return school_bank_name;
        }

        public void setSchool_bank_name(String school_bank_name) {
            this.school_bank_name = school_bank_name;
        }

        private String school_bank_name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_school);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView = (ListView) findViewById(R.id.ListView01);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mDataList = new ArrayList<Schools>();
        mAdapter = new EfficientAdapter(this);
        mListView.setAdapter(mAdapter);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            showResults(query);
        }
    }

    private void showResults(String query) {
        Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
        makeSampleHttpRequest(query);
    }
    private void makeSampleHttpRequest(final String query) {
        showProgress();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, Constants.SCHOOLSSEARCH+"?search_query="+query,
                createMyReqSuccessListenerMerchant(),
                createMyReqErrorListener()) {


        };
        AppController.getInstance().addToRequestQueue(jsonObjRequest);

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
                                Schools model = new Schools();
                                model.setSchool_name(jsonObj.getString("school_name"));
                                model.setSchool_image(jsonObj.getString("school_image"));
                                model.setSchool_address(jsonObj.getString("school_address"));
                                model.setSchool_account_name(jsonObj.getString("school_account_name"));
                                model.setSchool_account_number(jsonObj.getString("school_account_number"));
                                model.setSchool_bank_name(jsonObj.getString("bank_name"));
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
    private void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    private void stopProgress() {
        mProgress.setVisibility(View.GONE);

    }
    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
        if (mProgress != null)
            mProgress.setVisibility(View.GONE);
    }

    private class EfficientAdapter extends BaseAdapter {

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
                holder.schoolName = (TextView) convertView.findViewById(R.id.schoolName);
                holder.schoolAddress = (TextView) convertView.findViewById(R.id.schoolAddress);
                holder.schoolImage = (ImageView) convertView.findViewById(R.id.schoolImage);
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
            Picasso.with(SearchResults.this)
                    .load(mDataList.get(i).school_image)
                    .placeholder(drawable)
                    .error(drawable)
                    .into(holder.schoolImage);


            mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int pos, long arg3) {
                    Intent myIntent = new Intent();
                    Toast.makeText(getApplicationContext(), mDataList.get(pos).getSchool_account_number(), Toast.LENGTH_LONG).show();
                    myIntent.putExtra("accountNumber", mDataList.get(pos).getSchool_account_number());
                    myIntent.putExtra("schoolName", mDataList.get(pos).getSchool_name());
                    myIntent.putExtra("bankName", mDataList.get(pos).getSchool_bank_name());
                    myIntent.putExtra("accountName", mDataList.get(pos).getSchool_account_name());
                    setResult(RESULT_OK, myIntent);
                    finish();

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
}
