package com.example.tug_pc.restaurantmanagermini.fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tug_pc.restaurantmanagermini.R;
import com.example.tug_pc.restaurantmanagermini.ultil.BlurBuilder;

public class RegisterFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup scroll_register = getActivity().findViewById(R.id.scroll_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BlurBuilder.setBackground(getContext(), scroll_register, R.drawable.bg_register_res);
        }else {
            BlurBuilder.setBackground(getActivity(), scroll_register, R.drawable.bg_register_res);
        }
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
}
