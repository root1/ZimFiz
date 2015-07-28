package school;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.zimfiz.zimfiz.AppController;
import com.app.zimfiz.zimfiz.R;
import com.app.zimfiz.zimfiz.TransactionHistory;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Constants;


public class Classes extends ActionBarActivity{

    private Handler mHandler = new Handler();

    private Toolbar toolbar;

    ListView mListView;
    ProgressBar mProgress;

    RequestQueue mVolleyQueue;
    EfficientAdapter mAdapter;
    JsonObjectRequest jsonObjRequest;

    private static final String TAG = "Data";
    private List<Transactions> mDataList;
    private class Transactions {
        public String getStuden_name() {
            return studen_name;
        }

        public void setStuden_name(String studen_name) {
            this.studen_name = studen_name;
        }

        public String getStuden_class() {
            return studen_class;
        }

        public void setStuden_class(String studen_class) {
            this.studen_class = studen_class;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getTransaction_date() {
            return transaction_date;
        }

        public void setTransaction_date(String transaction_date) {
            this.transaction_date = transaction_date;
        }

        public String getTransaction_state() {
            return transaction_state;
        }

        public void setTransaction_state(String transaction_state) {
            this.transaction_state = transaction_state;
        }

        public String getPaid_by() {
            return paid_by;
        }

        public void setPaid_by(String paid_by) {
            this.paid_by = paid_by;
        }

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

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        private String studen_name;
        private String studen_class;
        private String amount;
        private String payment_method;
        private String transaction_date;
        private String transaction_state;
        private String paid_by;
        private String school_name;
        private String school_address;
        private String school_image;
        private String school_account_name;
        private String school_account_number;
        private String school_current_fees;
        private String bank_name;
        private String transactioID;


        public String getTransactioID() {
            return transactioID;
        }

        public void setTransactioID(String transactioID) {
            this.transactioID = transactioID;
        }
    }
    String form = "";
    String school_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_internet_lists_toolbar);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mVolleyQueue = Volley.newRequestQueue(this);
        mListView = (ListView) findViewById(R.id.ListView01);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mDataList = new ArrayList<Transactions>();


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        form = extras.getString("form");

        SharedPreferences sharedPreferences = getSharedPreferences("user_login", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", Constants.NOTAVAILABLE);
         school_id = sharedPreferences.getString("school_id", Constants.NOTAVAILABLE);

        if(!id.equals(Constants.NOTAVAILABLE)){
            callJsonArrayRequest(school_id, form);
        }


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

    private void callJsonArrayRequest(final String school_id, final String form) {
        showProgress();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.GETSCHOOLTRANSACTIONS,
                createMyReqSuccessListenerMerchant(),
                createMyReqErrorListener()) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("form", form);
                params.put("school_id", school_id);
                return params;
            }

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

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.transactions_list_item, null);
                holder = new ViewHolder();
                holder.schoolName = (TextView)convertView.findViewById(R.id.schoolName);
                holder.date = (TextView)convertView.findViewById(R.id.date);
                holder. summary = (TextView)convertView.findViewById(R.id.transaction_summary);
                holder. schoolImage = (ImageView)convertView.findViewById(R.id.schoolImage);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.schoolName.setText(mDataList.get(position).getStuden_name());
            holder.summary.setText("Payment by "+mDataList.get(position).getPaid_by()+" of "+mDataList.get(position).getAmount());
            holder.date.setText(mDataList.get(position).getTransaction_date());

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
                    .buildRound(String.valueOf(mDataList.get(position).getSchool_name().charAt(0)), color);
            Picasso.with(getApplicationContext())
                    .load(mDataList.get(position).getSchool_image())
                    .placeholder(drawable)
                    .error(drawable)
                    .into(holder.schoolImage);



            mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    Intent i = new Intent(getApplicationContext(), TransactionHistory.class);
                    Bundle extras = new Bundle();
                    extras.putString("sBankName", (mDataList.get(position).getBank_name()));
                    extras.putString("sAccountNumber", (mDataList.get(position).getSchool_account_number()));
                    extras.putString("sAccountName", (mDataList.get(position).getSchool_account_name()));
                    extras.putString("sSelectSchool", (mDataList.get(position).getSchool_name()));
                    extras.putString("sStudentName", (mDataList.get(position).getStuden_name()));
                    extras.putString("sClass", (mDataList.get(position).getStuden_class()));
                    extras.putString("sAmount", (mDataList.get(position).getAmount()));
                    extras.putString("sPaymentMethod", (mDataList.get(position).getPayment_method()));
                    extras.putString("sPaidBy", (mDataList.get(position).getPaid_by()));
                    extras.putString("sState", (mDataList.get(position).getTransaction_state()));
                    extras.putString("sTransactionId", (mDataList.get(position).getTransactioID()));
                    i.putExtras(extras);
                    startActivity(i);

                }
            });



            return convertView;

        }
        class ViewHolder {
            TextView schoolName;
            TextView date;
            TextView summary;
            ImageView schoolImage;

        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) Classes.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Classes.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
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
            mDataList.clear();
        }
    }

    private void showResults(String query) {

        makeSampleHttpRequest(query, form, school_id);
    }
    private void makeSampleHttpRequest(final String query, final String form, final String school_id) {
        showProgress();

        StringRequest jsonObjRequest2 = new StringRequest(Request.Method.POST,
                Constants.SCHOOLTRANSACTIONSSEARCH,
                createMyReqSuccessListenerMerchant(),
                createMyReqErrorListener()) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("form", form);
                params.put("school_id", school_id);
                params.put("search_query", query);
                return params;
            }

        };


        //AppController.getInstance().addToRequestQueue(jsonObjRequest2);
        mVolleyQueue.add(jsonObjRequest2);
       // Toast.makeText(getApplicationContext(), jsonObjRequest2.getUrl(), Toast.LENGTH_LONG).show();

    }
    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
                    Log.e("hjh", response);
                    //JSONObject homedata = new JSONObject(responses.getString("home"));
                    if(responses.has("details")) {

                        try {
                            JSONArray items = responses.getJSONArray("details");

                            for(int index = 0 ; index < items.length(); index++) {

                                JSONObject jsonObj = items.getJSONObject(index);
                                Transactions model = new Transactions();
                                model.setSchool_account_number(jsonObj.getString("school_account_number"));
                                model.setSchool_account_name(jsonObj.getString("school_account_name"));
                                model.setAmount(jsonObj.getString("amount"));
                                model.setBank_name(jsonObj.getString("bank_name"));
                                model.setSchool_address(jsonObj.getString("school_address"));
                                model.setSchool_image(jsonObj.getString("school_image"));
                                model.setPaid_by(jsonObj.getString("paid_by"));
                                model.setPayment_method(jsonObj.getString("payment_method"));
                                model.setSchool_current_fees(jsonObj.getString("school_current_fees"));
                                model.setSchool_name(jsonObj.getString("school_name"));
                                model.setStuden_class(jsonObj.getString("studen_class"));
                                model.setTransaction_state(jsonObj.getString("transaction_state"));

                                model.setTransactioID(jsonObj.getString("transacion_id"));
                                // model.setTransaction_date(jsonObj.getString("transaction_date"));
                                model.setStuden_name(jsonObj.getString("studen_name"));

                                DateTimeFormatter formatter = DateTimeFormat.forPattern("yy/MM/dd HH:mm:ss");
                                DateTime startTime = formatter.parseDateTime((jsonObj.getString("transaction_date").replace("-", "/")));

                                DateTime  endTime =  new DateTime() ;
                                int realMinutes = Minutes.minutesBetween(startTime, endTime).getMinutes();
                                int realHours = Hours.hoursBetween(startTime, endTime).getHours();
                                int realDays = Days.daysBetween(startTime, endTime).getDays();
                                int realSeconds = Seconds.secondsBetween(startTime, endTime).getSeconds();
                                int realWeeks = Weeks.weeksBetween(startTime, endTime).getWeeks();
                                int realMonths = Months.monthsBetween(startTime, endTime).getMonths();
                                String Valu = "";

                                if(realSeconds < 60){
                                    Valu = "just now";
                                }
                                if (realMinutes==1){
                                    Valu= "1 min";
                                }
                                if(realMinutes>= 2 && realMinutes<=59){
                                    Valu= realMinutes +" min";
                                }

                                if (realHours==1){
                                    Valu= "1 hr";
                                }

                                if(realHours>= 2 && realHours<=23){
                                    Valu= realHours +" hrs";
                                }

                                if (realDays==1){
                                    Valu= "yesterday";
                                }

                                if(realDays>= 2 && realDays<=13){
                                    Valu= realDays +" days";
                                }
                                if (realWeeks==1){
                                    Valu= "1 wk";
                                }
                                if(realWeeks>= 2 && realWeeks<=3){
                                    Valu= realWeeks +" wks";
                                }
                                if (realMonths==1){
                                    Valu= "1 m";
                                }
                                if(realMonths>= 2 && realMonths<=11){
                                    Valu= realMonths +" ms";
                                }


                                model.setTransaction_date(Valu);

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
