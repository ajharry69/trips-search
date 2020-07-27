package com.hava.trips.ui;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected OnBackPressedCallback mOnBackPressedCallback;
    protected OnBackPressedDispatcher mOnBackPressedDispatcher;

    public BaseFragment() {
        super();
    }

    public BaseFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOnBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        mOnBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onBackPressed();
            }
        };

        mOnBackPressedDispatcher.addCallback(this, mOnBackPressedCallback);
    }

    protected void setupToolbar(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
    }

    protected void onBackPressed() {
        mOnBackPressedCallback.setEnabled(false);
        mOnBackPressedDispatcher.onBackPressed();
    }
}
