package com.sagar.qbar.activities.host.scanner;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.result.ParsedResultType;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.sagar.qbar.R;
import com.sagar.qbar.response.Response;
import com.sagar.qbar.response.Status;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment implements DecoratedBarcodeView.TorchListener, BarcodeCallback {

    @Inject
    ScannerFragmentViewModelFactory viewModelFactory;

    @Inject
    BeepManager beepManager;

    @BindView(R.id.barcode_scanner)
    MyBarcodeView barcodeView;

    private ScannerFragmentViewModel viewModel;
    private NavController navController;
    private boolean torchOn;
    private long id;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScannerFragmentViewModel.class);
        viewModel.getAddHistoryResponse().observe(this, this::onResponse);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_scanner, container, false);
        ButterKnife.bind(this, inflate);
        getLifecycle().addObserver(barcodeView);

        barcodeView.setStatusText("");
        return inflate;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        barcodeView.setTorchListener(this);
        navController = Navigation.findNavController(barcodeView);

        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.values());
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.initializeFromIntent(getActivity().getIntent());
        barcodeView.decodeContinuous(this);

    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        beepManager.playBeepSoundAndVibrate();
        barcodeView.pause();
        viewModel.handleResult(result);
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }

    private void onResponse(Response<ScanResponse> response) {
        Status status = response.getStatus();
        if (status == Status.ERROR) {
            Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();

        } else if (status == Status.SUCCESS) {

            ScanResponse data = response.getData();
            ParsedResultType type = data.getType();
            if (id == data.getId()) {
                return;
            }
            id = data.getId();

            Bundle bundle = new Bundle();
            bundle.putLong("id", id);

            switch (type) {
                case GEO:
                    navController.navigate(R.id.action_scannerFragment_to_geoFragment, bundle);
                    break;
                case SMS:
                    navController.navigate(R.id.action_scannerFragment_to_smsFragment, bundle);
                    break;
                case TEL:
                    navController.navigate(R.id.action_scannerFragment_to_telFragment, bundle);
                    break;
                case URI:
                    navController.navigate(R.id.action_scannerFragment_to_URIFragment, bundle);
                    break;
                case ISBN:
                case PRODUCT:
                    navController.navigate(R.id.action_scannerFragment_to_barcodeFragment, bundle);
                    break;
                case TEXT:
                    navController.navigate(R.id.action_scannerFragment_to_textFragment, bundle);
                    break;
                case VIN:
                    break;
                case WIFI:
                    navController.navigate(R.id.action_scannerFragment_to_wifiFragment, bundle);
                    break;
                case CALENDAR:
                    break;
                case ADDRESSBOOK:
                    break;
                case EMAIL_ADDRESS:
                    break;
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.scanner, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_flash) {
            switchFlashlight();
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchFlashlight() {
        if (torchOn) {
            barcodeView.setTorchOff();

        } else {
            barcodeView.setTorchOn();
        }
    }

    @Override
    public void onTorchOn() {
        this.torchOn = true;
    }

    @Override
    public void onTorchOff() {
        this.torchOn = false;
    }
}
