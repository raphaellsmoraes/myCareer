package com.rm.mycareer.utils;

import com.rm.mycareer.utils.command.AsyncCommand;

import org.json.JSONException;

/**
 * Callback for activities that use AsyncTasks
 */

public interface AsyncTaskCompleteListener extends AsyncCommand.CommandListener{
    public void onTaskComplete(String result, int statusCode, int requestCode) throws JSONException;
}
