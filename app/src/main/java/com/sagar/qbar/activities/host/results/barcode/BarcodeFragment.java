package com.sagar.qbar.activities.host.results.barcode;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.uri.URIFragmentModel;
import com.sagar.qbar.databinding.FragmentBarcodeBinding;
import com.sagar.qbar.databinding.FragmentUriBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.OpenUrlUtil;
import com.sagar.qbar.utils.SearchUtil;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarcodeFragment extends Fragment implements BarcodeFragmentEventHandler {

    @Inject
    BarcodeFragmentViewModelFactory viewModelFactory;

    private BarcodeFragmentModel model;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        long id = arguments != null ? arguments.getLong("id", 0) : 0;

        if (id == 0) {
            throw new IllegalStateException("id should be passed");
        }

        viewModelFactory.setId(id);

        BarcodeFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(BarcodeFragmentViewModel.class);
        model = viewModel.getBarcodeFragmentModel();
        viewModel.getResponse().observe(this, this::onResponse);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBarcodeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_barcode, container, false);

        binding.setModel(model);
        binding.setHandler(this);
        return binding.getRoot();
    }

    private void onResponse(StorableResult storableResult) {
        model.setTimestamp(storableResult.getTimestamp());
        model.setBarcode(storableResult.getText());
        model.setType(storableResult.getParsedResultType());
    }


    @Override
    public void onClickWebSearch(BarcodeFragmentModel model) {
        Context context = this.getContext();
        if (context != null) {
            SearchUtil.searchText(context, model.getBarcode());
        }
    }

    @Override
    public void onClickShare(BarcodeFragmentModel model) {
        Context context = this.getContext();
        if (context != null) {
            ShareTextUtil.share(context, model.getBarcode());
        }

    }
}
