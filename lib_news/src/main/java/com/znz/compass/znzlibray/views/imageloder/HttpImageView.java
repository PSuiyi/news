package com.znz.compass.znzlibray.views.imageloder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.znz.compass.znzlibray.R;
import com.znz.compass.znzlibray.utils.GlideCircleTransform;
import com.znz.compass.znzlibray.utils.GlideRoundTransform;
import com.znz.compass.znzlibray.utils.StringUtil;


/**
 * 网络图片加载控件
 */
public class HttpImageView extends AppCompatImageView {

    private Context context;
    private int default_image = R.mipmap.default_image_square; // 还没加载时的图片
    private int error_image = R.mipmap.default_image_square; // 加载失败的图片

    public HttpImageView(Context context) {
        super(context);
        initialize(context);
    }

    public HttpImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public HttpImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    /**
     * 初始化加载中图片和加载失败图片
     *
     * @param context
     */
    private void initialize(Context context) {
        this.context = context;
    }

    /**
     * 加载长方形图
     *
     * @param url_image
     */
    public void loadRectImage(String url_image) {
        default_image = R.mipmap.default_image_rect;
        error_image = R.mipmap.default_image_rect;
        loadHttpImage(url_image);
    }

    /**
     * 加载竖直长方形图
     *
     * @param url_image
     */
    public void loadVerImage(final String url_image) {
        default_image = R.mipmap.default_image_ver;
        error_image = R.mipmap.default_image_ver;
        loadHttpImage(url_image);
    }

    /**
     * 加载gif
     *
     * @param url_image
     */
    public void loadGifImage(final String url_image) {
        default_image = R.mipmap.default_image_ver;
        error_image = R.mipmap.default_image_ver;

        if (StringUtil.isBlank(url_image)) {
            this.setImageResource(default_image);
            this.setScaleType(ScaleType.CENTER_CROP);
        } else {
            Glide.with(context)
                    .load(url_image)
                    .apply(RequestOptions.placeholderOf(default_image))
                    .apply(RequestOptions.centerCropTransform())
                    .into(this);
        }
    }

    /**
     * 加载正方形图
     *
     * @param url_image
     */
    public void loadSquareImage(final String url_image) {
        default_image = R.mipmap.default_image_square;
        error_image = R.mipmap.default_image_square;
        loadHttpImage(url_image);
    }

    /**
     * 设置头像
     *
     * @param url_image
     */
    public void loadHeaderImage(final String url_image) {
        loadHeaderImage(url_image, true);
    }

    /**
     * 设置头像
     *
     * @param url_image
     * @param canCache
     */
    public void loadHeaderImage(final String url_image, boolean canCache) {
        default_image = R.mipmap.default_header;
        error_image = R.mipmap.default_header;

        try {
            if (StringUtil.isBlank(url_image)) {
                if (canCache) {
                    GlideApp.with(context)
                            .load(default_image)
                            .centerCrop()
                            .error(error_image)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(default_image)
                            .transform(new GlideCircleTransform(context))
                            .into(this);
                } else {
                    GlideApp.with(context)
                            .load(default_image)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .error(error_image)
                            .placeholder(default_image)
                            .transform(new GlideCircleTransform(context))
                            .into(this);
                }

            } else {
                if (canCache) {
                    GlideApp.with(context)
                            .load(url_image)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(error_image)
                            .placeholder(default_image)
                            .transform(new GlideCircleTransform(context))
                            .into(this);
                } else {
                    GlideApp.with(context)
                            .load(url_image)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .error(error_image)
                            .placeholder(default_image)
                            .transform(new GlideCircleTransform(context))
                            .into(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置圆角
     *
     * @param url_image
     */
    public void loadRoundImage(final String url_image) {
        default_image = R.mipmap.default_image_square;
        error_image = R.mipmap.default_image_square;

        try {
            if (StringUtil.isBlank(url_image)) {
                GlideApp.with(context)
                        .load(default_image)
                        .centerCrop()
                        .error(error_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(default_image)
                        .transform(new GlideRoundTransform(context, 6))
                        .into(this);
            } else {
                GlideApp.with(context)
                        .load(url_image)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(error_image)
                        .placeholder(default_image)
                        .transform(new GlideRoundTransform(context, 6))
                        .into(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置圆角
     *
     * @param url_image
     */
    public void loadRoundImage(final int url_image) {
        default_image = R.mipmap.default_image_square;
        error_image = R.mipmap.default_image_square;

        try {
            GlideApp.with(context)
                    .load(url_image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(error_image)
                    .placeholder(default_image)
                    .transform(new GlideRoundTransform(context, 6))
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置大图预览
     *
     * @param url_image
     */
    public void loadHttpImagePreview(final String url_image) {
        default_image = R.mipmap.default_image_ver;
        error_image = R.mipmap.default_image_ver;
        GlideApp.with(getContext())
                .load(url_image)
                .error(error_image)
                .placeholder(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this);
    }


    /**
     * 公共加载Url
     *
     * @param url_image
     */
    public void loadHttpImage(final String url_image) {
        if (StringUtil.isBlank(url_image)) {
            this.setImageResource(default_image);
        } else {
            GlideApp.with(context)
                    .load(url_image)
                    .thumbnail(0.1f)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .error(error_image)
                    .placeholder(default_image)
                    .into(this);
        }
    }

    /**
     * 公共加载本地资源
     *
     * @param url_image
     */
    public void loadHttpImage(final int url_image) {
        if (url_image == 0) {
            this.setImageResource(default_image);
        } else {
            GlideApp.with(context)
                    .load(url_image)
                    .thumbnail(0.1f)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .error(error_image)
                    .placeholder(default_image)
                    .into(this);
        }
    }

}
