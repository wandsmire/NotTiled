package com.mirwanda.nottiled.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class ATGraph implements IndexedGraph<AutoTile> {

    ATHeuristic myHeuristic = new ATHeuristic();
    Array<AutoTile> autotiles = new Array<>();
    Array<Transition> transitions = new Array<>();

    /** Map of Cities to Streets starting in that City. */
    ObjectMap<AutoTile, Array<Connection<AutoTile>>> ATMap = new ObjectMap<>();

    private int lastNodeIndex = 0;

    public void addAT(AutoTile myAT){
        myAT.index = lastNodeIndex;
        lastNodeIndex++;
        autotiles.add(myAT);
    }

    public AutoTile getAT(int ATName){
        for (int i=0;i<autotiles.size;i++) {
            AutoTile at = autotiles.get( i );
            if (at.name == ATName) {
                return at;
            }
        }
        return null;
    }

    public void connectAT(AutoTile fromAT, AutoTile toAT){
        Transition myTrans = new Transition(fromAT, toAT);
        if(!ATMap.containsKey(fromAT)){
            ATMap.put(fromAT, new Array<Connection<AutoTile>>());
        }
        ATMap.get(fromAT).add(myTrans);
        transitions.add(myTrans);
    }

    public GraphPath<AutoTile> findPath(AutoTile startAT, AutoTile goalAT){
        GraphPath<AutoTile> ATPath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startAT, goalAT, myHeuristic, ATPath);
        return ATPath;
    }

    @Override
    public int getIndex(AutoTile node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<AutoTile>> getConnections(AutoTile fromNode) {
        if(ATMap.containsKey(fromNode)){
            return ATMap.get(fromNode);
        }

        return new Array<>(0);
    }
}
