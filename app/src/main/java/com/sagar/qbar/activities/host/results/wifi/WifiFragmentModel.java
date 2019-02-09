package com.sagar.qbar.activities.host.results.wifi;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.WifiParsedResult;
import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 04-Feb-19. at 20:24
 */
public class WifiFragmentModel extends BaseObservable {


    private String displayResult;
    private WifiParsedResult wifiParsedResult;

    @Bindable
    public String getDisplayResult() {
        return displayResult;
    }

    private void prepareDisplayResult() {

        StringBuilder stringBuilder = new StringBuilder();

        String networkEncryption = wifiParsedResult.getNetworkEncryption();
        String password = wifiParsedResult.getPassword();

        boolean empty = networkEncryption.equalsIgnoreCase("");
        boolean nopass = networkEncryption.equalsIgnoreCase("nopass");

        stringBuilder.append("SSID: ")
                .append(wifiParsedResult.getSsid());

        if (empty || nopass) {
            stringBuilder.append("\n").append("TYPE: ")
                    .append("OPEN");
        } else {
            stringBuilder.append("\n").append("TYPE: ")
                    .append(networkEncryption);
        }

        if (!(nopass || password.equalsIgnoreCase(""))) {
            stringBuilder.append("\n").append("PASSWORD: ").
                    append(password);
        }

        if (wifiParsedResult.isHidden()) {
            stringBuilder.append("\n").append("HIDDEN: YES");
        }
        displayResult = stringBuilder.toString();
        notifyPropertyChanged(BR.displayResult);
    }


    void setWifiParsedResult(WifiParsedResult wifiParsedResult) {
        this.wifiParsedResult = wifiParsedResult;
        prepareDisplayResult();
    }

    WifiParsedResult getWifiParsedResult() {
        return wifiParsedResult;
    }
}
