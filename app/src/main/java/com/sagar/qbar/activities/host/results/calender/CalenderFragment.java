package com.sagar.qbar.activities.host.results.calender;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.sagar.qbar.activities.host.results.ResultCommonModel;
import com.sagar.qbar.databinding.FragmentCalenderBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment implements CalenderFragmentEventHandler {


    @Inject
    CalenderFragmentViewModelFactory viewModelFactory;

    private CalenderFragmentModel model;
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
        CalenderFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(CalenderFragmentViewModel.class);

        model = viewModel.getCalenderFragmentModel();
        commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCalenderBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calender, container, false);

        binding.setModel(model);
        binding.setCommonModel(commonModel);
        binding.setHandler(this);
        return binding.getRoot();
    }

    private void onResponse(@NonNull StorableResult storableResult) {
        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());

        CalendarParsedResult parsedResult = (CalendarParsedResult) ResultParser.parseResult(result);
        commonModel.setTimestamp(storableResult.getTimestamp());
        commonModel.setType(parsedResult.getType());
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
