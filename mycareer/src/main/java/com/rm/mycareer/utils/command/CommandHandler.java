/*
 * Copyright (C) 2012 Sony Mobile Communications AB.
 * All rights, including trade secret rights, reserved.
 */

package com.rm.mycareer.utils.command;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.rm.mycareer.utils.Log;


/**
 * CommandHandler is just a handle to a command queue. Commands are added to the
 * queue by calling #start method. Also, Commands can be removed from queue by
 * calling #cancel method.
 */
public final class CommandHandler implements Handler.Callback {

    private static final int COMMAND = 1;
    private static final int RELEASE = 2;

    /**
     * Context reference.
     */
    private final Context mContext;
    /**
     * Handler to process the #Command queue.
     */
    private final Handler mHandler;

    /**
     * Creates a new Command Handler queue.
     *
     * @param name name of Handler task.
     */
    public CommandHandler(Context context, String name) {
        if (Log.ENABLED)
            Log.get().method();

        HandlerThread thread = new HandlerThread(name);
        thread.start();

        mContext = context;
        mHandler = new Handler(thread.getLooper(), this);
    }

    /**
     * Add a #Command to queue.
     *
     * @param command Command to be executed.
     */
    public void start(Command command) {
        if (Log.ENABLED)
            Log.get().method();
        mHandler.obtainMessage(COMMAND, command).sendToTarget();
    }

    /**
     * Add a #Command to queue.
     *
     * @param command Command to be executed.
     */
    public void start(Command command, long delay) {
        if (Log.ENABLED)
            Log.get().method();
        Message msg = mHandler.obtainMessage(COMMAND, command);
        mHandler.sendMessageDelayed(msg, delay);
    }

    /**
     * Cancel a #Command if possible. If the Command still on queue, the Command
     * is removed.
     *
     * @param command #Command to cancel.
     */
    public void cancel(Command command) {
        if (Log.ENABLED)
            Log.get().method();
        mHandler.removeMessages(COMMAND, command);
        command.cancel();
    }

    /**
     * Cancel all #Command if possible. If the Command still on queue, the
     * Command is removed.
     */
    public void cancel() {
        if (Log.ENABLED)
            Log.get().method();
        mHandler.removeMessages(COMMAND);
    }

    /**
     * Request the release the #CommandHandler. No more Commands can be added to
     * this handler.
     */
    public void release() {
        if (Log.ENABLED)
            Log.get().method();
        mHandler.removeMessages(COMMAND);
        mHandler.sendEmptyMessage(RELEASE);
    }

    /**
     * Handle the messages.
     */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case COMMAND:
                onCommand(msg.obj);
                return true;
            case RELEASE:
                onRelease();
                return true;
        }
        return false;
    }

    /**
     * Execute the command on handler task.
     *
     * @param mCommand command to be executed.
     */
    protected void onCommand(Object object) {
        if (Log.ENABLED)
            Log.get().method();
        if (object instanceof Command) {
            Command command = (Command) object;
            command.execute(mContext);
        }
    }

    /**
     * Release the CommandHandler.
     *
     * @param mCommand command to be executed.
     */
    protected void onRelease() {
        if (Log.ENABLED)
            Log.get().method();
        mHandler.removeMessages(COMMAND);
        mHandler.getLooper().quit();
    }
}
