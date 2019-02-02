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
import com.sagar.qbar.databinding.FragmentGeoBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeoFragment extends Fragment implements GeoFragmentEventHandler {

    @Inject
    GeoFragmentViewModelFactory viewModelFactory;

    private GeoFragmentModel model;

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
        GeoFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(GeoFragmentViewModel.class);

        model = viewModel.getGeoFragmentModel();
        viewModel.getResponse().observe(this, this::onResponse);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentGeoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_geo, container, false);

        binding.setModel(model);
        binding.setHandler(this);

        return binding.getRoot();
    }

    private void onResponse(StorableResult storableResult) {

        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());

        GeoParsedResult parsedResult = (GeoParsedResult) ResultParser.parseResult(result);

        model.setTimestamp(storableResult.getTimestamp());
        model.setDisplayResult(parsedResult.getDisplayResult());
        model.setUri(parsedResult.getGeoURI());
        model.setLat(parsedResult.getLatitude());
        model.setLon(parsedResult.getLongitude());
    }

    @Override
    public void onClickShowMap(GeoFragmentModel model) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(model.getUri()));
        startActivity(intent);
    }

    @Override
    public void onClickDirection(GeoFragmentModel model) {

        Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + model.getLat() + "," + model.getLon()));

        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }

    @Override
    public void onClickShare(GeoFragmentModel model) {
        Context context = this.getContext();
        if (context != null) {
            ShareTextUtil.share(context, model.getUri());
        }
    }
}
