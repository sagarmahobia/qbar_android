package com.sagar.qbar.activities.host.results.email;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.ResultCommonModel;
import com.sagar.qbar.databinding.FragmentEmailBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailFragment extends Fragment implements EmailFragmentEventHandler {

    @Inject
    EmailFragmentViewModelFactory viewModelFactory;

    private EmailFragmentModel model;
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
        EmailFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(EmailFragmentViewModel.class);

        model = viewModel.getEmailFragmentModel();
        commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEmailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false);

        binding.setModel(model);
        binding.setCommonModel(commonModel);
        binding.setHandler(this);

        return binding.getRoot();
    }

    private void onResponse(StorableResult storableResult) {
        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());

        EmailAddressParsedResult parsedResult = (EmailAddressParsedResult) ResultParser.parseResult(result);

        commonModel.setTimestamp(storableResult.getTimestamp());
        commonModel.setType(parsedResult.getType());

        model.setEmailAddressParsedResult(parsedResult);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClickEmail(EmailFragmentModel emailFragmentModel) {
        EmailAddressParsedResult emailAddressParsedResult = emailFragmentModel.getEmailAddressParsedResult();
        String[] bccs = emailAddressParsedResult.getBCCs();
        String[] ccs = emailAddressParsedResult.getCCs();
        String[] tos = emailAddressParsedResult.getTos();
        String subject = emailAddressParsedResult.getSubject();
        String body = emailAddressParsedResult.getBody();
        ShareCompat.IntentBuilder from = ShareCompat.IntentBuilder.from(this.getActivity());
        if (bccs != null) {
            from.addEmailBcc(bccs);
        }
        if (ccs != null) {
            from.addEmailCc(ccs);
        }

        if (tos != null) {
            from.addEmailTo(tos);
        }
        from.setSubject(subject)
                .setText(body)
                .setType("message/rfc822")
                .startChooser();
    }

    @Override
    public void onClickAddContact(EmailFragmentModel emailFragmentModel) {

        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);

        intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emailFragmentModel.getEmailAddressParsedResult().getTos());

        startActivity(intent);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClickShare(EmailFragmentModel emailFragmentModel) {
        ShareTextUtil.share(getContext(), emailFragmentModel.getDisplayResult());
    }
}
