package com.sagar.qbar.activities.host.results.text;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.ResultCommonModel;
import com.sagar.qbar.databinding.FragmentTextBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.SearchUtil;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class TextFragment extends Fragment implements TextFragmentEventHandler {

    @Inject
    TextFragmentViewModelFactory viewModelFactory;

    private TextFragmentModel model;
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

        TextFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(TextFragmentViewModel.class);
        model = viewModel.getTextFragmentModel();
        commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTextBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_text, container, false);

        binding.setModel(model);
        binding.setCommonModel(commonModel);
        binding.setHandler(this);
        return binding.getRoot();
    }

    private void onResponse(StorableResult storableResult) {
        commonModel.setTimestamp(storableResult.getTimestamp());
        commonModel.setType(storableResult.getParsedResultType());
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
