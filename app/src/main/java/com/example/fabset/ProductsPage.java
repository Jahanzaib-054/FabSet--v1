package com.example.fabset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class ProductsPage extends AppCompatActivity {
    private RecyclerView productrecyclerView;
    private ProductAdapter productadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_page);
        SQLite s1 = new SQLite(this);
        productrecyclerView = findViewById(R.id.ProductrecyclerView);
        productrecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        List<Product> productList = createProductList(s1);
        productadapter = new ProductAdapter(productList);
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
}