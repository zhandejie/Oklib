package com.oklib.utils.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.oklib.CoreConstants;
import com.oklib.utils.network.Exception.NetworkException;
import com.oklib.utils.network.util.NetworkUtil;
import com.oklib.utils.network.util.ProgressDialogUtil;

import rx.Subscriber;

/**
 * BaseSubscriber
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;
    private ProgressDialogUtil progressDialogUtil;

    public BaseSubscriber(Context context) {
        this.context = context;
        if (CoreConstants.PROGRESS_DIALOG_DISPALY) {
            progressDialogUtil = new ProgressDialogUtil(context);
        }
    }

    @Override
    final public void onError(Throwable e) {
        Log.e("NetWorker", e.getMessage());
        progressDialogUtil.dismissProgressDialog();
        if (e instanceof MThrowable) {
            onError((MThrowable) e);
        } else {
            onError(new MThrowable(e, NetworkException.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("NetWorker", "-->http is start");
        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            onCompleted();
            return;
        }
        if (progressDialogUtil != null) {
            progressDialogUtil.showProgressDialog();
        }

    }

    @Override
    public void onCompleted() {
        Log.e("NetWorker", "-->http is Complete");
        // todo some common as  dismiss loadding
        if (progressDialogUtil != null) {
            progressDialogUtil.dismissProgressDialog();
        }
    }

    public abstract void onError(MThrowable e);


}
