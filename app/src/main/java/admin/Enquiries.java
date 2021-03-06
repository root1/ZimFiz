package admin;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.app.zimfiz.zimfiz.AppController;
import com.app.zimfiz.zimfiz.R;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import fragments.NotificationActivity;
import utils.Constants;

/**
 * Created by neokree on 24/11/14.
 */
public class Enquiries extends ActionBarActivity {



    ProgressBar mProgress;
    RequestQueue mVolleyQueue;
    ListView mListView;
    EfficientAdapter mAdapter;

    EfficientAdapter mContext;
    private Handler mHandler = new Handler();
    private static final String TAG_REQUEST = "MY_TAG";
    private static final String TAG = "TAG";
    private List<Notifications> mDataList;
    private class Notifications {
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


        private String school_name;
        private String school_address;
        private String school_image;

        public String getNotification_subject() {
            return notification_subject;
        }

        public void setNotification_subject(String notification_subject) {
            this.notification_subject = notification_subject;
        }

        public String getNotification_date() {
            return notification_date;
        }

        public void setNotification_date(String notification_date) {
            this.notification_date = notification_date;
        }

        public String getNotification_detail() {
            return notification_detail;
        }

        public void setNotification_detail(String notification_detail) {
            this.notification_detail = notification_detail;
        }

        private String notification_subject;
        private String notification_date;
        private String notification_detail;

        private String row_id;
        private String myschool_id;
        private String school_phone_number;
        private String school_email_address;

        public String getSchool_phone_number() {
            return school_phone_number;
        }

        public void setSchool_phone_number(String school_phone_number) {
            this.school_phone_number = school_phone_number;
        }

        public String getRow_id() {
            return row_id;
        }

        public void setRow_id(String row_id) {
            this.row_id = row_id;
        }

        public String getMyschool_id() {
            return myschool_id;
        }

        public void setMyschool_id(String myschool_id) {
            this.myschool_id = myschool_id;
        }

        public String getSchool_email_address() {
            return school_email_address;
        }

        public void setSchool_email_address(String school_email_address) {
            this.school_email_address = school_email_address;
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_internet_lists_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        mListView = (ListView) findViewById(R.id.ListView01);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mDataList = new ArrayList<Notifications>();
        mAdapter = new EfficientAdapter(this);
        mListView.setAdapter(mAdapter);

        makeSampleHttpRequest();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
        if(mProgress != null)
            mProgress.setVisibility(View.GONE);
    }
    private void makeSampleHttpRequest() {
        try{
            showProgress();
            JsonArrayRequest movieReq = new JsonArrayRequest(Constants.GETALLENQUIRIRIES,
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

                    stopProgress();

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
                Notifications model = new Notifications();
                model.setSchool_name(jsonObj.getString("school_name"));
                model.setSchool_image(jsonObj.getString("school_image"));
                model.setSchool_address(jsonObj.getString("school_address"));
               // model.setNotification_date(jsonObj.getString("notification_date"));
                model.setNotification_detail(jsonObj.getString("detail"));
                model.setNotification_subject(jsonObj.getString("subject"));

                model.setRow_id(jsonObj.getString("row_id"));

                model.setMyschool_id(jsonObj.getString("school_id"));
                model.setSchool_email_address(jsonObj.getString("school_email_address"));
                model.setSchool_phone_number(jsonObj.getString("school_phone_number"));


                DateTimeFormatter formatter = DateTimeFormat.forPattern("yy/MM/dd HH:mm:ss");
                DateTime startTime = formatter.parseDateTime((jsonObj.getString("date").replace("-", "/")));

                DateTime  endTime =  new DateTime() ;
                int realMinutes = Minutes.minutesBetween(startTime, endTime).getMinutes();
                int realHours = Hours.hoursBetween(startTime, endTime).getHours();
                int realDays = Days.daysBetween(startTime, endTime).getDays();
                int realSeconds = Seconds.secondsBetween(startTime, endTime).getSeconds();
                int realWeeks = Weeks.weeksBetween(startTime, endTime).getWeeks();
                int realMonths = Months.monthsBetween(startTime, endTime).getMonths();
                int realYears = Years.yearsBetween(startTime, endTime).getYears();
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
                    Valu= "1 week";
                }
                if(realWeeks>= 2 && realWeeks<=4){
                    Valu= realWeeks +" weeks";
                }
                if (realMonths==1){
                    Valu= "1 month";
                }
                if(realMonths>= 2 && realMonths<=12){
                    Valu= realMonths +" months ago";
                }
                if (realYears==1){
                    Valu= "a year ago";
                }
                model.setNotification_date(Valu);

                mDataList.add(model);

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        mAdapter.notifyDataSetChanged();
        if(!isCache){
            // Toast.makeText(getActivity(), "Cache not available..Loading from service", Toast.LENGTH_SHORT).show();
            stopProgress();
        }
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
                convertView = mInflater.inflate(R.layout.notifications_list_item, parent, false);
                holder = new ViewHolder();


                holder.schoolName = (TextView)convertView.findViewById(R.id.schoolName);
                holder.date = (TextView)convertView.findViewById(R.id.date);
                holder.notificationSubject = (TextView)convertView.findViewById(R.id.notification_subject);
                holder.schoolImage = (ImageView)convertView.findViewById(R.id.schoolImage);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }




            holder.schoolName.setText(mDataList.get(i).getSchool_name());
            holder.notificationSubject.setText(mDataList.get(i).getNotification_subject());
            holder.date.setText(mDataList.get(i).getNotification_date());

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
                    .buildRound(String.valueOf(mDataList.get(i).getSchool_name().charAt(0)), color);
            Picasso.with(getApplicationContext())
                    .load(mDataList.get(i).getSchool_image())
                    .placeholder(drawable)
                    .error(drawable)
                    .into(holder.schoolImage);



            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {


                    Intent intent = new Intent(getApplicationContext(), Enquiry.class);


                    intent.putExtra("dateSent",mDataList.get(position).getNotification_date());
                    intent.putExtra("subject",mDataList.get(position).getNotification_subject());
                    intent.putExtra("detail",mDataList.get(position).getNotification_detail());
                    intent.putExtra("schoolName",mDataList.get(position).getSchool_name());
                    intent.putExtra("schoolImage",mDataList.get(position).getSchool_image());

                    intent.putExtra("school_id",mDataList.get(position).getMyschool_id());
                    intent.putExtra("row_id",mDataList.get(position).getRow_id());
                    intent.putExtra("sPhone",mDataList.get(position).getSchool_phone_number());
                    intent.putExtra("sEmail",mDataList.get(position).getSchool_email_address());

                    startActivity(intent);

                }
            });

            return convertView;

        }
        class ViewHolder {
            TextView schoolName;
            TextView date;
            TextView notificationSubject;
            ImageView schoolImage;

        }

    }

}
