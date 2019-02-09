package com.sagar.qbar.activities.host.history;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.client.result.ParsedResultType;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.history.adapter.HistoryAdapter;
import com.sagar.qbar.activities.host.history.adapter.HistoryModel;
import com.sagar.qbar.databinding.FragmentHistoryBinding;
import com.sagar.qbar.room.entities.StorableResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.navigation.Navigation;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryAdapter.AdapterListener {

    @Inject
    HistoryFragmentViewModelFactory viewModelFactory;

    @Inject
    HistoryAdapter adapter;

    private HistoryFragmentViewModel viewModel;
    private HistoryFragmentModel historyFragmentModel;

    private View root;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryFragmentViewModel.class);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        historyFragmentModel = viewModel.getHistoryFragmentModel();
        binding.setModel(historyFragmentModel);

        RecyclerView recyclerView = binding.historyRecyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter.setAdapterListener(this);

        recyclerView.setAdapter(adapter);
        root = binding.getRoot();
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.setHistoryItemEventHandler(viewModel);
        viewModel.getStorableResultsLiveData().observe(this, this::observeHistory);
    }

    private void observeHistory(List<StorableResult> storableResults) {

        List<HistoryModel> historyModels = new ArrayList<>();
        for (StorableResult storableResult : storableResults) {
            HistoryModel historyModel = new HistoryModel();

            historyModel.setImageResource(storableResult.getParsedResultType());
            historyModel.setText(storableResult.getText());
            historyModel.setId(storableResult.getId());
            historyModel.setParsedResultType(storableResult.getParsedResultType());

            historyModels.add(historyModel);
        }

        adapter.submitList(historyModels);
        historyFragmentModel.setSize(storableResults.size());
    }


    @Override
    public void onHistoryClicked(HistoryModel history) {
        ParsedResultType type = history.getParsedResultType();

        Bundle bundle = new Bundle();
        bundle.putLong("id", history.getId());

        switch (type) {
            case GEO:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_geoFragment, bundle);
                break;
            case SMS:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_smsFragment, bundle);
                break;
            case TEL:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_telFragment, bundle);
                break;
            case URI:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_URIFragment, bundle);
                break;
            case ISBN:
            case PRODUCT:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_barcodeFragment, bundle);
                break;
            case TEXT:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_textFragment, bundle);
                break;
            case VIN:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_vinFragment, bundle);
                break;
            case WIFI:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_wifiFragment, bundle);
                break;
            case CALENDAR:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_calenderFragment, bundle);
                break;
            case ADDRESSBOOK:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_contactFragment, bundle);
                break;
            case EMAIL_ADDRESS:
                Navigation.findNavController(root).navigate(R.id.action_historyFragment_to_emailFragment, bundle);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.history, menu);
    }

    @SuppressWarnings("ConstantConditions")
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Do you want delete all history?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dialog.dismiss();
                        viewModel.clearAll();
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setCancelable(true)
                    .create()
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
