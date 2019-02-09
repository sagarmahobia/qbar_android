package com.sagar.qbar.activities.host.results;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.sagar.qbar.room.entities.StorableResult;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by SAGAR MAHOBIA on 10-Feb-19. at 01:01
 */
public class BaseResultFragment extends Fragment {

    protected long id;
    protected ResultCommonModel commonModel;

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

}
