package com.asking91.app.ui.widget.doodle;

import android.graphics.Color;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by huangjun on 2015/6/24.
 */
public class Transaction implements Serializable {
    public interface ActionStep {
        byte START = 1;
        byte MOVE = 2;
        byte END = 3;
        byte REVOKE = 4;
        byte CLEAR_SELF = 6;
        byte CLEAR_ACK = 7;

        byte SCROLL_TO = 9;
        byte START_TIME = 10;
    }

    private byte step = ActionStep.START;
    private float x = 0.0f;
    private float y = 0.0f;

    private long lineRech = 0;
    private long lineColor = Color.BLACK;

    public long getLineRech() {
        return lineRech;
    }

    public long getLineColor() {
        return lineColor;
    }

    public Transaction() {
    }

    public Transaction(byte step, float x, float y) {
        this.step = step;
        this.x = x;
        this.y = y;
    }

    public Transaction(byte step, float x, float y,long color,long lineRech) {
        this.step = step;
        this.x = x;
        this.y = y;
        this.lineRech = lineRech;
        this.lineColor = color;
    }

    public static String pack(Transaction t) {
        return String.format("%d:%f,%f;", t.getStep(), t.getX(), t.getY());
    }

    public static String packIndex(int index) {
        return String.format("5:%d,0;", index);
    }

    public static Transaction unpack(String data) {
        int sp1 = data.indexOf(":");
        if (sp1 <= 0) {
            return null;
        }
        int sp2 = data.indexOf(",");
        if (sp2 <= 2) {
            return null;
        }
        String step = data.substring(0, sp1);
        String data1 = data.substring(sp1+1);
        String[] sp = data1.split(",");
        try {
            byte p1 = Byte.parseByte(step);
            if (p1 == 5) {
                Log.i("Transaction", "RECV DATA:" + sp[0]);
            } else {
                float p2 = Float.parseFloat(sp[0]);
                float p3 = Float.parseFloat(sp[1]);
                if (sp2 > 4) {
                    long p4 = Long.parseLong(sp[2]);
                    long p5 = Long.parseLong(sp[3]);
                    return new Transaction(p1, p2, p3,p4,p5);
                }
                return new Transaction(p1,p2,p3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void make(byte step, float x, float y) {
        this.step = step;
        this.x = x;
        this.y = y;
    }

    public Transaction makeStartTransaction(float x, float y) {
        make(ActionStep.START, x, y);
        return this;
    }

    public Transaction makeMoveTransaction(float x, float y) {
        make(ActionStep.MOVE, x, y);
        return this;
    }

    public Transaction makeEndTransaction(float x, float y) {
        make(ActionStep.END, x, y);
        return this;
    }

    public Transaction makeRevokeTransaction() {
        make(ActionStep.REVOKE, 0.0f, 0.0f);
        return this;
    }

    public Transaction makeClearSelfTransaction() {
        make(ActionStep.CLEAR_SELF, 0.0f, 0.0f);
        return this;
    }

    public Transaction makeClearAckTransaction() {
        make(ActionStep.CLEAR_ACK, 0.0f, 0.0f);
        return this;
    }

    public int getStep() {
        return step;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isPaint() {
        return !isRevoke() && !isClearSelf() && !isClearAck();
    }

    public boolean isRevoke() {
        return step == ActionStep.REVOKE;
    }

    public boolean isClearSelf() {
        return step == ActionStep.CLEAR_SELF;
    }

    public boolean isClearAck() {
        return step == ActionStep.CLEAR_ACK;
    }
}
