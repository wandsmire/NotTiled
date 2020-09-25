package com.mirwanda.nottiled.ai;
import com.badlogic.gdx.ai.pfa.Connection;

public class Transition implements Connection<AutoTile>{
    AutoTile fromAT;
    AutoTile toAT;
    float cost;

    public Transition(AutoTile fromCity, AutoTile toCity){
        this.fromAT = fromCity;
        this.toAT = toCity;
        cost = 1;
        //cost = Vector2.dst(fromCity.x, fromCity.y, toCity.x, toCity.y);
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public AutoTile getFromNode() {
        return fromAT;
    }

    @Override
    public AutoTile getToNode() {
        return toAT;
    }
}
