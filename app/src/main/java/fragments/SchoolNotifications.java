package fragments;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.SchoolsListAdapter;
import utils.Constants;

/**
 * Created by neokree on 24/11/14.
 */
public class SchoolNotifications extends Fragment{



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

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_internet_lists, container, false);
        return rootView;

    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        mListView = (ListView) getView().findViewById(R.id.ListView01);
        mProgress = (ProgressBar) getView().findViewById(R.id.progressBar);
        mDataList = new ArrayList<Notifications>();
        mAdapter = new EfficientAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);

        String id = sharedPreferences.getString("id", Constants.NOTAVAILABLE);
        if(!id.equals(Constants.NOTAVAILABLE)){
            callJsonArrayRequest(id);
        }


    }
    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
        if(mProgress != null)
            mProgress.setVisibility(View.GONE);
    }
    private void callJsonArrayRequest(final String userId) {
        showProgress();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.GETALLNOTIFICATIONS,
                createMyReqSuccessListenerMerchant(),
                createMyReqErrorListener()) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userId);
                return params;
            };

        };
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
        //mVolleyQueue.add(jsonObjRequest);
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
                    Log.e("response", response);
                    //JSONObject homedata = new JSONObject(responses.getString("home"));
                    if(responses.has("details")) {

                        try {
                            JSONArray items = responses.getJSONArray("details");

                            for(int index = 0 ; index < items.length(); index++) {

                                JSONObject jsonObj = items.getJSONObject(index);
                                Notifications model = new Notifications();
                                model.setSchool_name(jsonObj.getString("school_name"));
                                model.setSchool_image(jsonObj.getString("school_image"));
                                model.setSchool_address(jsonObj.getString("school_address"));
                                // model.setNotification_date(jsonObj.getString("notification_date"));
                                model.setNotification_detail(jsonObj.getString("notification_detail"));
                                model.setNotification_subject(jsonObj.getString("notification_subject"));

                                DateTimeFormatter formatter = DateTimeFormat.forPattern("yy/MM/dd HH:mm:ss");
                                DateTime startTime = formatter.parseDateTime((jsonObj.getString("notification_date").replace("-", "/")));

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

            Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoSlab-Light.ttf");
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
            Picasso.with(getActivity())
                    .load(mDataList.get(i).getSchool_image())
                    .placeholder(drawable)
                    .error(drawable)
                    .into(holder.schoolImage);



            mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    Toast.makeText(getActivity(), mDataList.get(position).school_name, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), NotificationActivity.class);


                    intent.putExtra("dateSent",mDataList.get(position).getNotification_date());
                    intent.putExtra("subject",mDataList.get(position).getNotification_subject());
                    intent.putExtra("detail",mDataList.get(position).getNotification_detail());
                    intent.putExtra("schoolName",mDataList.get(position).getSchool_name());
                    intent.putExtra("schoolImage",mDataList.get(position).getSchool_image());

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
