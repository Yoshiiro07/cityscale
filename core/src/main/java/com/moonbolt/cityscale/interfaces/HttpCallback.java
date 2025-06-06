package com.moonbolt.cityscale.interfaces;

public interface HttpCallback {
    void onSuccess(String response);
    void onFailure(Throwable t);
}
