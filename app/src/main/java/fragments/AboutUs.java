package fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.zimfiz.zimfiz.R;

public class AboutUs extends Fragment {
    Button mCall, mEmail, mFeedBack;
    TextView mWebsite;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.about, container, false);


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        mCall = (Button) getView().findViewById(R.id.call_us);
        mEmail = (Button) getView().findViewById(R.id.email_us);
        mFeedBack = (Button) getView().findViewById(R.id.feedback);
        mWebsite = (TextView) getView().findViewById(R.id.website);

        mFeedBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), Feedback.class);
//                startActivity(i);

            }
        });

        mCall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), Contact.class));

            }
        });
        mEmail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Charges.class));

            }
        });

        mWebsite.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.zimfiz.co.zw");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }
        });
    }


}
