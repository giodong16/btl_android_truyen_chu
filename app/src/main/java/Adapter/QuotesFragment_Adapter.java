package Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.trang_chu.Past_01_QuotesFragment;

public class QuotesFragment_Adapter extends FragmentStatePagerAdapter {


    public QuotesFragment_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new Past_01_QuotesFragment(0);
        else if(position==1)
            return new Past_01_QuotesFragment(1);
        else if(position==2)
            return new Past_01_QuotesFragment(2);
        else if(position==3)
            return new Past_01_QuotesFragment(3);
        return new Past_01_QuotesFragment(0);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="Part 01";
                break;
            case 1:
                title="Part 02";
                break;
            case 2:
                title="Part 03";
                break;
            case 3:
                title="Part 04";
                break;
        }
        return title;
    }
}
