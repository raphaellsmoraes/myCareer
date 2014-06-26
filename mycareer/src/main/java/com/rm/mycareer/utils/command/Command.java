/*
 * Copyright (C) 2012 Sony Mobile Communications AB.
 * All rights, including trade secret rights, reserved.
 */

package com.rm.mycareer.utils.command;

import android.content.Context;

/**
 * Encapsulates an action for later execution, often from a different context.
 * The Command class provides a layer of separation between the code specifying
 * some operation and the code invoking that operation. This separation aids in
 * creating reusable code.
 */
public class Command {

    /**
     * Causes the Command to perform its encapsulated operation.
     */
    protected void execute(Context context) {
    }

    /**
     * Causes the Command to cancel the operation if possible.
     */
    protected void cancel() {
    }
}
