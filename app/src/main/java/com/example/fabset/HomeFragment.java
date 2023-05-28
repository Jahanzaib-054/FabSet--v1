package com.example.fabset;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

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

        SQLite s1 = new SQLite(getContext());
        List<Product> productList = createProductList(s1);
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


    private List<Product> createProductList(SQLite s1) {
        List<Product> productList = s1.SearchTagProducts("New");
        //BitmapFactory.decodeResource(getResources(), R.drawable.my_image)

        // Add your product data here

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
