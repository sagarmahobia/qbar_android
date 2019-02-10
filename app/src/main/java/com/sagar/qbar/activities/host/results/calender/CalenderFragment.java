package com.sagar.qbar.activities.host.results.calender;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.google.zxing.client.result.CalendarParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.BaseResultFragment;
import com.sagar.qbar.databinding.FragmentCalenderBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends BaseResultFragment implements CalenderFragmentEventHandler {


    @Inject
    CalenderFragmentViewModelFactory viewModelFactory;

    private CalenderFragmentModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModelFactory.setId(super.id);
        CalenderFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(CalenderFragmentViewModel.class);
        model = viewModel.getCalenderFragmentModel();
        super.commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);
        super.observeTimerResponse(viewModel);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCalenderBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calender, container, false);

        binding.setModel(model);
        binding.setCommonModel(super.commonModel);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    protected void onResponse(@NonNull StorableResult storableResult) {
        super.onResponse(storableResult);

        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());
        CalendarParsedResult parsedResult = (CalendarParsedResult) ResultParser.parseResult(result);
        model.setParsedResult(parsedResult);
    }

    @Override
    public void onClickSave(CalenderFragmentModel model) {

        CalendarParsedResult parsedResult = model.getParsedResult();
        String summary = parsedResult.getSummary();
        long start = parsedResult.getStartTimestamp();
        boolean startAllDay = parsedResult.isStartAllDay();
        long end = parsedResult.getEndTimestamp();
        boolean endAllDay = parsedResult.isEndAllDay();
        String location = parsedResult.getLocation();
        String organizer = parsedResult.getOrganizer();
        //   String[] attendees = parsedResult.getAttendees();TODO
        String description = parsedResult.getDescription();
        //   double latitude = parsedResult.getLatitude();TODO
        //   double longitude = parsedResult.getLongitude();TODO


        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI);

        if (!startAllDay) {
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start);
        }
        if (!endAllDay) {
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        }

        intent.putExtra(CalendarContract.Events.TITLE, summary)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.ORGANIZER, organizer);

        startActivity(intent);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClickShare(CalenderFragmentModel model) {
        ShareTextUtil.share(getContext(), model.getDisplayResult());
    }
}
