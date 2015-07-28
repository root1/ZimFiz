package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.SchoolsListAdapter;
import utils.Constants;

/**
 * Created by neokree on 24/11/14.
 */
public class AvailableSchools extends Fragment {


    ProgressBar mProgress;

    ListView mListView;
    EfficientAdapter mAdapter;
    ProgressDialog mProgressD;
    EfficientAdapter mContext;
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
    }

    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_internet_lists, container, false);
        return rootView;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        mListView = (ListView) getView().findViewById(R.id.ListView01);
        mProgress = (ProgressBar) getView().findViewById(R.id.progressBar);
        mProgressD = new ProgressDialog(getActivity());
        mProgressD.setMessage("Please wait...");
        mDataList = new ArrayList<Schools>();
        mAdapter = new EfficientAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);

        id = sharedPreferences.getString("id", Constants.NOTAVAILABLE);



//        Cache cache = AppController.getInstance().getRequestQueue().getCache();
//        Cache.Entry entry = cache.get(Constants.GETALLSCHOOLS);
//        Calendar calendar = Calendar.getInstance();
//        long serverDate = AppController.getInstance().getRequestQueue().getCache().get(Constants.GETALLSCHOOLS).serverDate;
//        if(getMinutesDifference(serverDate, calendar.getTimeInMillis()) >=30){
//            AppController.getInstance().getRequestQueue().getCache().invalidate(Constants.GETALLSCHOOLS, true);
//        }
//        if (entry != null) {
//            //Cache data available.
//            try {
//                String data = new String(entry.data, "UTF-8");
//                Log.d("CACHE DATA", data);
//                JSONArray jsonArray = new JSONArray(data);
//                setData(jsonArray, true);
//
//                //Toast.makeText(getActivity(), "Loading from cache.", Toast.LENGTH_SHORT).show();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
//
//        }
        makeSampleHttpRequest();

    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
        if (mProgress != null)
            mProgress.setVisibility(View.GONE);
    }
    public static long getMinutesDifference(long timeStart,long timeStop){
        long diff = timeStop - timeStart;
        long diffMinutes = diff / (60 * 1000);

        return  diffMinutes;
    }
    private void makeSampleHttpRequest() {
        try {
            showProgress();
            JsonArrayRequest movieReq = new JsonArrayRequest(Constants.GETALLSCHOOLS,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            setData(response, false);
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {

                    } else if (error instanceof ServerError) {
                    } else if (error instanceof AuthFailureError) {
                    } else if (error instanceof ParseError) {
                    } else if (error instanceof NoConnectionError) {
                    } else if (error instanceof TimeoutError) {
                    }

                    stopProgress();

                }
            });
            AppController.getInstance().addToRequestQueue(movieReq);
            //mVolleyQueue.add(movieReq);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setData(JSONArray response, Boolean isCache) {
        Log.d(TAG, response.toString());
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObj = response.getJSONObject(i);
                Schools model = new Schools();
                model.setSchool_name(jsonObj.getString("school_name"));
                model.setSchool_image(jsonObj.getString("school_image"));
                model.setSchool_address(jsonObj.getString("school_address"));
                model.setSchool_id(jsonObj.getString("school_id"));


                mDataList.add(model);

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        mAdapter.notifyDataSetChanged();
        if (!isCache) {
            stopProgress();
        }
    }

    private void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    private void stopProgress() {
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


            holder.schoolName.setText(mDataList.get(i).getSchool_name());
            holder.schoolAddress.setText(mDataList.get(i).getSchool_address());
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
                                        final int pos, long arg3) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle(Html.fromHtml("<font color='#2196f3'>Subscribe</font>"))

                            .setCancelable(false)
                            .setMessage(Html.fromHtml("<font color='#2196f3'>Receive notifications from this school</font>"))
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mProgressD.show();
                                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, Constants.ADDSUBSCRIPTION,
                                            registerAccountrSuccessListener(),
                                            registerAccountErrorListener()) {
                                        protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                                            Map<String, String> params = new HashMap<>();
                                            params.put("user_id", id);
                                            params.put("school_id", mDataList.get(pos).getSchool_id());


                                            return params;

                                        }
                                    };
                                    // mVolleyQueue.add(jsonObjRequest);
                                    AppController.getInstance().addToRequestQueue(jsonObjRequest);

                                }

                            })
                            .show();

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
    private Response.ErrorListener registerAccountErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (mProgressD.isShowing())
                    mProgressD.dismiss();
            }
        };
    }
    private Response.Listener<String> registerAccountrSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (mProgressD.isShowing())
                    mProgressD.dismiss();

                try {
                    JSONObject responses =new JSONObject(response) ;
                    String status = responses.getString("success");
                    String message = responses.getString("message");
                    new android.app.AlertDialog.Builder(getActivity())
                            .setTitle(Html.fromHtml("<font color='#2196f3'>"+status+"</font>"))
                            .setCancelable(false)
                            .setMessage(Html.fromHtml("<font color='#2196f3'>"+message+"</font>"))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which ) {

                                    //startActivity(new Intent(getApplicationContext(), Login.class));

                                }

                            })
                            .show();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
