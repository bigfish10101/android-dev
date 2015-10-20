package com.bigfish.myfirstapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Activity;

public class FirstFragment extends Fragment {

    private MainFragmentActivity mainFragmentActivity;

    OnFirstFragmentListener mCallback;

    public interface OnFirstFragmentListener {
        public void onReplaceSecondFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment1, container, false);

        Button btn = (Button)v.findViewById(R.id.fragment_btn_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallback.onReplaceSecondFragment();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

            mCallback = (OnFirstFragmentListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "class not implement....");
        }
    }

    public void setFragmentParentActivity(MainFragmentActivity main) {

        mainFragmentActivity = main;
    }
}
