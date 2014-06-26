/*
 * Copyright (C) 2012 Sony Mobile Communications AB.
 * All rights, including trade secret rights, reserved.
 */

package com.rm.mycareer.utils.command;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

public abstract class IntentCommand extends Command implements Parcelable {

    private static final String INTENT_EXTRA_COMMAND = "command";

    public IntentCommand() {
    }

    public void start(Context context) {
        Intent intent = createIntent(context);
        intent.putExtra(INTENT_EXTRA_COMMAND, this);
        context.startService(intent);
    }

    public Intent getIntent(Context context) {
        Intent intent = createIntent(context);
        intent.putExtra(INTENT_EXTRA_COMMAND, this);
        return intent;
    }

    public static IntentCommand getCommand(Intent intent) {
        return intent.getParcelableExtra(INTENT_EXTRA_COMMAND);
    }

    public static void executeFromIntent(Context context, Intent intent) {
        if (intent != null) {
            IntentCommand command = getCommand(intent);
            if (command != null) {
                command.execute(context);
            }
        }
    }

    protected abstract Intent createIntent(Context context);

    @Override
    public int describeContents() {
        return 0;
    }
}
