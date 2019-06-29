package com.workingtogether.workingtogether.adapter.recyclerview;

import android.view.View;

public interface OnItemClickListenerInterface {

    void onClick(View v, int position);

    void onLongClick(View v, int position);

}