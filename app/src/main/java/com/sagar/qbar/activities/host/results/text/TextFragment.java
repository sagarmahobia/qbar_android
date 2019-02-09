package com.sagar.qbar.activities.host.results.text;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.BaseResultFragment;
import com.sagar.qbar.databinding.FragmentTextBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.SearchUtil;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;


public class TextFragment extends BaseResultFragment implements TextFragmentEventHandler {

    @Inject
    TextFragmentViewModelFactory viewModelFactory;

    private TextFragmentModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModelFactory.setId(super.id);

        TextFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(TextFragmentViewModel.class);
        model = viewModel.getTextFragmentModel();
        super.commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTextBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_text, container, false);

        binding.setModel(model);
        binding.setCommonModel(super.commonModel);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    protected void onResponse(StorableResult storableResult) {
        super.onResponse(storableResult);
        model.setText(storableResult.getText());
    }

    @Override
    public void onClickWebSearch(TextFragmentModel model) {
        Context context = this.getContext();
        if (context != null) {
            SearchUtil.searchText(context, model.getText());
        }
    }

    @Override
    public void onClickShare(TextFragmentModel model) {
        Context context = this.getContext();
        if (context != null) {
            ShareTextUtil.share(context, model.getText());
        }

    }
}
