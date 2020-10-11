package com.mirwanda.nottiled.platformer;

public class KV {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String key;
    public int value;
    public KV(){}
    public KV(String key, int value){
        this.key=key; this.value=value;
    }
}