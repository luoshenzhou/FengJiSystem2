package com.haozhi.machinestatu.fengjisystem.base.base_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by LSZ on 2018/1/5.
 */
public abstract class BaseFragment extends Fragment {

    public String TAG=this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), null);
        ButterKnife.bind(getActivity());
        onCreateView(view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(getActivity());
    }

    public abstract int getLayout() ;

    public abstract void onCreateView(View view) ;

}
