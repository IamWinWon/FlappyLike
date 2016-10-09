package ru.mrwinwon.backwards_game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Артём on 20.09.2016.
 */

public class Birdy {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation birdyAnimation;
    private Texture texture;
    private Sound flap;

    private Texture birdy;

    public  Birdy(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        birdy = new Texture("bird.png");
        texture = new Texture("birdanimation.png");
        birdyAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float udt){
        birdyAnimation.update(udt);
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);

        velocity.scl(udt);
        position.add(MOVEMENT * udt, velocity.y, 0);
        if(position.y < 0)
            position.y = 0;

        velocity.scl(1/udt);
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdyAnimation.getFrame();
    }

    public void jump(){
        velocity.y = 250;
        flap.play(0.6f);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
        flap.dispose();
    }
}
