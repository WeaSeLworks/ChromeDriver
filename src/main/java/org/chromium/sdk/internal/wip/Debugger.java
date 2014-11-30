package org.chromium.sdk.internal.wip;

import org.chromium.sdk.*;
import org.chromium.sdk.wip.*;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Debugger implements IMyoPose{

    private WipBrowser browser;
    private WipBackend backend;
    private JavascriptVm javascriptVm;
    private TabListener listener;
    private DebugListener debugListener;
    private IMyoPose mp;

    public Debugger(String host,int port){

       try
       {
           System.err.println("started");
           this.connect(host, port);
           System.err.println("finished");
       }catch(Exception e){
           e.printStackTrace();
       }

    }

    private void connect(String host, int port) throws Exception {
        browser = WipBrowserFactory.INSTANCE.createBrowser(
                new InetSocketAddress(host, port), new LoggerFactoryImpl());
        WipBackendFactory backendFactory = new WipBackendFactory(); // Same class name in each backend .jar 
        backend = backendFactory.create();
        List<? extends WipBrowser.WipTabConnector> tabs = browser.getTabs(backend);
        listener = new TabListener();
        debugListener = listener.getDebugEventListenerImpl();
        WipBrowserTab tab = tabs.get(0).attach(listener);
        javascriptVm = tab.getJavascriptVm();

        WipTabImpl wti = (WipTabImpl) javascriptVm;


        String valueMappingGroup = UUID.randomUUID().toString();

        PermanentRemoteValueMapping prm = ((WipTabImpl) javascriptVm).createPermanentValueMapping(valueMappingGroup);

        mp = new MyoPoseImpl(prm);


       //debugListener.getSemaphore().tryAcquire(999, TimeUnit.DAYS);


    }



    @Override
    public void onFist() {
        mp.onFist();
    }

    @Override
    public void onFingersSpread() {
    mp.onFingersSpread();
    }

    @Override
    public void onWaveIn() {
    mp.onWaveIn();
    }

    @Override
    public void onWaveOut() {
    mp.onWaveOut();
    }

    @Override
    public void onThumbToPinky() {
    mp.onThumbToPinky();
    }

    private class TabListener implements TabDebugEventListener {

        private DebugListener debugEvenListener;

        public TabListener() {
            this.debugEvenListener = new DebugListener();
        }

        @Override
        public DebugEventListener getDebugEventListener() {
            return debugEvenListener;
        }

        public DebugListener getDebugEventListenerImpl() {
            return debugEvenListener;
        }

        @Override
        public void navigated(String string) {
            System.err.println("navigated="+string);
        }

        @Override
        public void closed() {
            System.err.println("tab closed");
        }
    }

    private class DebugListener implements DebugEventListener {

        private final Semaphore semaphore = new Semaphore(0);

        @Override
        public void suspended(DebugContext debugContext) {
            semaphore.release();
        }

        public Semaphore getSemaphore() {
            return semaphore;
        }

        @Override
        public void scriptLoaded(Script script) {
            System.err.println("------- script: "+script.getName());
        }

        @Override
        public void resumed() {
        }

        @Override
        public void disconnected() {
            semaphore.release();
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

    private class LoggerFactoryImpl implements WipBrowserFactory.LoggerFactory {

        @Override
        public ConnectionLogger newBrowserConnectionLogger() {
            return new ConnectionLoggerImpl();
        }

        @Override
        public ConnectionLogger newTabConnectionLogger() {
            return new ConnectionLoggerImpl();
        }

    }

    private class ConnectionLoggerImpl implements ConnectionLogger {

        @Override
        public StreamListener getIncomingStreamListener() {
            return new StreamListenerImpl();
        }

        @Override
        public StreamListener getOutgoingStreamListener() {
            return new StreamListenerImpl();
        }

        @Override
        public void setConnectionCloser(ConnectionCloser connectionCloser) {
        }

        @Override
        public void start() {
        }

        @Override
        public void handleEos() {
        }

    }

    private class StreamListenerImpl implements ConnectionLogger.StreamListener {

        @Override
        public void addContent(CharSequence text) {
            //    System.err.println(""+text);
        }

        @Override
        public void addSeparator() {
//            System.err.println("   ------ ");
        }

    }
}
