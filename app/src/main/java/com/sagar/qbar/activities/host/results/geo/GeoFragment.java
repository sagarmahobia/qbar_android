package com.sagar.qbar.activities.host.results.geo;


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
import com.google.zxing.client.result.GeoParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.BaseResultFragment;
import com.sagar.qbar.databinding.FragmentGeoBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeoFragment extends BaseResultFragment implements GeoFragmentEventHandler {

    @Inject
    GeoFragmentViewModelFactory viewModelFactory;

    private GeoFragmentModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModelFactory.setId(super.id);
        GeoFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(GeoFragmentViewModel.class);

        model = viewModel.getGeoFragmentModel();
        super.commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);
        super.observeTimerResponse(viewModel);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentGeoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_geo, container, false);

        binding.setModel(model);
        binding.setCommonModel(super.commonModel);
        binding.setHandler(this);

        return binding.getRoot();
    }

    @Override
    protected void onResponse(StorableResult storableResult) {
        super.onResponse(storableResult);

        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());
        GeoParsedResult parsedResult = (GeoParsedResult) ResultParser.parseResult(result);
        model.setGeoParsedResult(parsedResult);
    }

    @Override
    public void onClickShowMap(GeoFragmentModel model) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(model.getGeoParsedResult().getGeoURI()));
        startActivity(intent);
    }

    @Override
    public void onClickDirection(GeoFragmentModel model) {

        GeoParsedResult geoParsedResult = model.getGeoParsedResult();

        Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + geoParsedResult.getLongitude() + "," + geoParsedResult.getLatitude()));

        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }

    @Override
    public void onClickShare(GeoFragmentModel model) {
        Context context = this.getContext();
        if (context != null) {
            ShareTextUtil.share(context, model.getGeoParsedResult().getGeoURI());
        }
    }
}
