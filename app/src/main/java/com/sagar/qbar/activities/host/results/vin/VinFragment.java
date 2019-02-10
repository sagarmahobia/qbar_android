package com.sagar.qbar.activities.host.results.vin;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.VINParsedResult;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.BaseResultFragment;
import com.sagar.qbar.databinding.FragmentVinBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class VinFragment extends BaseResultFragment implements VinFragmentEventHandler {

    @Inject
    VinFragmentViewModelFactory viewModelFactory;

    private VinFragmentModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModelFactory.setId(super.id);
        VinFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(VinFragmentViewModel.class);
        model = viewModel.getVinFragmentModel();
        super.commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);
        super.observeTimerResponse(viewModel);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentVinBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vin, container, false);

        binding.setModel(model);
        binding.setCommonModel(super.commonModel);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    protected void onResponse(StorableResult storableResult) {
        super.onResponse(storableResult);

        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());
        VINParsedResult parsedResult = (VINParsedResult) ResultParser.parseResult(result);
        model.setDisplayResult(parsedResult.getDisplayResult());
    }


    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClickShare(VinFragmentModel model) {
        ShareTextUtil.share(getContext(), model.getDisplayResult());
    }

}
