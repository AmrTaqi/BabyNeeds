package com.ammt.babyneeds.callbacks;

import android.view.View;

public interface BabyAdapterOnClickListener {
    void onClickUpdate(int position, View view);
    void onClickRemove(int position, View view);
}
