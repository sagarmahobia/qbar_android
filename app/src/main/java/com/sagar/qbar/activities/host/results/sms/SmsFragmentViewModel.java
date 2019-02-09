package com.sagar.qbar.activities.host.results.sms;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 03-Feb-19. at 00:59
 */
class SmsFragmentViewModel extends ResultCommonViewModel {

    private final SmsFragmentModel smsFragmentModel;

    SmsFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        smsFragmentModel = new SmsFragmentModel();
    }

    SmsFragmentModel getSmsFragmentModel() {
        return smsFragmentModel;
    }

}