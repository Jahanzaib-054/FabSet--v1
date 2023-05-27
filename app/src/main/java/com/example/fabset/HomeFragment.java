package com.example.fabset;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private ProductAdapter productadapter;
    EditText qry;
    HorizontalScrollView horizontalScrollView;
    HorizontalScrollView categoryScrollView;
    private int[] images = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    private CustomPagerAdapter adapter;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            int currentPosition = viewPager.getCurrentItem();
            int newPosition = (currentPosition + 1) % images.length;
            viewPager.setCurrentItem(newPosition);
            handler.postDelayed(this, 3000);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        List<Product> productList = createProductList();
        productadapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productadapter);

        viewPager = view.findViewById(R.id.view_pager);
        adapter = new CustomPagerAdapter(getContext(), images);
        viewPager.setAdapter(adapter);
        horizontalScrollView = view.findViewById(R.id.Home_cards);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        categoryScrollView = view.findViewById(R.id.Category_cards);
        categoryScrollView.setHorizontalScrollBarEnabled(false);
        qry = view.findViewById(R.id.search_bar);

        qry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });


        handler.postDelayed(runnable, 4000);

        return view;
    }

    private void performSearch() {
        String query = qry.getText().toString();
        // Perform the search operation based on the query
        // ...
    }


    private List<Product> createProductList() {
        List<Product> productList = new ArrayList<>();

        // Add your product data here
        productList.add(new Product("Product 1", "Price 1", R.drawable.shirt));
        productList.add(new Product("Product 2", "Price 2", R.drawable.shirt));
        productList.add(new Product("Product 3", "Price 3", R.drawable.shirt));
        productList.add(new Product("Product 4", "Price 4", R.drawable.shirt));
        productList.add(new Product("Product 5", "Price 5", R.drawable.shirt));
        productList.add(new Product("Product 6", "Price 6", R.drawable.shirt));
        productList.add(new Product("Product 7", "Price 7", R.drawable.shirt));
        productList.add(new Product("Product 8", "Price 8", R.drawable.shirt));
        productList.add(new Product("Product 9", "Price 9", R.drawable.shirt));
        productList.add(new Product("Product 10", "Price 10", R.drawable.shirt));
        productList.add(new Product("Product 11", "Price 11", R.drawable.shirt));
        productList.add(new Product("Product 12", "Price 12", R.drawable.shirt));

        return productList;
    }

    private static class CustomPagerAdapter extends PagerAdapter {

        private int[] images;
        private LayoutInflater inflater;

        CustomPagerAdapter(Context context, int[] images) {
            this.images = images;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.image_slider_item, container, false);
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageResource(images[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
