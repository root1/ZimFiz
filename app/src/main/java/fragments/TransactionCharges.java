package fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.zimfiz.zimfiz.R;

import utils.Constants;

/**
 * Created by neokree on 24/11/14.
 */
public class TransactionCharges extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.transaction_charges, container, false);

        return rootView;

    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        final  EditText amount = (EditText)getActivity().findViewById(R.id.amount);
        final EditText charge = (EditText)getActivity().findViewById(R.id.charge);
        final EditText amountPayabale = (EditText)getActivity().findViewById(R.id.amountPayable);
        final TextView lable3 = (TextView)getActivity().findViewById(R.id.lable3);
        final TextView lable4 = (TextView)getActivity().findViewById(R.id.lable4);
        final LinearLayout chargeLayout = (LinearLayout)getActivity().findViewById(R.id.chargeLayout);
        final LinearLayout amounPayableLayout = (LinearLayout)getActivity().findViewById(R.id.amountPayableLayout);

        Button cal = (Button)getActivity().findViewById(R.id.btncontinue);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sAmount = amount.getText().toString();
                if(sAmount.equals("") || sAmount.equals(null)){
                    Toast.makeText(getActivity(), "Please enter the tuition amount", Toast.LENGTH_LONG).show();
                }else{
                    Double amountTuition = Double.parseDouble(sAmount);
                    Double amounCharge = amountTuition * Constants.CHARGE;
                    Double amounPayablex = amountTuition + amounCharge;
                    lable3.setVisibility(View.VISIBLE);
                    lable4.setVisibility(View.VISIBLE);

                    chargeLayout.setVisibility(View.VISIBLE);
                    amounPayableLayout.setVisibility(View.VISIBLE);

                    charge.setText("$ "+String.valueOf(amounCharge));
                    amountPayabale.setText("$ "+String.valueOf(amounPayablex));
                }
            }
        });
    }
}
