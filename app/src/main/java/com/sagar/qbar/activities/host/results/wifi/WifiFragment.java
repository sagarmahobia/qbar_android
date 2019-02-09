package com.sagar.qbar.activities.host.results.wifi;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.WifiParsedResult;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.BaseResultFragment;
import com.sagar.qbar.databinding.FragmentWifiBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import java.util.List;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class WifiFragment extends BaseResultFragment implements WifiFragmentEventHandler {

    @Inject
    WifiManager wifiManager;

    @Inject
    WifiFragmentViewModelFactory viewModelFactory;

    private WifiFragmentModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModelFactory.setId(super.id);
        WifiFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(WifiFragmentViewModel.class);

        model = viewModel.getWifiModel();
        super.commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);

        boolean wifiEnabled = wifiManager.isWifiEnabled();
        if (!wifiEnabled) {
            wifiManager.setWifiEnabled(true);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentWifiBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wifi, container, false);

        binding.setModel(model);
        binding.setCommonModel(super.commonModel);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    protected void onResponse(StorableResult storableResult) {
        super.onResponse(storableResult);

        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());
        WifiParsedResult parsedResult = (WifiParsedResult) ResultParser.parseResult(result);
        model.setWifiParsedResult(parsedResult);
    }

    @Override
    public void onClickConnect(WifiFragmentModel wifiFragmentModel) {

        WifiParsedResult wifiParsedResult = wifiFragmentModel.getWifiParsedResult();
        String networkSSID = wrapQuote(wifiParsedResult.getSsid());
        String networkPass = wrapQuote(wifiParsedResult.getPassword());
        String networkEncryption = wifiParsedResult.getNetworkEncryption();

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = networkSSID;

        if (networkEncryption.equalsIgnoreCase("WEP")) {
            conf.wepKeys[0] = networkPass;
            conf.wepTxKeyIndex = 0;
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

        } else if (networkEncryption.equalsIgnoreCase("WPA")) {
            conf.preSharedKey = networkPass;

        } else if (networkEncryption.equalsIgnoreCase("") || networkEncryption.equalsIgnoreCase("nopass")) {
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        }

        wifiManager.addNetwork(conf);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(getContext(), "Enable wifi in the settings first", Toast.LENGTH_SHORT).show();
            return;
        }

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals(networkSSID)) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                Toast.makeText(getContext(), "Connecting to network", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClickShare(WifiFragmentModel wifiFragmentModel) {
        ShareTextUtil.share(getContext(), wifiFragmentModel.getDisplayResult());
    }

    private String wrapQuote(String string) {
        return "\"" + string + "\"";
    }
}
