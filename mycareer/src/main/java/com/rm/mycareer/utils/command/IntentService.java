/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2012 Sony Mobile Communications AB.
 * All rights, including trade secret rights, reserved.
 */

package com.rm.mycareer.utils.command;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.rm.mycareer.utils.Log;


/**
 * IntentService is a base class for {@link android.app.Service}s that handle asynchronous
 * requests (expressed as {@link android.content.Intent}s) on demand. Clients send requests
 * through {@link android.content.Context#startService(android.content.Intent)} calls; the
 * service is started as needed, handles each Intent in turn using a worker
 * thread, and stops itself when it runs out of work.
 * <p>
 * This "work queue processor" pattern is commonly used to offload tasks from an
 * application's main thread. The IntentService class exists to simplify this
 * pattern and take care of the mechanics. To use it, extend IntentService and
 * implement {@link #onHandleIntent(android.content.Intent)}. IntentService will receive the
 * Intents, launch a worker thread, and stop the service as appropriate.
 * <p>
 * All requests are handled on a single worker thread -- they may take as long
 * as necessary (and will not block the application's main loop), but only one
 * request will be processed at a time. <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>
 * For a detailed discussion about how to create services, read the <a
 * href="{@docRoot}guide/topics/fundamentals/services.html">Services</a>
 * developer guide.
 * </p>
 * </div>
 * 
 * @see android.os.AsyncTask
 */
public abstract class IntentService extends Service {
    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;
    private final String mName;
    private static final int INTENT_MESSAGE = 1;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent) msg.obj);
            if (!mServiceHandler.hasMessages(INTENT_MESSAGE)) {
                onEmptyQueue();
            }
        }
    }

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     * 
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentService(String name) {
        super();
        mName = name;
    }

    @Override
    public void onCreate() {
        // It would be nice to have an option to hold a partial wakelock
        // during processing, and to have a static startService(Context, Intent)
        // method that would launch the service & hand off a wakelock.

        super.onCreate();

        if (Log.ENABLED) {
            Log.get().method();
            Log.get().d(mName);
        }

        HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        if (intent != null) {
            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = startId;
            msg.obj = intent;
            msg.what = INTENT_MESSAGE;
            mServiceHandler.sendMessage(msg);
        } else {
            stopSelf();
        }
    }

    /**
     * You should not override this method for your IntentService. Instead,
     * override {@link #onHandleIntent}, which the system calls when the
     * IntentService receives a start request.
     * 
     * @see android.app.Service#onStartCommand
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (Log.ENABLED) {
            Log.get().method();
        }
        mServiceLooper.quit();
    }

    /**
     * Unless you provide binding for your service, you don't need to implement
     * this method, because the default implementation returns null.
     * 
     * @see android.app.Service#onBind
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic. So,
     * if this code takes a long time, it will hold up other requests to the
     * same IntentService, but it will not hold up anything else. When all
     * requests have been handled, the IntentService stops itself, so you should
     * not call {@link #stopSelf}.
     * 
     * @param intent The value passed to
     *            {@link android.content.Context#startService(android.content.Intent)}.
     */
    protected abstract void onHandleIntent(Intent intent);

    /**
     * This method notifies when the message queue is empty. You can use it to
     * call {@link #stopSelf} and stop the {@link com.rm.mycareer.utils.command.IntentService}
     */
    protected void onEmptyQueue() {
    }

}
