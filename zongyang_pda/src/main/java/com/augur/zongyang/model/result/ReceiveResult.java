package com.augur.zongyang.model.result;

import com.augur.zongyang.model.ReceiveForm;

/**
 * Created by yunhu on 2018-01-29.
 */

public class ReceiveResult {

    private boolean success;
    private String message;
    private ReceiveForm form;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReceiveForm getForm() {
        return form;
    }

    public void setForm(ReceiveForm form) {
        this.form = form;
    }
}
