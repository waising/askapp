package com.asking91.app.ui.juniorhigh;

/**
 * 初升高pdf
 * Created by jswang on 2017/4/5.
 */

public class JuniorToHighPdfEvent {
    public int state;
    public int progress;
    public String titProgress;

    public String fileUrl;

    public JuniorToHighPdfEvent(int state) {
        this.state = state;
    }

    public JuniorToHighPdfEvent(int state, int progress, String titProgress) {
        this.state = state;
        this.progress = progress;
        this.titProgress = titProgress;
    }



}
