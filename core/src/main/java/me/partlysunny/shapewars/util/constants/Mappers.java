package me.partlysunny.shapewars.util.constants;

import com.badlogic.ashley.core.ComponentMapper;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.item.components.ItemComponent;
import me.partlysunny.shapewars.item.components.WeaponComponent;
import me.partlysunny.shapewars.world.components.PlayerCameraFollowComponent;
import me.partlysunny.shapewars.world.components.TextureComponent;
import me.partlysunny.shapewars.world.components.ai.AiDodgeIgnoreComponent;
import me.partlysunny.shapewars.world.components.ai.EnemyAttackComponent;
import me.partlysunny.shapewars.world.components.ai.SteeringComponent;
import me.partlysunny.shapewars.world.components.collision.BulletDeleterComponent;
import me.partlysunny.shapewars.world.components.collision.RigidBodyComponent;
import me.partlysunny.shapewars.world.components.collision.TransformComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyMeleeDamageComponent;
import me.partlysunny.shapewars.world.components.mechanics.enemy.EnemyStateComponent;
import me.partlysunny.shapewars.world.components.movement.GroundFrictionComponent;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;
import me.partlysunny.shapewars.world.components.player.state.StateComponent;
import me.partlysunny.shapewars.world.components.render.AnimationComponent;
import me.partlysunny.shapewars.world.components.render.DeathEffectComponent;
import me.partlysunny.shapewars.world.components.render.TintComponent;

public final class Mappers {

    public static final ComponentMapper<RigidBodyComponent> bodyMapper = ComponentMapper.getFor(RigidBodyComponent.class);
    public static final ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<AiDodgeIgnoreComponent> dodgeIgnoreMapper = ComponentMapper.getFor(AiDodgeIgnoreComponent.class);
    public static final ComponentMapper<EnemyAttackComponent> enemyAttackMapper = ComponentMapper.getFor(EnemyAttackComponent.class);
    public static final ComponentMapper<SteeringComponent> steeringMapper = ComponentMapper.getFor(SteeringComponent.class);
    public static final ComponentMapper<BulletDeleterComponent> deletionMapper = ComponentMapper.getFor(BulletDeleterComponent.class);
    public static final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<GroundFrictionComponent> frictionMapper = ComponentMapper.getFor(GroundFrictionComponent.class);
    public static final ComponentMapper<StateComponent> playerStateMapper = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<PlayerControlComponent> controlMapper = ComponentMapper.getFor(PlayerControlComponent.class);
    public static final ComponentMapper<AnimationComponent> animationMapper = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<DeathEffectComponent> deathEffectMapper = ComponentMapper.getFor(DeathEffectComponent.class);
    public static final ComponentMapper<TintComponent> tintMapper = ComponentMapper.getFor(TintComponent.class);
    public static final ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<PlayerCameraFollowComponent> cameraFollowMapper = ComponentMapper.getFor(PlayerCameraFollowComponent.class);
    public static final ComponentMapper<BulletComponent> bulletMapper = ComponentMapper.getFor(BulletComponent.class);
    public static final ComponentMapper<WeaponComponent> weaponMapper = ComponentMapper.getFor(WeaponComponent.class);
    public static final ComponentMapper<ItemComponent> itemMapper = ComponentMapper.getFor(ItemComponent.class);
    public static final ComponentMapper<EnemyStateComponent> enemyStateMapper = ComponentMapper.getFor(EnemyStateComponent.class);
    public static final ComponentMapper<EnemyMeleeDamageComponent> enemyMeleeDamageMapper = ComponentMapper.getFor(EnemyMeleeDamageComponent.class);
}
