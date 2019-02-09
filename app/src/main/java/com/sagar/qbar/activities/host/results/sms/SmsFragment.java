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
import com.sagar.qbar.activities.host.results.ResultCommonModel;
import com.sagar.qbar.databinding.FragmentSmsBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsFragment extends Fragment implements SmsFragmentEventHandler {


    @Inject
    SmsFragmentViewModelFactory viewModelFactory;

    private SmsFragmentModel model;
    private ResultCommonModel commonModel;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        long id = arguments != null ? arguments.getLong("id", 0) : 0;

        if (id == 0) {
            throw new IllegalStateException("id should be passed");
        }

        viewModelFactory.setId(id);
        SmsFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(SmsFragmentViewModel.class);

        model = viewModel.getSmsFragmentModel();
        commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSmsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sms, container, false);

        binding.setModel(model);
        binding.setHandler(this);
        binding.setCommonModel(commonModel);

        return binding.getRoot();
    }

    private void onResponse(StorableResult storableResult) {
        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());

        SMSParsedResult parsedResult = (SMSParsedResult) ResultParser.parseResult(result);

        commonModel.setTimestamp(storableResult.getTimestamp());
        commonModel.setType(storableResult.getParsedResultType());

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
