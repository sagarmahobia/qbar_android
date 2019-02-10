package com.sagar.qbar.activities.host.results.contact;


import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.results.BaseResultFragment;
import com.sagar.qbar.databinding.FragmentContactBinding;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.utils.ShareTextUtil;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseResultFragment implements ContactFragmentEventHandler {


    @Inject
    ContactFragmentViewModelFactory viewModelFactory;

    private ContactFragmentModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModelFactory.setId(super.id);
        ContactFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactFragmentViewModel.class);
        model = viewModel.getContactFragmentModel();
        super.commonModel = viewModel.getCommonModel();
        viewModel.getResponse().observe(this, this::onResponse);
        super.observeTimerResponse(viewModel);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentContactBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);

        binding.setModel(model);
        binding.setCommonModel(super.commonModel);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    protected void onResponse(@NonNull StorableResult storableResult) {
        super.onResponse(storableResult);

        Result result = new Result(storableResult.getText(), null, null, storableResult.getBarcodeFormat(), storableResult.getTimestamp());
        AddressBookParsedResult parsedResult = (AddressBookParsedResult) ResultParser.parseResult(result);
        model.setParsedResult(parsedResult);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClickSave(ContactFragmentModel model) {
        AddressBookParsedResult parsedResult = model.getParsedResult();

        String[] names = parsedResult.getNames();
        String[] nicknames = parsedResult.getNicknames();

        String[] addresses = parsedResult.getAddresses();
        String[] addressTypes = parsedResult.getAddressTypes();

        String[] emails = parsedResult.getEmails();
        String[] emailTypes = parsedResult.getEmailTypes();

        String org = parsedResult.getOrg();

        String[] phoneNumbers = parsedResult.getPhoneNumbers();
        String[] phoneTypes = parsedResult.getPhoneTypes();

        String note = parsedResult.getNote();
        String[] urLs = parsedResult.getURLs();

        String title = parsedResult.getTitle();
//        String birthday = parsedResult.getBirthday();
//        String[] geo = parsedResult.getGeo();//todo
//        String instantMessenger = parsedResult.getInstantMessenger();//todo
//        String pronunciation = parsedResult.getPronunciation();//todo

        ArrayList<ContentValues> data = new ArrayList<>();

        if (names != null) {
            for (String name : names) {
                ContentValues nameContentValues = new ContentValues();
                nameContentValues.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                nameContentValues.put(CommonDataKinds.StructuredName.GIVEN_NAME, name);
                data.add(nameContentValues);
            }
        }
        if (nicknames != null) {
            for (String nickname : nicknames) {
                ContentValues nickNameContentValues = new ContentValues();
                nickNameContentValues.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Nickname.CONTENT_ITEM_TYPE);
                nickNameContentValues.put(CommonDataKinds.Nickname.NAME, nickname);
                data.add(nickNameContentValues);
            }
        }

        if (addresses != null) {
            for (int i = 0; i < addresses.length; i++) {
                ContentValues addressContentValues = new ContentValues();
                addressContentValues.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);

                String addressType = addressTypes[i];
                if (addressType != null) {
                    String type = addressType.toLowerCase();
                    if (type.contains("home")) {
                        addressContentValues.put(CommonDataKinds.StructuredPostal.TYPE, CommonDataKinds.StructuredPostal.TYPE_HOME);
                    } else if (type.contains("work")) {
                        addressContentValues.put(CommonDataKinds.StructuredPostal.TYPE, CommonDataKinds.StructuredPostal.TYPE_WORK);
                    }
                }
                String address = addresses[i];
                addressContentValues.put(CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, address);
                data.add(addressContentValues);

            }
        }

        if (emails != null) {
            for (int i = 0; i < emails.length; i++) {
                ContentValues emailContentValues = new ContentValues();
                emailContentValues.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);

                String emailType = emailTypes[i];
                if (emailType != null) {
                    String type = emailType.toLowerCase();
                    if (type.contains("home")) {
                        emailContentValues.put(CommonDataKinds.Email.TYPE, CommonDataKinds.Email.TYPE_HOME);
                    } else if (type.contains("work")) {
                        emailContentValues.put(CommonDataKinds.Email.TYPE, CommonDataKinds.Email.TYPE_WORK);
                    }
                }
                String email = emails[i];
                emailContentValues.put(CommonDataKinds.Email.ADDRESS, email);
                data.add(emailContentValues);

            }
        }

        if (phoneNumbers != null) {

            for (int i = 0; i < phoneNumbers.length; i++) {

                ContentValues phoneContentValues = new ContentValues();
                phoneContentValues.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                String phoneType = phoneTypes[i];
                if (phoneType != null) {
                    String s = phoneType.toLowerCase();
                    if (s.contains("home")) {
                        phoneContentValues.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_HOME);
                    } else if (s.contains("work")) {
                        phoneContentValues.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_WORK);
                    }
                }
                phoneContentValues.put(CommonDataKinds.Phone.NUMBER, phoneNumbers[i]);
                data.add(phoneContentValues);
            }

        }

        if (org != null && !org.equalsIgnoreCase("")) {
            ContentValues orgContentValues = new ContentValues();
            orgContentValues.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Organization.CONTENT_ITEM_TYPE);
            orgContentValues.put(CommonDataKinds.Organization.COMPANY, org);
            orgContentValues.put(CommonDataKinds.Organization.JOB_DESCRIPTION, title);
            data.add(orgContentValues);
        }


        if (note != null && !note.equalsIgnoreCase("")) {
            ContentValues orgContentValues = new ContentValues();
            orgContentValues.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Note.CONTENT_ITEM_TYPE);
            orgContentValues.put(CommonDataKinds.Note.NOTE, note);
            data.add(orgContentValues);
        }

        if (urLs != null) {
            for (String urL : urLs) {
                ContentValues urlContentValues = new ContentValues();
                urlContentValues.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                urlContentValues.put(CommonDataKinds.Website.URL, urL);
                data.add(urlContentValues);
            }
        }

        Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
        intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data);

        getContext().startActivity(intent);

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClickShare(ContactFragmentModel model) {
        ShareTextUtil.share(getContext(), model.getDisplayResult());
    }
}
