package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.trang_chu.R;

import java.util.List;

import Model.SlideModel;

public class Adapter_Slider extends PagerAdapter {
    Context myContext;
    List<SlideModel> slideModelList;

    public Adapter_Slider(Context myContext, List<SlideModel> slideModelList) {
        this.myContext = myContext;
        this.slideModelList = slideModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View myView= LayoutInflater.from(container.getContext()).inflate(R.layout.slide_item,container,false);
        ImageView imageView=myView.findViewById(R.id.slide);
        TextView textView=myView.findViewById(R.id.title_slider);

        SlideModel model=slideModelList.get(position);
        if(model!=null){
            Glide.with(myContext).load(model.getImage()).into(imageView);
            textView.setText(model.getTitle());
        }
        container.addView(myView);
        return myView;
    }

    @Override
    public int getCount() {
        if(slideModelList!=null) return slideModelList.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }
}
