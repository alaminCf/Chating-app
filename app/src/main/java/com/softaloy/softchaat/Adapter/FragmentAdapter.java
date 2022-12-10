package com.softaloy.softchaat.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.softaloy.softchaat.Fragments.Calls_Fragment;
import com.softaloy.softchaat.Fragments.Chat_Fragment;
import com.softaloy.softchaat.Fragments.Status_Fragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0: return new Chat_Fragment();
            case 1: return new Status_Fragment();
            case 2: return new Calls_Fragment();
            default: return new Chat_Fragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position==0){
            title = "Chats";

        }

        if (position==1){
            title = "Status";

        }
        if (position==2){
            title = "Calls";

        }

        return title;
    }
}
