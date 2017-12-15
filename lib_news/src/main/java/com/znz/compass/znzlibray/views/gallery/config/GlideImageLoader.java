package com.znz.compass.znzlibray.views.gallery.config;

import android.app.Activity;
import android.content.Context;

import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.views.gallery.inter.ImageLoader;
import com.znz.compass.znzlibray.views.gallery.widget.GalleryImageView;
import com.znz.compass.znzlibray.views.imageloder.GlideApp;


/**
 * GlideImageLoader
 * Created by Yancy on 2016/10/28.
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
        GlideApp.with(context)
                .load(path)
                .placeholder(R.mipmap.gallery_pick_photo)
                .centerCrop()
                .into(galleryImageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}