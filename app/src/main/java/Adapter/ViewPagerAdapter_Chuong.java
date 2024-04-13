package Adapter;

import android.os.Trace;
import android.util.Log;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.trang_chu.ChuongFragment;

import java.util.ArrayList;
import java.util.List;

import Model.Chuong;
import SQL.TruyenDAO;

public class ViewPagerAdapter_Chuong extends FragmentStateAdapter {
    List<Chuong> chuongList;
    private SeekBar seekBar;
    private List<Fragment> createdFragments = new ArrayList<>();


    public ViewPagerAdapter_Chuong(@NonNull FragmentActivity fragmentActivity,List<Chuong> chuongList ,SeekBar seekBar) {
        super(fragmentActivity);
        this.chuongList=chuongList;
        this.seekBar=seekBar;
    }
    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ChuongFragment fragment = new ChuongFragment(chuongList.get(position), seekBar);
        if (!createdFragments.contains(fragment)) {
            createdFragments.add(fragment);
        }
       // createdFragments.add(fragment);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return chuongList.size();
    }

    public void updateFontSize() {
        Log.e("123",createdFragments.size()+"123");
        for (Fragment fragment : createdFragments) {
            if (fragment instanceof ChuongFragment) {
                ((ChuongFragment) fragment).updateFontSize();

            }
        }
    }
    public void updateFontSizeNotCurrent(int currentFragment) {
        for (int i = 0; i < createdFragments.size(); i++) {
            Fragment fragment = createdFragments.get(i);
            if (i != currentFragment && fragment instanceof ChuongFragment) {
                ((ChuongFragment) fragment).updateFontSize();
            }
        }
    }

}
