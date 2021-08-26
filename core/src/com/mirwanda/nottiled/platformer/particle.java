package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class particle extends Sprite {
    boolean complete=false;
    game mygame;
    float stateTime;
    public Animation<TextureRegion> anim;

    public particle(game mygame){
        this.mygame = mygame;
        stateTime=0f;
    }


    public void update(float delta){
        stateTime+=delta;
        if (!isComplete()){
            TextureRegion currentFrame = anim.getKeyFrame( stateTime, false );
            setRegion( currentFrame );
            setColor( 1,0,0,1-stateTime/anim.getAnimationDuration() );
            setSize( getRegionWidth() /mygame.scale, getRegionHeight() /mygame.scale );
            setOriginCenter();

            if (anim.isAnimationFinished(stateTime)) complete = true;
        }

    }

    public boolean isComplete(){
        return complete;
    }
}
