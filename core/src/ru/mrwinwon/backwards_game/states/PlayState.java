package ru.mrwinwon.backwards_game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ru.mrwinwon.backwards_game.BackwardsBird;
import ru.mrwinwon.backwards_game.sprites.Birdy;
import ru.mrwinwon.backwards_game.sprites.Pipe;

/**
 * Created by Артём on 18.09.2016.
 */

public class PlayState extends State {
    private static final int PIPE_SPACING = 125;
    private static final int PIPE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -30;

    private Birdy birdy;
    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private Array<Pipe> pipes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        birdy = new Birdy(50, 100);
        camera.setToOrtho(false, BackwardsBird.WIDTH / 2, BackwardsBird.HEIGHT / 2);
        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        pipes = new Array<Pipe>();

        for(int i = 1; i < PIPE_COUNT; i++){
            pipes.add(new Pipe(i * (PIPE_SPACING + Pipe.PIPE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            birdy.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        birdy.update(dt);
        camera.position.x = birdy.getPosition().x + 80;

        /** Менять тут позицию! */
        for (int i = 0; i < pipes.size; i++){
            Pipe pipe = pipes.get(i);

            if(camera.position.x - (camera.viewportWidth / 2) > pipe.getPosTopPipe().x + pipe.getTopPipe().getWidth()){
                pipe.reposition(pipe.getPosTopPipe().x + ((Pipe.PIPE_WIDTH + PIPE_SPACING) * PIPE_COUNT));
            }

            if (pipe.collides(birdy.getBounds()))
                gsm.set(new PlayState(gsm));
        }

        if(birdy.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gsm.set(new PlayState(gsm));

        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        spriteBatch.draw(birdy.getTexture(), birdy.getPosition().x, birdy.getPosition().y);
        for (Pipe pipe : pipes){
            spriteBatch.draw(pipe.getTopPipe(), pipe.getPosTopPipe().x, pipe.getPosTopPipe().y);
            spriteBatch.draw(pipe.getBottomPipe(), pipe.getPosBotPipe().x, pipe.getPosBotPipe().y);
        }

        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        spriteBatch.end();
    }

    public void dispose() {
        background.dispose();
        ground.dispose();
        birdy.dispose();
        for (Pipe pipe : pipes)
            pipe.dispose();
        System.out.println("Play State Disposed!");
    }

    private void updateGround(){
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }
}

