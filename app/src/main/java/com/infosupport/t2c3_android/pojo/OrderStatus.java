package com.infosupport.t2c3_android.pojo;

/**
 * Created by koen on 1/12/16.
 */
public enum OrderStatus {
    PLACED("placed"),
    INPROGRESS("in progress"),
    WAITFORAPPROVAL("wait for approval"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    SENT("sent");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
