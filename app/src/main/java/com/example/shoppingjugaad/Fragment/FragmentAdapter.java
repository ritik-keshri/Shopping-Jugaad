package com.example.shoppingjugaad.Fragment;

import android.app.ActionBar;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shoppingjugaad.Fragment.SpeakerFragment;
import com.example.shoppingjugaad.R;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    public FragmentAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new ClothFragment();
        else if (position == 1)
            return new WatchFragment();
        else if (position == 2)
            return new ShoesFragment();
        else
            return new SpeakerFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return context.getString(R.string.cloth_category);
        else if (position == 1)
            return context.getString(R.string.watch_category);
        else if (position == 2)
            return context.getString(R.string.shoes_category);
        else
            return context.getString(R.string.speaker_category);
    }

}
