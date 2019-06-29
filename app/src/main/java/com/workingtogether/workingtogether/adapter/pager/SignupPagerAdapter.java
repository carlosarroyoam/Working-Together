package com.workingtogether.workingtogether.adapter.pager;

import android.content.Context;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.workingtogether.workingtogether.R;

public class SignupPagerAdapter extends PagerAdapter {
    private Context context;
    private int[] slide_layouts = {
            R.layout.activity_signup_step_one,
            R.layout.activity_signup_step_two,
            R.layout.activity_signup_step_three
    };

    public SignupPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slide_layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_signup_slide, container, false);

        ViewStub stub = view.findViewById(R.id.layout_loader);
        stub.setLayoutResource(slide_layouts[position]);
        View inflated = stub.inflate();

        //Si el View tiene elementos hijos primero eliminalos
        if (inflated.getParent() != null)
            ((ViewGroup) inflated.getParent()).removeView(inflated);

        container.addView(inflated);

        return inflated;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
