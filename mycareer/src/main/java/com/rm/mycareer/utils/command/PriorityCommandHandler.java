/*
 * Copyright (C) 2012 Sony Mobile Communications AB.
 * All rights, including trade secret rights, reserved.
 */

package com.rm.mycareer.utils.command;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.SparseArray;

import java.util.PriorityQueue;

/**
 * CommandHandler is just a handle to a command queue. Commands are added to the
 * queue by calling #start method. Also, Commands can be removed from queue by
 * calling #cancel method.
 */
public final class PriorityCommandHandler implements Handler.Callback {

    private static final int COMMAND = 1;
    private static final int RELEASE = 2;

    private int mId = 1;

    private final SparseArray<CommandInfo> mCommandMap;
    private final PriorityQueue<CommandInfo> mQueue;
    private final Handler mHandler;
    /**
     * Context reference.
     */
    private final Context mContext;

    /**
     * Creates a new Command Handler queue.
     * 
     * @param name name of Handler task.
     */
    public PriorityCommandHandler(Context context, String name) {
        HandlerThread thread = new HandlerThread(name);
        thread.start();

        mContext = context;
        mQueue = new PriorityQueue<CommandInfo>();
        mCommandMap = new SparseArray<CommandInfo>();
        mHandler = new Handler(thread.getLooper(), this);
    }

    /**
     * Add a #Command to queue.
     * 
     * @param command Command to be executed.
     */
    public int start(Command command, int priority) {
        CommandInfo info = createCommandInfo(command, priority);
        mCommandMap.put(info.mId, info);

        Message msg = mHandler.obtainMessage(COMMAND, info);
        mHandler.sendMessage(msg);

        return info.mId;
    }

    /**
     * Add a #Command to queue.
     * 
     * @param command Command to be executed.
     */
    public int start(Command command, int priority, long delay) {
        CommandInfo info = createCommandInfo(command, priority);
        mCommandMap.put(info.mId, info);

        Message msg = mHandler.obtainMessage(COMMAND, info);
        mHandler.sendMessageDelayed(msg, delay);

        return info.mId;
    }

    /**
     * Cancel a #Command if possible. If the Command still on queue, the Command
     * is removed.
     * 
     * @param command #Command to cancel.
     */
    public void cancel(int id) {

        CommandInfo info = mCommandMap.get(id);
        if (info != null) {
            info.mCommand.cancel();
            mCommandMap.remove(id);
            mHandler.removeMessages(COMMAND, info);
            mQueue.remove(info);
        }
    }

    /**
     * Request the release the #CommandHandler. No more Commands can be added to
     * this handler.
     */
    public void release() {
        mHandler.removeMessages(COMMAND);
        mHandler.sendEmptyMessage(RELEASE);
    }

    private CommandInfo createCommandInfo(Command command, int priority) {
        CommandInfo info = new CommandInfo();
        info.mCommand = command;
        info.mPriority = priority;
        info.mId = mId++;

        return info;
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
     */
    protected void onCommand(Object obj) {
        if (obj instanceof CommandInfo) {
            CommandInfo info = (CommandInfo) obj;
            mQueue.add(info);
        }

        CommandInfo commandInfo = mQueue.poll();
        while (commandInfo != null) {
            commandInfo.mCommand.execute(mContext);
            mCommandMap.remove(commandInfo.mId);
            commandInfo = mQueue.poll();
        }
    }

    /**
     * Release the CommandHandler.
     * 
     * @param mCommand command to be executed.
     */
    private void onRelease() {
        mHandler.removeMessages(COMMAND);
        mHandler.getLooper().quit();
    }
}
