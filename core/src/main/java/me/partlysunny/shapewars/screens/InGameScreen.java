package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.eskalon.commons.screen.ManagedScreen;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.particle.ParticleEffectManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.item.types.WeaponItem;
import me.partlysunny.shapewars.level.LevelManager;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.world.GameWorld;
import me.partlysunny.shapewars.world.components.player.PlayerAction;
import me.partlysunny.shapewars.world.components.player.PlayerInfo;
import me.partlysunny.shapewars.world.objects.EntityManager;
import me.partlysunny.shapewars.world.objects.enemy.EnemyManager;
import me.partlysunny.shapewars.world.objects.items.ItemManager;
import me.partlysunny.shapewars.world.objects.obstacle.ObstacleManager;
import me.partlysunny.shapewars.world.objects.obstacle.WallEntity;
import me.partlysunny.shapewars.world.objects.player.PlayerEntity;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.*;

public class InGameScreen extends ManagedScreen {

    public static final Vector2 cameraVelocity = new Vector2(0, 0);
    public static final OrthographicCamera camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
    public static final OrthographicCamera guiCamera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
    public static final Viewport viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera);
    public static final Viewport guiViewport = new ExtendViewport(guiCamera.viewportWidth, guiCamera.viewportHeight, guiCamera);
    public static GameWorld world;
    public static PlayerInfo playerInfo;
    public static LevelManager levelManager;
    private final ShapeWars game;
    private EntityManager entityManager;
    private float accumulator = 0;
    private Box2DDebugRenderer debugRenderer;
    private Stage stage;
    private Stage guiStage;

    public InGameScreen(ShapeWars game) {
        this.game = game;
        EnemyManager.init();
        ObstacleManager.init();
        stage = new Stage(viewport, game.batch());
        guiStage = new Stage(guiViewport, game.batch());
        InGameScreenGuiManager.init(guiStage);
        world = new GameWorld(stage);
        debugRenderer = new Box2DDebugRenderer();
        entityManager = new EntityManager(world.gameWorld());
        //Init basic player and wall
        InGameScreen.playerInfo = new PlayerInfo(entityManager.registerEntity(new PlayerEntity(), 0, 0), game);
        entityManager.registerEntity(new WallEntity(), 0, 0);
        //Create level manager (also will start level counter and spawn enemies)
        levelManager = new LevelManager(stage);
        playerInfo.equipment().setWeaponOne((WeaponItem) ItemManager.getItem("circleBlaster"));
        playerInfo.equipment().setWeaponTwo((WeaponItem) ItemManager.getItem("circlePummeler"));
    }

    @Override
    protected void create() {
        viewport.apply();
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
        InGameScreen s = this;
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (game.getScreenManager().getCurrentScreen().equals(s)) {
                    if (keycode == Input.Keys.SPACE) {
                        game.getScreenManager().pushScreen("paused", null);
                    }
                    if (keycode == playerInfo.keyMap().getKey(PlayerAction.WEAPON_SLOT_1)) {
                        playerInfo.equipment().setActiveWeaponSlot(0);
                    }
                    if (keycode == playerInfo.keyMap().getKey(PlayerAction.WEAPON_SLOT_2)) {
                        playerInfo.equipment().setActiveWeaponSlot(1);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void hide() {

    }

    public void render(float delta) {
        //Update viewport
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();
        //Clear screen with background color
        ScreenUtils.clear(GameInfo.BACKGROUND_COLOR);
        //Move camera
        camera.position.add(cameraVelocity.x / PPM, cameraVelocity.y / PPM, 0);
        //Ticking and logic
        doPhysicsStep(Gdx.graphics.getDeltaTime());
        stage.act(Gdx.graphics.getDeltaTime());
        GdxAI.getTimepiece().update(delta);
        levelManager.update(delta);
        InGameScreenGuiManager.update();
        VisualEffectManager.update(delta);
        //Rendering
        game.batch().enableBlending();
        game.batch().setProjectionMatrix(camera.combined);
        stage.draw();
        world.gameWorld().update(delta);
        //Draw UI
        guiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiViewport.apply();
        guiStage.draw();
        ParticleEffectManager.render(game.batch(), delta);
        //debugRenderer.render(world().physicsWorld(), camera.combined);
        //Process deletion
        LateRemover.process();
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
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
