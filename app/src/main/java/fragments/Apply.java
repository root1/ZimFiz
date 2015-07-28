package fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.zimfiz.zimfiz.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ApplicationListAdapter;
import classes.SimpleList;
import utils.SimpleDividerItemDecoration;

/**
 * Created by neokree on 24/11/14.
 */
public class Apply extends Fragment {
    List<SimpleList> simpleListList;
    ApplicationListAdapter applicationListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.select_place, container, false);

        return rootView;

    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);


        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        simpleListList = new ArrayList<>();


        simpleListList.add(new SimpleList("Form 1 Place"));
        simpleListList.add(new SimpleList("Lower 6 Place"));
        simpleListList.add(new SimpleList("Transfer Place"));
        applicationListAdapter = new ApplicationListAdapter(getActivity(), simpleListList);
        recyclerView.setAdapter(applicationListAdapter);
    }
}
