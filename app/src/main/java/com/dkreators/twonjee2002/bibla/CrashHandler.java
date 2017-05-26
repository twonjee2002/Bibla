package com.dkreators.twonjee2002.bibla;

import android.content.Intent;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by twonjee2002 on 25-May-17.
 */

public final class CrashHandler implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler handler = null;

    public CrashHandler() {
        // Uncomment this line if you want to show the default app crash message
       this.handler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable throwable) {
        // Show pretty message to user

        // Uncomment this line to show the default app crash message
       // this.handler.uncaughtException(thread, throwable);
        StringWriter stackTrace = new StringWriter();

        System.err.println(stackTrace);

    }
}