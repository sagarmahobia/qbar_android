package com.sagar.qbar.activities.host.results.calender;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 19:46
 */
class CalenderFragmentViewModel extends ResultCommonViewModel {
    private CalenderFragmentModel calenderFragmentModel;

    CalenderFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        calenderFragmentModel = new CalenderFragmentModel();
    }
    CalenderFragmentModel getCalenderFragmentModel() {
        return calenderFragmentModel;
    }

}
