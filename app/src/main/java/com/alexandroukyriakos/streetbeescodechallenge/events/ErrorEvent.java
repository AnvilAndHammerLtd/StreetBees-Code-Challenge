package com.alexandroukyriakos.streetbeescodechallenge.events;

/**
 * A generic event for simple error messages
 */
public class ErrorEvent {
    private String mErrorMessage;

    public ErrorEvent(String errorMessage) {
        this.mErrorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }
}
