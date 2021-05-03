package com.thewyp.daggerpractice.ui.main.photo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.thewyp.daggerpractice.R;

public class SquareImageView extends AppCompatImageView {

    private static final String TAG = "SquareImageView";

    public SquareImageView(@NonNull Context context) {
        super(context);
    }

    public SquareImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageUrl(String imageUrl) {
        setImageUrl(this, imageUrl);
    }

    @BindingAdapter(value = {"image_url"})
    public static void setImageUrl(SquareImageView imageView, String imageUrl) {
        Log.d(TAG, "setImageUrl: "+ imageUrl) ;
        Glide.with(imageView).load(imageUrl).error(R.mipmap.ic_launcher).into(imageView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
