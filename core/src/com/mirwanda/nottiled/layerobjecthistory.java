package com.mirwanda.nottiled;

import com.mirwanda.nottiled.obj;

public class layerobjecthistory {

    public obj getRelatedobj() {
        return relatedobj;
    }

    public void setRelatedobj(obj relatedobj) {
        this.relatedobj = relatedobj;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isFollower() {
        return follower;
    }

    public void setFollower(boolean follower) {
        this.follower = follower;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    obj relatedobj;
    int layer;
    String data;
    boolean follower;
    String action = "edit";

}
