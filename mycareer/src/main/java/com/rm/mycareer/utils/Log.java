/*
 * Copyright (C) 2012 Sony Mobile Communications AB.
 * All rights, including trade secret rights, reserved.
 */

package com.rm.mycareer.utils;

import com.rm.mycareer.utils.log.Logger;

public class Log {

    public static final boolean ENABLED = Logger.ENABLED;
    public static final String  TAG = "Common";

    public static Logger sInstance = new Logger(TAG, ENABLED);

    public static Logger get() {
        return sInstance;
    }
}
