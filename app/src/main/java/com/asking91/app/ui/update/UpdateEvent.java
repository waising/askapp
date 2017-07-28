package com.asking91.app.ui.update;

/**
 * Created by jswang on 2017/4/5.
 */

public class UpdateEvent {
    public int state;
    public int progress;
    public String titProgress;

    public UpdateEvent(int state) {
        this.state = state;
    }

    public UpdateEvent(int state, int progress, String titProgress) {
        this.state = state;
        this.progress = progress;
        this.titProgress = titProgress;
    }

}
