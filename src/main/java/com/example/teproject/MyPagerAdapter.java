package com.example.teproject;

import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.teproject.FirstFragment;
import com.example.teproject.FourthFragment;
import com.example.teproject.R;
import com.example.teproject.SecondFragment;
import com.example.teproject.ThirdFragment;

class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return FirstFragment.newInstance(0, "Page # 1");
            case 1: // Fragment # 1 - This will show SecondFragment different title
                return SecondFragment.newInstance(1, "Page # 2");
            case 2: // Fragment # 2 - This will show Third Fragment
                return ThirdFragment.newInstance(2, "Page # 3");
            case 3:// Fragment # 3 - This will show Fourth Fragment
                return FourthFragment.newInstance(3, "Page # 4");
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
    @Override
    public int getItemPosition(Object object) {
        // this method will be called for every fragment in the ViewPager
        if (object instanceof FirstFragment) {
            return POSITION_UNCHANGED; // don't force a reload
        } else {
            // POSITION_NONE means something like: this fragment is no longer valid
            // triggering the ViewPager to re-build the instance of this fragment.
            return POSITION_NONE;
        }
    }
}