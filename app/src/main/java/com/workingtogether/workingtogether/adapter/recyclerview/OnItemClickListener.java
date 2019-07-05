package com.workingtogether.workingtogether.adapter.recyclerview;

import android.view.View;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public interface OnItemClickListener {

    void onClick(View v, int position);

    void onLongClick(View v, int position);

}