package com.alexandroukyriakos.streetbeescodechallenge.events;

import com.alexandroukyriakos.streetbeescodechallenge.models.ComicsResult;
import com.alexandroukyriakos.streetbeescodechallenge.services.ComicsResultService;

/**
 * The event where the response from the {@link ComicsResultService#getComicsResultRequest} gets stored
 */
public class ComicsResultEvent {
    private ComicsResult mComicsResult;
    private ErrorEvent mErrorEvent;

    public ComicsResultEvent(ErrorEvent errorEvent) {
        mErrorEvent = errorEvent;
    }

    public ErrorEvent getErrorEvent() {
        return mErrorEvent;
    }

    public ComicsResult getComicsResult() {
        return mComicsResult;
    }

    public void setComicsResult(ComicsResult mComicsResult) {
        this.mComicsResult = mComicsResult;
    }
}