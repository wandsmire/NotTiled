package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.utils.Json;
import com.mirwanda.nottiled.autotiles;

import java.util.ArrayList;
import java.util.List;

public class savegame {

    public savegame(){}

    public List<KV> getVars() {
        return vars;
    }

    public void setVars(List<KV> vars) {
        this.vars = vars;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getMapname() {
        return mapname;
    }

    public void setMapname(String mapname) {
        this.mapname = mapname;
    }

    public java.util.List<KV> vars = new ArrayList<KV>();
    public float x;
    public float y;

    public String mapname;
}
