package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.GameInfo;
import me.partlysunny.shapewars.world.GameWorld;
import me.partlysunny.shapewars.world.objects.EntityManager;
import me.partlysunny.shapewars.world.objects.obstacle.RockEntity;
import me.partlysunny.shapewars.world.objects.obstacle.WallEntity;
import me.partlysunny.shapewars.world.objects.player.PlayerEntity;

import static me.partlysunny.shapewars.world.systems.TextureRenderingSystem.*;

public class InGameScreen extends ScreenAdapter {

    public static final Vector2 cameraVelocity = new Vector2(0, 0);
    public static GameWorld world;
    private final ShapeWars game;
    private EntityManager entityManager;
    private float accumulator = 0;
    private Box2DDebugRenderer debugRenderer;
    private Stage stage;
    public static final OrthographicCamera camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT); // a reference to our camera
    public static final Viewport viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera);

    public InGameScreen(ShapeWars game) {
        this.game = game;
        InGameScreen s = this;

        stage = new Stage(viewport, game.batch());
        world = new GameWorld(stage);
        debugRenderer = new Box2DDebugRenderer();
        entityManager = new EntityManager(world.gameWorld());
        //Init player and wall
        entityManager.registerEntity(new PlayerEntity());
        entityManager.registerEntity(new WallEntity());
        for (int i = 0; i < 50; i++) {
            entityManager.registerEntity(new RockEntity());
        }
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE && game.getScreen().equals(s)) {
                    game.setScreen(Screens.pausedScreen);
                }
                return true;
            }
        });
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        ScreenUtils.clear(GameInfo.BACKGROUND_COLOR);
        //Move camera
        camera.position.add(cameraVelocity.x / PPM, cameraVelocity.y / PPM, 0);
        game.batch().enableBlending();
        game.batch().setProjectionMatrix(camera.combined);
        doPhysicsStep(Gdx.graphics.getDeltaTime());
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        world.gameWorld().update(delta);
        //debugRenderer.render(world().physicsWorld(), camera.combined);
    }

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= GameInfo.TIME_STEP) {
            InGameScreen.world.physicsWorld().step(GameInfo.TIME_STEP, GameInfo.VELOCITY_ITERATIONS, GameInfo.POSITION_ITERATIONS);
            accumulator -= GameInfo.TIME_STEP;
        }
    }

    public GameWorld world() {
        return world;
    }

    @Override
    public void resize (int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
