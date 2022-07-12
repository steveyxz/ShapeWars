package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
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
import me.partlysunny.shapewars.effects.sound.MusicManager;
import me.partlysunny.shapewars.effects.visual.VisualEffectManager;
import me.partlysunny.shapewars.level.LevelManager;
import me.partlysunny.shapewars.player.InventoryMenuManager;
import me.partlysunny.shapewars.player.PlayerInfo;
import me.partlysunny.shapewars.player.PlayerKiller;
import me.partlysunny.shapewars.player.item.items.ItemManager;
import me.partlysunny.shapewars.player.item.types.WeaponItem;
import me.partlysunny.shapewars.util.classes.RandomList;
import me.partlysunny.shapewars.util.constants.GameInfo;
import me.partlysunny.shapewars.util.utilities.LateRemover;
import me.partlysunny.shapewars.util.utilities.Util;
import me.partlysunny.shapewars.world.GameWorld;
import me.partlysunny.shapewars.world.components.player.PlayerAction;
import me.partlysunny.shapewars.world.components.render.ActorComponent;
import me.partlysunny.shapewars.world.objects.EntityManager;
import me.partlysunny.shapewars.world.objects.enemy.EnemyManager;
import me.partlysunny.shapewars.world.objects.obstacle.ObstacleManager;
import me.partlysunny.shapewars.world.objects.player.PlayerEntity;
import me.partlysunny.shapewars.world.systems.render.ActorUpdatingSystem;
import me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem;

import static me.partlysunny.shapewars.world.systems.render.TextureRenderingSystem.*;

public class InGameScreen extends ManagedScreen {

    public static final Vector2 cameraVelocity = new Vector2(0, 0);
    public static final OrthographicCamera camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
    public static final OrthographicCamera guiCamera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
    public static final Viewport viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera);
    public static final Viewport guiViewport = new ExtendViewport(guiCamera.viewportWidth, guiCamera.viewportHeight, guiCamera);
    public static final ScreenGuiManager guiManager = new ScreenGuiManager();
    public static GameWorld world;
    public static PlayerInfo playerInfo;
    public static LevelManager levelManager;
    private final ShapeWars game;
    private final GameMusicSwitcher switcher = new GameMusicSwitcher();
    private final Box2DDebugRenderer debugRenderer;
    private final Stage stage;
    private final Stage guiStage;
    private float accumulator = 0;

    public InGameScreen(ShapeWars game) {
        this.game = game;
        EnemyManager.init();
        ObstacleManager.init();
        stage = new Stage(viewport, game.batch());
        guiStage = new Stage(guiViewport, game.batch());
        guiManager.init(guiStage);
        world = new GameWorld(stage);
        debugRenderer = new Box2DDebugRenderer();
        EntityManager entityManager = new EntityManager(world.gameWorld());
        //Init basic player and wall
        InGameScreen.playerInfo = new PlayerInfo(entityManager.registerEntity(new PlayerEntity(), 0, 0));
        //Create level manager (also will start level counter and spawn enemies)
        levelManager = new LevelManager();
        playerInfo.equipment().setWeaponOne((WeaponItem) ItemManager.getItem("circleBlaster"));
        playerInfo.equipment().unlockWeapon("circleBlaster");
        InventoryMenuManager.init(stage);
    }

    @Override
    protected void create() {
    }

    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(guiStage, stage));
        InGameScreen s = this;
        playerInfo.initGui();
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (game.getScreenManager().getCurrentScreen().equals(s)) {
                    if (keycode == Input.Keys.SPACE || keycode == Input.Keys.ESCAPE) {
                        game.getScreenManager().pushScreen("paused", null);
                    }
                    if (keycode == playerInfo.keyMap().getKey(PlayerAction.WEAPON_SLOT_1)) {
                        playerInfo.equipment().setActiveWeaponSlot(0);
                    }
                    if (keycode == playerInfo.keyMap().getKey(PlayerAction.WEAPON_SLOT_2)) {
                        playerInfo.equipment().setActiveWeaponSlot(1);
                    }
                }
                return false;
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

        if (!InventoryMenuManager.isOpen()) {
            //Move camera
            camera.position.add(cameraVelocity.x / PPM, cameraVelocity.y / PPM, 0);
            //Ticking and logic
            doPhysicsStep(Gdx.graphics.getDeltaTime());
            //Update AI
            GdxAI.getTimepiece().update(delta);
            //Update level (check level up, countdowns)
            levelManager.update(delta);
            //Visual effects (damage, swing)
            VisualEffectManager.update(delta);
            //Update equipment (ammo recharge)
            playerInfo.equipment().update(delta);
            //Act out the current stage
            stage.act(Gdx.graphics.getDeltaTime());
            world.gameWorld().update(delta);
        }

        //Process late killers / removers (so that they don't collide with the physics step)
        LateRemover.process();
        PlayerKiller.update(game);

        //Rendering
        game.batch().enableBlending();
        game.batch().setProjectionMatrix(camera.combined);
        stage.draw();
        if (InventoryMenuManager.isOpen()) {
            world.gameWorld().getSystem(TextureRenderingSystem.class).update(delta);
            world.gameWorld().getSystem(ActorUpdatingSystem.class).update(delta);
        }
        //Draw UI
        guiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiViewport.apply();
        //Update UI components
        guiManager.update();
        guiStage.act(delta);
        ParticleEffectManager.render(game.batch(), delta);
        guiStage.draw();
        //Music
        switcher.update(delta);
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
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public Stage stage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private static final class GameMusicSwitcher {

        private static final int MAX_DELAY = 10;
        private static final int MIN_DELAY = 4;
        private final RandomList<String> possibleTracks = new RandomList<>();
        private float delay = -1;

        public GameMusicSwitcher() {
            possibleTracks.add("squaresAndCircles", 2);
            possibleTracks.add("triangleDash", 2);
        }

        public void update(float delta) {
            if (!MusicManager.isPlaying()) {
                if (delay < 0) {
                    delay = (float) Util.getRandomBetween(MIN_DELAY, MAX_DELAY);
                }
                delay -= delta;
                if (delay < 0) {
                    delay = -1;
                    String track = possibleTracks.raffle();
                    MusicManager.play(track, false, 1f);
                }
            }
        }

    }
}
