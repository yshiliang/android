package com.stepone.uikit.dispatcher.request;

/**
 * FileName: BackRequest
 * Author: shiliang
 * Date: 2019-12-01 23:26
 */
public final class BackRequest extends Request {
    private int step;//回退层级， 默认为1，小于1或者大于当前回退栈层级时，会返回到栈底

    public BackRequest() {
        this(1);
    }

    public BackRequest(int backStep) {
        super();
        step = backStep;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int backStep) {
        step = backStep;
    }
}
