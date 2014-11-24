package com.github.weaselworks.web.drivers;

import org.chromium.debug.core.model.ConnectionLoggerImpl;
import org.chromium.debug.core.model.DebugTargetImpl;
import org.chromium.sdk.ConnectionLogger;
import org.chromium.sdk.DebugEventListener;
import org.chromium.sdk.JavascriptVm;
import org.chromium.sdk.TabDebugEventListener;
import org.chromium.sdk.wip.*;

import java.net.InetSocketAddress;

/**
 * Created by steve on 20/11/2014.
 */
public class ChromeDriver {


    public static void main(String[] args){


        WipBrowser browser = WipBrowserFactory.INSTANCE.createBrowser(
                new InetSocketAddress("127.0.0.1", 9222), null);
        WipBackend backend;
        WipBackendFactory backendFactory = new WipBackendFactory(); // Same class name in each backend .jar
        backend = backendFactory.create();


        try{


            WipBrowserTab tab = browser.getTabs(backend).get(0).attach(null);
            JavascriptVm javascriptVm = tab.getJavascriptVm();
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }


}
