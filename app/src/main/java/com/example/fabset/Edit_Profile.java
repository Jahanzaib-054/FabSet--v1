package com.example.fabset;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.jar.Attributes;

public class Edit_Profile extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 100;
    private static final int REQUEST_CODE_PICK_IMAGE = 200;
    private Bitmap imageBitmap;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ImageButton top_back = findViewById(R.id.top_back);
        Button select_img = findViewById(R.id.selectImageButton);
        Button save_change = findViewById(R.id.submit);
        Button back = findViewById(R.id.go_back);
        ImageView prof_img = findViewById(R.id.pro_img);
        EditText Username = findViewById(R.id.username);
        EditText Email = findViewById(R.id.email);
        EditText Password = findViewById(R.id.password);
        EditText Phone = findViewById(R.id.phone);
        EditText Address = findViewById(R.id.address);

        Intent intent = new Intent(Edit_Profile.this, MainActivity.class);

        SharedPreferences SP;
        SP = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String old_user = SP.getString("Name","");
        String old_em = SP.getString("Email","");

        SQLite s1 = new SQLite(this);
        User userData = s1.SearchForEdit(old_user,old_em);

        Username.setText(userData.getName());
        Email.setText(userData.getEmail());
        Password.setText(userData.getPassword());
        Phone.setText(userData.getPhone());
        Address.setText(userData.getAddress());

        if(userData.getImage()==null){
            prof_img.setImageResource(R.drawable.default_avatar);
        }
        else {
            prof_img.setImageBitmap(userData.getImage());
        }

        save_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Username.getText().toString();
                String em = Email.getText().toString();
                String pass = Password.getText().toString();
                String ph = Phone.getText().toString();
                String at = Address.getText().toString();

                Boolean flag = s1.UpdateProfileData(old_user, old_em,user,em,pass,ph,at, imageBitmap);
                if (flag){
                    Toast.makeText(Edit_Profile.this, "Changes Successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Edit_Profile.this, "Error !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        top_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Flag",true);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Flag",true);
                startActivity(intent);
            }
        });

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        openGallery();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        imageBitmap = loadImageFromUri(imageUri);
                        prof_img.setImageBitmap(imageBitmap);

                        if (imageBitmap != null) {
                            Toast.makeText(this, "Image Updated", Toast.LENGTH_LONG).show();
//                            SQLite dbHelper = new SQLite(this);
//                            dbHelper.insertImage(imageBitmap);
                        }
                        else {
                            Toast.makeText(this, "Error loading image", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        select_img.setOnClickListener(v -> checkPermissionAndOpenGallery());
    }

    private void checkPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private Bitmap loadImageFromUri(Uri imageUri) {
        try {
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(imageUri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}