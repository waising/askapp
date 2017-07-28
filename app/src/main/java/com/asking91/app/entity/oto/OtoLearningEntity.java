package com.asking91.app.entity.oto;

import java.util.ArrayList;

/**
 * Created by wxiao on 2016/11/24.
 * 接收教师端发来的操作信息
 */

public class OtoLearningEntity {
    private int operate;
    private double operateTime;
    private int lineWidth;
    private int colorR;
    private int colorG;
    private int colorB;
    private float colorA;
    private ArrayList<PointInt> pointInts = new ArrayList<>();
    public PointInt newPointInt(int pointX, int pointY){
        return new PointInt(pointX, pointY);
    }

    public int getOperate() {
        return operate;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }

    public double getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(double operateTime) {
        this.operateTime = operateTime;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getColorR() {
        return colorR;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public float getColorA() {
        return colorA;
    }

    public void setColorA(float colorA) {
        this.colorA = colorA;
    }

    public ArrayList<PointInt> getPointInts() {
        return pointInts;
    }

    public void setPointInts(ArrayList<PointInt> pointInts) {
        this.pointInts = pointInts;
    }

    public class PointInt{
        private int pointX;
        private int pointY;

        public PointInt() {
        }

        public PointInt(int pointX, int pointY) {
            this.pointX = pointX;
            this.pointY = pointY;
        }

        public int getPointX() {
            return pointX;
        }

        public void setPointX(int pointX) {
            this.pointX = pointX;
        }

        public int getPointY() {
            return pointY;
        }

        public void setPointY(int pointY) {
            this.pointY = pointY;
        }
    }
}
