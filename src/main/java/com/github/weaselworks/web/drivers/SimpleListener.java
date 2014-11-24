package com.github.weaselworks.web.drivers;

import org.chromium.sdk.DebugEventListener;
import org.chromium.sdk.DebugContext;
import org.chromium.sdk.Script;

import java.util.concurrent.Semaphore;

class SimpleListener implements DebugEventListener {
    DebugContext savedDebugContext;
    final Semaphore semaphore = new Semaphore(0);
    @Override public void suspended(DebugContext debugContext) {
        savedDebugContext = debugContext;
        semaphore.release();
    }

    @Override
    public void resumed() {

    }

    @Override
    public void disconnected() {

    }

    @Override public void scriptLoaded(Script script) {
        // ignore
    }

    @Override
    public void scriptCollected(Script script) {

    }

    @Override
    public VmStatusListener getVmStatusListener() {
        return null;
    }

    @Override
    public void scriptContentChanged(Script script) {

    }

}