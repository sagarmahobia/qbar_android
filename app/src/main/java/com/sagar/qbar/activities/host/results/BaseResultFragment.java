package com.sagar.qbar.activities.host.results;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.services.FirebaseService;
import com.sagar.qbar.services.SharedPreferenceService;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by SAGAR MAHOBIA on 10-Feb-19. at 01:01
 */
public class BaseResultFragment extends Fragment {

    @Inject
    SharedPreferenceService sharedPreference;

    @Inject
    FirebaseService firebaseService;

    protected long id;
    protected ResultCommonModel commonModel;

    private static boolean shown;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        id = arguments != null ? arguments.getLong("id", 0) : 0;

        if (id == 0) {
            throw new IllegalStateException("id should be passed");
        }
    }

    protected void onResponse(StorableResult storableResult) {
        commonModel.setTimestamp(storableResult.getTimestamp());
        commonModel.setType(storableResult.getParsedResultType());
    }

    protected void observeTimerResponse(ResultCommonViewModel viewModel) {
        viewModel.getTimerLiveData().observe(this, (bool) -> {
            if (sharedPreference.getLaunchCount() % 3 == 0 && !sharedPreference.checkRated() && !shown) {
                showAlertDialog();
                shown = true;
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    void showAlertDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("⭐⭐⭐⭐⭐")
                .setMessage("Enjoying this app? Help us grow by rating this app.")
                .setPositiveButton("Ok, Sure", (dialog, which) -> {
                    dialog.dismiss();
                    openStore();
                    sharedPreference.setRated();
                    firebaseService.onRateRequestAccepted();
                })
                .setNegativeButton("No, Thanks", (dialog, which) -> {
                    dialog.dismiss();
                    firebaseService.onRateRequestRejected();
                })
                .setCancelable(false)
                .create()
                .show();
    }

    @SuppressWarnings("ConstantConditions")
    private void openStore() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=" + getContext().getPackageName())
        );
        startActivity(browserIntent);
    }

    // TODO: 11-Feb-19 use abstract method
}
