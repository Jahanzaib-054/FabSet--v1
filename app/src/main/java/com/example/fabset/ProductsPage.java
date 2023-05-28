package com.example.fabset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProductsPage extends AppCompatActivity implements RecyclerViewClickListener {
    private RecyclerView productrecyclerView;
    private ProductAdapter productadapter;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_page);
        SQLite s1 = new SQLite(this);
        productrecyclerView = findViewById(R.id.ProductrecyclerView);
        productrecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        List<Product> productList = createProductList(s1);
        productadapter = new ProductAdapter(productList);
        productadapter.setClickListener(this);
        productrecyclerView.setAdapter(productadapter);

    }

    private List<Product> createProductList(SQLite s1) {
        String category = getIntent().getStringExtra("category");
        String subcategory = getIntent().getStringExtra("subcategory");
        List<Product> productList = s1.SearchCatProducts(category, subcategory);
        //BitmapFactory.decodeResource(getResources(), R.drawable.my_image)
        // Add your product data here
        return productList;
    }

    @Override
    public void onClick(View view, ImageView imageView, TextView titleTextView, TextView priceTextView, int position) {
        String text = titleTextView.getText().toString();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, ImageView imageView, TextView titleTextView, TextView priceTextView, int position);
    }

    }