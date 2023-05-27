package com.example.fabset;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        EditText Username = view.findViewById(R.id.username);
        EditText Password = view.findViewById(R.id.password);
        EditText Phone = view.findViewById(R.id.phone);
        EditText Address = view.findViewById(R.id.address);
        EditText Email = view.findViewById(R.id.email);
        Button Submit = view.findViewById(R.id.submit);
        SharedPreferences SP;
        SP = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        SQLite s1 = new SQLite(getActivity());

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Username.getText().toString();
                String password = Password.getText().toString();
                String phone = Phone.getText().toString();
                String address = Address.getText().toString();
                String email = Email.getText().toString();
                Intent intent = new Intent(getActivity(), Form.class);
                if(!username.isEmpty() && !password.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !email.isEmpty())
                {
                    if (!(username.length() > 3)){
                        Toast.makeText(getActivity(), "Username must be atleast 3 characters long", Toast.LENGTH_SHORT).show();
                    } else if (!(password.length() >= 6 )) {
                        Toast.makeText(getActivity(), "Password must be 6 characters long", Toast.LENGTH_SHORT).show();
                    } else if (!(phone.length() == 11)) {
                        Toast.makeText(getActivity(), "Invalid phone number", Toast.LENGTH_SHORT).show();
                    } else {
                        long r = s1.SaveSignupData(username,password,email,address,phone);
                        if (r > 0) {
                            editor.putString("Name", username);
                            editor.putString("Email", email);
                            editor.putBoolean("isLogin", true);
                            editor.commit();
                            Toast.makeText(getActivity(), "Signup Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getActivity(), "Username or Email already used", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Fields must not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}