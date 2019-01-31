package com.sagar.qbar.activities.host.scanner;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.sagar.qbar.response.Response;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 15:41
 */
class ScannerFragmentViewModel extends ViewModel {

    private StorableResultRepository repository;
    private MutableLiveData<Response> addHistoryResponse;

    private CompositeDisposable disposable;

    ScannerFragmentViewModel(StorableResultRepository repository) {
        this.repository = repository;
        addHistoryResponse = new MutableLiveData<>();

        disposable = new CompositeDisposable();
    }

    LiveData<Response> getAddHistoryResponse() {
        return addHistoryResponse;
    }

    void handleResult(BarcodeResult result) {

        ParsedResult parsedResult = ResultParser.parseResult(result.getResult());

        StorableResult storableResult = new StorableResult();
        storableResult.setBarcodeFormat(result.getBarcodeFormat());
        storableResult.setParsedResultType(parsedResult.getType());
        storableResult.setText(parsedResult.getDisplayResult());
        storableResult.setTimestamp(result.getTimestamp());

        disposable.add(repository.insert(storableResult).
                subscribe(id -> {
                            ScanResponse scanResponse = new ScanResponse();
                            scanResponse.setId(id);
                            scanResponse.setType(storableResult.getParsedResultType());
                            addHistoryResponse.setValue(Response.success(scanResponse));
                        },
                        error -> addHistoryResponse.setValue(Response.error(error))));
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
    }
}
