package com.oklib.utils.network;

import com.oklib.CoreApp;

import java.io.File;

import okhttp3.Cache;

/**
 */
public class HttpCache {

    public static Cache getCache() {
        return new Cache(new File(CoreApp.getAppContext().getCacheDir().getAbsolutePath() + File.separator + "data/NetCache"),
                NetWorkerConstant.HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
