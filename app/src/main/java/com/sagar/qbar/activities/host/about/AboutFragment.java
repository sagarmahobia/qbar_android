package com.sagar.qbar.activities.host.about;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagar.qbar.R;
import com.sagar.qbar.services.ImageDecoderService;
import com.sagar.qbar.utils.MyHtml;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    @Inject
    ImageDecoderService imageDecoderService;

    @BindView(R.id.about_logo_image)
    ImageView logoImageView;

    @BindView(R.id.project_link_zxing_embedded)
    TextView barcodeScannerGithub;

    @BindView(R.id.project_link_zxing)
    TextView zxingProjectLink;

    private Disposable disposable;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_about, container, false);

        ButterKnife.bind(this, inflate);

        disposable = imageDecoderService.
                getBitmapSingle(getResources().openRawResource(R.raw.logo)).
                subscribe(logoImageView::setImageBitmap);

        if (barcodeScannerGithub != null) {
            barcodeScannerGithub.setText(MyHtml.fromHtml("<a href=\"https://github.com/journeyapps/zxing-android-embedded\">https://github.com/journeyapps/zxing-android-embedded</a>"));
            barcodeScannerGithub.setMovementMethod(LinkMovementMethod.getInstance());
        }


        if (zxingProjectLink != null) {
            zxingProjectLink.setText(MyHtml.fromHtml("<a href=\"https://github.com/zxing/zxing\">https://github.com/zxing/zxing</a>"));
            zxingProjectLink.setMovementMethod(LinkMovementMethod.getInstance());
        }

        return inflate;
    }


    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
