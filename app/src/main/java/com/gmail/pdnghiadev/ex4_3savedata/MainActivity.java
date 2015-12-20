package com.gmail.pdnghiadev.ex4_3savedata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


import com.gmail.pdnghiadev.ex4_3savedata.model.ResultItem;

import java.io.FileOutputStream;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String TAG_RESULT_FRAGMENT = "ResultFragment";
    public static final int TIME_COUNT = 10000; // 10s
    public static final String CURRENT_TIME = "current_time";
    public static final String COUNT_CHANGE = "count_change";
    public static final String START_TIME = "start_time";
    public static final String DURATION_TIME = "duration_time";
    public static final String COUNT = "count";
    private Button mBtnStart, mBtnTap, mBtnReset;
    private Chronometer mTime;
    private TextView mCountTap; // Save the count tap when user press
    private long mStartTime = 0; // Get the time when start
    private int mCount; // Save the count tap after load screen again
    private TapCountResultFragment fragment; // Fragment show list highscore
    private long mDurationTime = 0; // Thời gian đã trôi qua
    private long mCurrentTime = 0; // Thời gian lúc người dùng xoay màn hình
    private int countChange = 0; // Số lần xoay màn hình

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        loadComponents();

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTapping();
            }
        });

        mBtnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                mCountTap.setText(String.valueOf(mCount));
            }
        });

        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.onClear();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fragment = (TapCountResultFragment) fm.findFragmentByTag(TAG_RESULT_FRAGMENT);

        // If the Fragment is non-null, then it is being retained
        // over a configuration change
        if (fragment == null) {
            fragment = new TapCountResultFragment();
        }

        if (!fragment.isAdded()) {
            fm.beginTransaction().add(R.id.fl_result_fragment, fragment, TAG_RESULT_FRAGMENT).commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        mTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                if (countChange == 0) { // Trạng thái đầu tiên chạy từ 0 -> TIME_COUNT
                    if (SystemClock.elapsedRealtime() - mStartTime >= TIME_COUNT) {
                        pauseTapping();
                    }
                } else { // Trạng thái đang chạy tạm dừng 0 -> 2s -> 4s -> TIME_COUNT
                    long totalTime = TIME_COUNT - mDurationTime; // Tổng số thời gian còn lại sau khi trừ đi thời gian trôi qua VD: 10 - 2 = 8
                    if (SystemClock.elapsedRealtime() - mStartTime >= totalTime) {
                        pauseTapping();
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void startTapping() {
        // Thay đổi trạng thái Button
        mBtnStart.setEnabled(false);
        mBtnTap.setEnabled(true);

        mStartTime = SystemClock.elapsedRealtime();

        if (countChange == 0) { // Trạng thái lần đầu nhấn
            mTime.setBase(SystemClock.elapsedRealtime()); // Thiết lập thòi gian theo hệ thống 00:00
            mCount = 0;
        } else { // Trạng thái khi xoay màn hình
            mTime.setBase(SystemClock.elapsedRealtime() - mDurationTime); // Thiết lập thời gian sau khi trừ đi thời gian đã trôi qua => VD: 00:02
        }

        // Chạy Chronometer
        mTime.start();
        mCountTap.setText(String.valueOf(mCount));

    }

    private void pauseTapping() {
        // Thay đổi trạng thái Button
        mBtnTap.setEnabled(false);
        mBtnStart.setEnabled(true);
        mBtnStart.setText("Start");

        // Trở lại mặc định
        countChange = 0;
        mStartTime = 0;
        mCurrentTime = 0;
        mDurationTime = 0;

        // Dừng Chronometer
        mTime.stop();

        // Pass Object to Fragment
        fragment.onClick(new ResultItem(new Date(), mCount));

    }

    private void loadComponents() {
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnTap = (Button) findViewById(R.id.btn_tap);
        mTime = (Chronometer) findViewById(R.id.ch_time);
        mCountTap = (TextView) findViewById(R.id.tv_time);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");

        mTime.stop();

        if (mStartTime == 0) {
            countChange = 0;
        } else {
            if (mCurrentTime == 0) {
                countChange++;
                mCurrentTime = SystemClock.elapsedRealtime(); // Thời gian khi xoay màn hình
            } else {
                if (mCurrentTime - mStartTime != mDurationTime) { // Kiểm tra trạng thái khi bấm Resume sau 1 thời gian
                    // VD: Khi 00:02 và bấm Resume đến 00:04 thì xoay màn hình
                    mCurrentTime = SystemClock.elapsedRealtime() + mDurationTime;
                }
            }
            mDurationTime = mCurrentTime - mStartTime;
            outState.putLong(DURATION_TIME, mDurationTime);
            outState.putLong(START_TIME, mStartTime);
            outState.putLong(CURRENT_TIME, mCurrentTime);
        }
        outState.putInt(COUNT_CHANGE, countChange);
        outState.putInt(COUNT, mCount);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");

        countChange = savedInstanceState.getInt(COUNT_CHANGE);
        mDurationTime = savedInstanceState.getLong(DURATION_TIME);
        mStartTime = savedInstanceState.getLong(START_TIME);
        mCurrentTime = savedInstanceState.getLong(CURRENT_TIME);
        mCount = savedInstanceState.getInt(COUNT);
        if (countChange != 0) {
            mTime.setBase(SystemClock.elapsedRealtime() - mDurationTime);
            mBtnStart.setText("Resume");
        }
        mCountTap.setText(String.valueOf(mCount));
    }

}
