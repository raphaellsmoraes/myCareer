package com.rm.mycareer.utils.command;


public class CommandInfo implements Comparable<CommandInfo> {
    int mPriority;
    int mId;
    Command mCommand;

    @Override
    public int compareTo(CommandInfo p) {
        if (mPriority < p.mPriority) {
            return -1;
        } else if (mPriority > p.mPriority) {
            return 1;
        } else if (mId > p.mId) {
            return -1;
        } else if (mId < p.mId) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof CommandInfo) {
            return compareTo((CommandInfo) o) == 0;
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        String value = mPriority + "_" + mId;
        return value.hashCode();
    }
}
