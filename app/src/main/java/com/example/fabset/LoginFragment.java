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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        EditText Username = view.findViewById(R.id.username);
        EditText Password = view.findViewById(R.id.password);
        Button Submit = view.findViewById(R.id.submit);
        SQLite s1 = new SQLite(getActivity());
        Intent intent = new Intent(getActivity(),Form.class);
        SharedPreferences SP;
        SP = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Username.getText().toString();
                String pass = Password.getText().toString();
                User userData = s1.SearchUser(user, pass);
                if(!user.isEmpty() && !pass.isEmpty())
                {
                    if(user.contains(userData.getName()) && pass.contains(userData.getPassword())){
                        Toast.makeText(getActivity(), "Logout successful", Toast.LENGTH_SHORT).show();
                        editor.putBoolean("isLogin", true);
                        editor.commit();
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getActivity(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Name or password field must not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}