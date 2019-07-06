package com.workingtogether.android.adapter.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.workingtogether.android.R;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class SignupPagerAdapter extends PagerAdapter {
    private Context mContext;
    private int[] slide_layouts = {
            R.layout.activity_signup_step_one,
            R.layout.activity_signup_step_two,
            R.layout.activity_signup_step_three
    };

    public SignupPagerAdapter(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    @Override
    public int getCount() {
        return slide_layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_signup_slide, container, false);

        ViewStub stub = view.findViewById(R.id.layout_loader);
        stub.setLayoutResource(slide_layouts[position]);
        View inflated = stub.inflate();

        // if View has children elements then delete them first
        if (inflated.getParent() != null)
            ((ViewGroup) inflated.getParent()).removeView(inflated);

        container.addView(inflated);

        return inflated;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
