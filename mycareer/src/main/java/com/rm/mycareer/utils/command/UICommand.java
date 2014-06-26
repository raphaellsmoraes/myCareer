/*
 * Copyright (C) 2012 Sony Mobile Communications AB.
 * All rights, including trade secret rights, reserved.
 */

package com.rm.mycareer.utils.command;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;

public class UICommand {

    private Activity mContext;
    private Fragment mFragment;
    private MyAsyncTask mAsyncTask;
    private boolean mIsCancelled;

    public void start(Fragment fragment) {
        mFragment = fragment;
        start(mFragment.getActivity());
    }

    public void start(Activity context) {
        mContext = context;
        mIsCancelled = false;
        mAsyncTask = new MyAsyncTask();
        mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
    }

    protected boolean execute(Context context) {
        return true;
    }

    protected void onPostExecute(boolean result) {
    }

    protected void updateUI(boolean result) {
    }

    public void cancel() {
        mIsCancelled = true;
        if (isRunning()) {
            mAsyncTask.cancel(true);
        }
    }

    public boolean isCancelled() {
        return mIsCancelled;
    }

    public boolean isRunning() {
        boolean result = true;
        if (mAsyncTask == null) {
            result = false;
        } else {
            Status status = mAsyncTask.getStatus();
            if (mAsyncTask.isCancelled()) {
                mAsyncTask = null;
                result = false;
            } else if (status.equals(Status.FINISHED)) {
                mAsyncTask = null;
                result = false;
            }
        }
        return result;
    }

    private boolean isActive() {
        if (mFragment != null) {
            return mFragment.isResumed();
        }
        return !mContext.isFinishing();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            if (!mIsCancelled) {
                return UICommand.this.execute(mContext);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            UICommand.this.onPostExecute(result);
            if (!mIsCancelled && isActive()) {
                updateUI(result);
            }
        }
    }
}
