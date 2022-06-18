package me.partlysunny.shapewars.world.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;

import java.util.Comparator;

import static me.partlysunny.shapewars.screens.InGameScreen.camera;

public class TextureRenderingSystem extends SortedIteratingSystem {

    public static final float PPM = 8f;

    public static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth() / PPM;
    public static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight() / PPM;

    public static final float PIXELS_TO_METRES = 1.0f / PPM;

    private static final Vector2 meterDimensions = new Vector2();
    private static final Vector2 pixelDimensions = new Vector2();
    private final Batch batch; // a reference to our spritebatch
    private final Array<Entity> renderQueue; // an array used to allow sorting of images allowing us to draw images on top of each other
    private final Comparator<Entity> comparator = new ZComparator(); // a comparator to sort images based on the z position of the transfromComponent
    private final ComponentMapper<TextureComponent> textureMapper;
    private final ComponentMapper<TransformComponent> transformMapper;
    public TextureRenderingSystem(Batch batch) {
        // gets all entities with a TransformComponent and TextureComponent
        super(Family.all(TextureComponent.class, TransformComponent.class).get(), new ZComparator());

        //creates out componentMappers
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        // create the array for sorting entities
        renderQueue = new Array<>();

        this.batch = batch;  // set our batch to the one supplied in constructor

        // set up the camera to match our screen size
        camera.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);
    }

    public static Vector2 getScreenSizeInMeters() {
        meterDimensions.set(Gdx.graphics.getWidth() * PIXELS_TO_METRES,
                Gdx.graphics.getHeight() * PIXELS_TO_METRES);
        return meterDimensions;
    }

    public static Vector2 getScreenSizeInPixels() {
        pixelDimensions.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return pixelDimensions;
    }

    public static float pixelsToMeters(float pixelValue) {
        return pixelValue * PIXELS_TO_METRES;
    }

    public static float metersToPixels(float meterValue) {
        return meterValue / PIXELS_TO_METRES;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // sort the renderQueue based on z index
        renderQueue.sort(comparator);

        // update camera and sprite batch
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        batch.begin();

        // loop through each entity in our render queue
        for (Entity entity : renderQueue) {
            TextureComponent tex = textureMapper.get(entity);
            TransformComponent transform = transformMapper.get(entity);

            if (tex.texture() == null || tex.isHidden()) {
                continue;
            }


            float width = tex.texture().getRegionWidth();
            float height = tex.texture().getRegionHeight();

            float originX = width / 2f;
            float originY = height / 2f;

            float x = transform.position.x;
            float y = transform.position.y;
            float x1 = x - originX;
            float y1 = y - originY;

            float rotation = transform.rotation;
            batch.draw(tex.texture(),
                    x1, y1,
                    originX, originY,
                    width, height,
                    transform.scale.x / tex.texture().getRegionWidth(), transform.scale.y / tex.texture().getRegionHeight(),
                    rotation * MathUtils.radiansToDegrees);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
