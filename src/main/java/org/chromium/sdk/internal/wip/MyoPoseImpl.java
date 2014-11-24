package org.chromium.sdk.internal.wip;

import org.chromium.sdk.RelayOk;
import org.chromium.sdk.wip.PermanentRemoteValueMapping;

import java.util.HashMap;

/**
 * Created by steve on 24/11/2014.
 */
public class MyoPoseImpl implements IMyoPose {


    private PermanentRemoteValueMapping prm;

    public MyoPoseImpl(PermanentRemoteValueMapping prm){
     this.prm = prm;
    }

    @Override
    public void onFist() {

        prm.getEvaluateContext().evaluateAsync("alert('hello');",new HashMap<String, String>(),null,null);

    }

    @Override
    public void onFingersSpread() {

        prm.getEvaluateContext().evaluateAsync("onPose('fingersspread');",new HashMap<String, String>(),null,null);

    }

    @Override
    public void onWaveIn() {

        prm.getEvaluateContext().evaluateAsync("onPose('wavein');",new HashMap<String, String>(),null,null);
    }

    @Override
    public void onWaveOut() {

        prm.getEvaluateContext().evaluateAsync("onPose('waveout');",new HashMap<String, String>(),null,null);
    }

    @Override
    public void onThumbToPinky() {
        prm.getEvaluateContext().evaluateAsync("onPose('thumbTopinky');",new HashMap<String, String>(),null,null);
    }
}
