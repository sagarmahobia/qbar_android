package com.sagar.qbar.activities.host.results.sms;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.BaseResultFragment;
import com.sagar.qbar.databinding.FragmentSmsBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsFragment extends BaseResultFragment implements SmsFragmentEventHandler {


    @Inject
    SmsFragmentViewModelFactory viewModelFactory;

    private SmsFragmentModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModelFactory.setId(super.id);
        SmsFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(SmsFragmentViewModel.class);

        model = viewModel.getSmsFragmentModel();
        super.commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSmsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sms, container, false);

        binding.setModel(model);
        binding.setHandler(this);
        binding.setCommonModel(super.commonModel);

        return binding.getRoot();
    }

    @Override
    protected void onResponse(StorableResult storableResult) {
        super.onResponse(storableResult);

        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());
        SMSParsedResult parsedResult = (SMSParsedResult) ResultParser.parseResult(result);
        model.setSmsParsedResult(parsedResult);
    }

    @Override
    public void onClickSMS(SmsFragmentModel smsFragmentModel) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + smsFragmentModel.getNumber()));
        smsIntent.putExtra("sms_body", smsFragmentModel.getBody());
        startActivity(smsIntent);
    }


    @Override
    public void onClickShare(SmsFragmentModel smsFragmentModel) {
        Context context = this.getContext();
        if (context != null) {
            ShareTextUtil.share(context, smsFragmentModel.getDisplayResult());
        }
    }
}
