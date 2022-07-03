package me.partlysunny.shapewars.world.systems.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import me.partlysunny.shapewars.util.constants.Mappers;
import me.partlysunny.shapewars.world.components.render.TextureComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;

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

    public TextureRenderingSystem(Batch batch) {
        // gets all entities with a TransformComponent and TextureComponent
        super(Family.all(TextureComponent.class, TransformComponent.class).get(), new ZComparator());

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
            TextureComponent tex = Mappers.textureMapper.get(entity);
            TransformComponent transform = Mappers.transformMapper.get(entity);
            Vector3 tint = null;
            float opacity = 0;
            if (Mappers.tintMapper.has(entity)) {
                TintComponent tintComponent = Mappers.tintMapper.get(entity);
                tint = tintComponent.tint();
                opacity = tintComponent.alpha();
            }

            if (tex.texture() == null || tex.isHidden()) {
                continue;
            }

            if (tint != null) {
                batch.setColor(new Color(tint.x, tint.y, tint.z, opacity));
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

            /*
            if (entity.getComponent(ItemComponent.class) != null) {
                System.out.println("x1 = " + x1);
                System.out.println("y1 = " + y1);
                System.out.println("width = " + width);
                System.out.println("height = " + height);
            }
             */
            batch.setColor(Color.WHITE);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
