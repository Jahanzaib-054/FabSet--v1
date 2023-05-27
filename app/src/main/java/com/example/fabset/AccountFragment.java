package com.example.fabset;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AccountFragment extends Fragment {

    private Button To_Form;
    private Button Logout;
    private Button Edit_profile;
    private LinearLayout user_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        To_Form = view.findViewById(R.id.to_form);
        user_layout = view.findViewById(R.id.user_account);
        Logout = view.findViewById(R.id.Logout);
        Edit_profile = view.findViewById(R.id.edit_prof);
        TextView greet = view.findViewById(R.id.greeting);
        TextView email = view.findViewById(R.id.email);

        SharedPreferences SP;
        SP = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        user_layout.setVisibility(View.GONE);

        greet.append(SP.getString("Name", "")+" !!!");
        email.setText(SP.getString("Email",""));

        Boolean Flag = SP.getBoolean("isLogin", false);
        if (Flag){
            To_Form.setVisibility(View.GONE);
            user_layout.setVisibility(View.VISIBLE);
        }
        else {
            To_Form.setVisibility(View.VISIBLE);
            user_layout.setVisibility(View.GONE);
        }

        To_Form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Form.class);
                startActivity(intent);
            }
        });
        Edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Edit_Profile.class);
                startActivity(intent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                editor.putBoolean("isLogin", false);
                editor.commit();
                Toast.makeText(getActivity(), "Logout Successfull", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        return view;
    }
}