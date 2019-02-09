package com.sagar.qbar.activities.host.results.text;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 22:15
 */
class TextFragmentViewModel extends ResultCommonViewModel {

    private final TextFragmentModel textFragmentModel;

    TextFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        textFragmentModel = new TextFragmentModel();
    }

    TextFragmentModel getTextFragmentModel() {
        return textFragmentModel;
    }

}
