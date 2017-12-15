package com.znz.compass.znzlibray.views.imageloder;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Date： 2017/12/5 2017
 * User： PSuiyi
 * Description：
 */
@GlideModule
public class ZnzGlideModule extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
