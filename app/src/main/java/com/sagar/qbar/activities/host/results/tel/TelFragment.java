package com.sagar.qbar.activities.host.results.tel;


import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.TelParsedResult;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.BaseResultFragment;
import com.sagar.qbar.databinding.FragmentTelBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class TelFragment extends BaseResultFragment implements TelFragmentEventHandler {

    private static final int MY_CALL_REQUEST_CODE = 200;
    @Inject
    TelFragmentViewModelFactory viewModelFactory;

    private TelFragmentModel model;
    private String uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModelFactory.setId(super.id);
        TelFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(TelFragmentViewModel.class);

        model = viewModel.getTelFragmentModel();
        super.commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);
        super.observeTimerResponse(viewModel);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTelBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tel, container, false);

        binding.setModel(model);
        binding.setCommonModel(super.commonModel);
        binding.setHandler(this);

        return binding.getRoot();

    }

    @Override
    protected void onResponse(StorableResult storableResult) {
        super.onResponse(storableResult);

        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());
        TelParsedResult parsedResult = (TelParsedResult) ResultParser.parseResult(result);
        model.setTelParsedResult(parsedResult);

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClickCall(TelFragmentModel telFragmentModel) {
        uri = telFragmentModel.getTelParsedResult().getTelURI();

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, MY_CALL_REQUEST_CODE);
            return;
        }
        makeACall(uri);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_CALL_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeACall(uri);
                } else {
                    Toast.makeText(this.getContext(), "Call Permission is required to make a call", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onClickSave(TelFragmentModel telFragmentModel) {
        addContact(telFragmentModel.getTelParsedResult().getNumber());
    }

    @Override
    public void onClickShare(TelFragmentModel telFragmentModel) {
        Context context = this.getContext();
        if (context != null) {
            ShareTextUtil.share(context, telFragmentModel.getTelParsedResult().getNumber());
        }
    }


    private void addContact(String phone) {

        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);

        intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);

        startActivity(intent);
    }

    @SuppressWarnings("ConstantConditions")
    @SuppressLint("MissingPermission")
    private void makeACall(String uri) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        getContext().startActivity(intent);

    }
}
