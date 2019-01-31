package com.sagar.qbar.response;

import android.support.annotation.NonNull;

import static com.sagar.qbar.response.Status.ERROR;
import static com.sagar.qbar.response.Status.LOADING;
import static com.sagar.qbar.response.Status.SUCCESS;

/**
 * Created by SAGAR MAHOBIA on 18-Jan-19. at 20:07
 */
public class Response<T> {

    private final Status type;

    private final T data;

    private final Throwable error;

    private Response(Status status, T data, Throwable error) {
        this.type = status;
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    @NonNull
    public Status getStatus() {
        return type;
    }

    public Throwable getError() {
        return error;
    }

    @NonNull
    public static <T> Response loading() {
        return new Response<T>(LOADING, null, null);
    }


    @NonNull
    public static <T> Response success(T data) {
        return new Response<>(SUCCESS, data, null);
    }

    @NonNull
    public static Response error(@NonNull Throwable error) {
        return new Response<>(ERROR, null, error);
    }

}
