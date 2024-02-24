package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.pjhappybank.Adapter.HomePageAdapter;
import com.example.pjhappybank.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.layout_bottom_bar);

        HomePageAdapter adapter = new HomePageAdapter(getChildFragmentManager());
        adapter.addFragment(new MainFragment());
        adapter.addFragment(new ProfileFragment());

        viewPager.setAdapter(adapter);

        // Set up ViewPager page change listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.menu_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    viewPager.setCurrentItem(0, true);
                    return true;
                case R.id.menu_profile:
                    viewPager.setCurrentItem(1, true);
                    return true;
                default:
                    return false;
            }
        });

        return view;
    }
}
