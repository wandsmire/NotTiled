package com.mirwanda.nottiled.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class ATHeuristic implements Heuristic<AutoTile> {

    @Override
    public float estimate(AutoTile currentCity, AutoTile goalCity) {
//        return Vector2.dst(currentCity.x, currentCity.y, goalCity.x, goalCity.y);
        return 9;
    }
}
