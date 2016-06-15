package com.fuhu.middleware.componet;

/** Callback interface for delivering the progress of the responses. */
public interface IProgressListener {
    /**
     * Callback method that called on each byte transfer.
     */
    void onProgress(long transferredBytes, long totalSize);
}