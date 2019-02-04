package com.sagar.qbar.activities.host.results.wifi;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 04-Feb-19. at 20:24
 */
public class WifiFragmentModel extends BaseObservable {

    private String SSID;
    private String networkEncryption;
    private String password;
    private boolean hidden;

    private String timestamp;
    private String displayResult;

    String getSSID() {
        return SSID;
    }

    void setSSID(String SSID) {
        this.SSID = SSID;
    }

    String getNetworkEncryption() {
        return networkEncryption;
    }

    void setNetworkEncryption(String networkEncryption) {
        this.networkEncryption = networkEncryption;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public boolean isHidden() {
        return hidden;
    }

    void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Bindable
    public String getDisplayResult() {
        return displayResult;
    }

    @Bindable
    public String getTimestamp() {
        return timestamp;
    }

    void prepareDisplayResult() {

        StringBuilder stringBuilder = new StringBuilder();

        boolean empty = networkEncryption.equalsIgnoreCase("");
        boolean nopass = networkEncryption.equalsIgnoreCase("nopass");

        stringBuilder.append("SSID: ")
                .append(SSID);

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

        if (hidden) {
            stringBuilder.append("\n").append("HIDDEN: YES");
        }
        displayResult = stringBuilder.toString();
        notifyPropertyChanged(BR.displayResult);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        notifyPropertyChanged(BR.timestamp);
    }
}
