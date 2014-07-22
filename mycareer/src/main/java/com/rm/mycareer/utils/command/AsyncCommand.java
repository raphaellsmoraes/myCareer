/*
 * Copyright (C) 2014 Sony Mobile Communications AB.
 * All rights, including trade secret rights, reserved.
 */

package com.rm.mycareer.utils.command;

import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;

import com.rm.mycareer.utils.myCareerUtils;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AsyncCommand {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;
    public String returnListener = null;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncCommand #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(10);

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    protected final Context mContext;
    private final CommandListener mListener;
    private MyAsyncTask mAsyncTask;

    public AsyncCommand(Context context, CommandListener listener) {
        mContext = context.getApplicationContext();
        mListener = listener;
    }

    public void start() {
        mAsyncTask = new MyAsyncTask();

        if(myCareerUtils.API_10){
            mAsyncTask.execute();
        }else{
            mAsyncTask.executeOnExecutor(THREAD_POOL_EXECUTOR, (Void) null);
        }
    }

    public final void startSync() {
        boolean result = false;
        if (onPreExecute()) {
            try {
                result = execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        onPostExecute(result);
        if (mListener != null) {
            mListener.onCommandFinished(result);
        }
    }

    public final boolean isRunning() {
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

    protected Context getContext() {
        return mContext;
    }

    protected boolean onPreExecute() {
        return true;
    }

    protected boolean execute() throws IOException {
        return true;
    }

    protected void onPostExecute(boolean result) {
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            if (AsyncCommand.this.onPreExecute()) {
                try {
                    return AsyncCommand.this.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            AsyncCommand.this.onPostExecute(result);
            if (mListener != null) {
                mListener.onCommandFinished(result);
            }
        }
    }

    public interface CommandListener {
        void onCommandFinished(boolean result);
    }

    public void cancel(boolean mayInterruptIfRunning) {
        if(mAsyncTask != null) {
            mAsyncTask.cancel(mayInterruptIfRunning);
        }
    }
}
