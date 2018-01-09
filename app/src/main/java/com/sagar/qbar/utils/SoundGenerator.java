package com.sagar.qbar.utils;

import android.media.AudioManager;
import android.media.ToneGenerator;

/**
 * Created by SAGAR MAHOBIA on 06-Jan-18.
 */

public class SoundGenerator {

    public static void playBeep(  ) {
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_DTMF_C,50);
    }
}
