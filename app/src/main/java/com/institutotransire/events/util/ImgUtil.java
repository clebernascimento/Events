package com.institutotransire.events.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.institutotransire.events.BuildConfig;
import com.institutotransire.events.R;
import com.squareup.picasso.Picasso;


public class ImgUtil {
    public static void requestImg(Context context, ImageView mImage, String imagePath) {
        GlideUrl url = new GlideUrl(imagePath);
        Glide.with(context).
                asDrawable().
                load(url).
                timeout(8000).
                centerCrop()
                .apply(new RequestOptions().override(600, 400))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mImage.setImageDrawable(resource);
                    }
                });
    }

    public static void requestImgWeb(Context context, ImageView mImage, String imagePath) {
        Picasso.get()
                .load(imagePath)
                .resize(600, 500)
                .error(R.drawable.imgdefault)
                .centerCrop()
                .into(mImage);
    }
}
