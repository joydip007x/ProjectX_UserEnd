package com.example.projectxue.ui.past_order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PastOrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PastOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is past fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}