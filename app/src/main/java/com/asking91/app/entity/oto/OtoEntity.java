package com.asking91.app.entity.oto;

/**
 * Created by wxiao on 2016/11/18.
 */

public class OtoEntity {
    /**
     * success : true
     * msg : hDTxHB2h9KTZT2vUviZ0nxZJoLyLfmXk5NJKkQ0N:kdor-D53XclCOn39KmIWSBydnDo=:eyJzY29wZSI6InpoaWJpcmQiLCJkZWFkbGluZSI6MTQ3OTQ3NTU5OX0=
     * prompt : 获取成功
     */

    private boolean success;
    private String msg;
    private String prompt;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
