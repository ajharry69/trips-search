package com.hava.trips.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class TaskResult<T> {
    private TaskResult() {
    }

    public boolean isLoading() {
        return this instanceof Loading;
    }

    public boolean isSuccess() {
        return this instanceof Success;
    }

    public boolean isError() {
        return this instanceof Error;
    }

    @NonNull
    public T getDataOrFail() throws Exception {
        T data = getData();
        if (data == null) throw new Exception("Data not found");
        else return data;
    }

    @Nullable
    public T getData() {
        if (isSuccess()) return ((Success<T>) this).getData();
        else return null;
    }

    public String getErrorMessage() {
        if (isError()) {
            Throwable exception = ((Error<T>) this).getError();
            String error = exception.getLocalizedMessage();
            if (error == null) error = exception.getMessage();
            return error;
        }
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        if (isError()) return String.format("Error: %s", getErrorMessage());
        if (isSuccess()) return String.format("Data: %s", ((Success<T>) this).getData());
        if (isLoading()) return "Loading...";
        return super.toString();
    }

    public static final class Success<T> extends TaskResult<T> {
        private final T data;

        public Success(@NonNull T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static final class Error<T> extends TaskResult<T> {
        private final Throwable error;

        public Error(String message) {
            this(new RuntimeException(message));
        }

        public Error(@NonNull Throwable error) {
            this.error = error;
        }

        public Throwable getError() {
            return error;
        }
    }

    public static final class Loading<T> extends TaskResult<T> {

    }
}
