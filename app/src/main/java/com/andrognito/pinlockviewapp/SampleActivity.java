package com.andrognito.pinlockviewapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

public class SampleActivity extends AppCompatActivity {

    public static final String TAG = "PinLockView";

    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDotsFirst;
    private IndicatorDots mIndicatorDotsSecond;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);
            Animation shake = AnimationUtils.loadAnimation(SampleActivity.this, R.anim.shake_wrong);
            mIndicatorDotsFirst.startAnimation(shake);
//            mIndicatorDotsFirst.setErrorDots();
            mIndicatorDotsFirst.setSuccessDots();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIndicatorDotsFirst.setDefaultDots();
                    mPinLockView.resetPinLockView();

//                    mPinLockView.attachIndicatorDots(mIndicatorDotsSecond);

                }
            }, 1000);
        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sample);

        mPinLockView = findViewById(R.id.pin_lock_view);
        mIndicatorDotsFirst = findViewById(R.id.indicator_dots_first);
        mIndicatorDotsSecond = findViewById(R.id.indicator_dots_second);


        mPinLockView.attachIndicatorDots(mIndicatorDotsFirst);
        mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDotsFirst.setIndicatorType(IndicatorDots.IndicatorType.FIXED);
    }
}
