package me.partlysunny.shapewars.util.constants;

import com.badlogic.ashley.core.ComponentMapper;
import me.partlysunny.shapewars.bullets.BulletComponent;
import me.partlysunny.shapewars.world.components.ai.AiDodgeIgnoreComponent;
import me.partlysunny.shapewars.world.components.ai.SteeringComponent;
import me.partlysunny.shapewars.world.components.ai.WanderComponent;
import me.partlysunny.shapewars.world.components.collision.*;
import me.partlysunny.shapewars.world.components.enemy.EnemyAttackComponent;
import me.partlysunny.shapewars.world.components.enemy.EnemyMeleeDamageComponent;
import me.partlysunny.shapewars.world.components.enemy.EnemyStateComponent;
import me.partlysunny.shapewars.world.components.enemy.loot.LootComponent;
import me.partlysunny.shapewars.world.components.enemy.loot.LootItemComponent;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.coin.CoinComponent;
import me.partlysunny.shapewars.world.components.enemy.loot.entry.item.UtilityComponent;
import me.partlysunny.shapewars.world.components.mechanics.BombComponent;
import me.partlysunny.shapewars.world.components.mechanics.HealthComponent;
import me.partlysunny.shapewars.world.components.movement.GroundFrictionComponent;
import me.partlysunny.shapewars.world.components.player.PlayerCameraFollowComponent;
import me.partlysunny.shapewars.world.components.player.PlayerControlComponent;
import me.partlysunny.shapewars.world.components.player.PlayerMeleeAttackComponent;
import me.partlysunny.shapewars.world.components.player.item.ItemComponent;
import me.partlysunny.shapewars.world.components.player.item.WeaponComponent;
import me.partlysunny.shapewars.world.components.player.state.StateComponent;
import me.partlysunny.shapewars.world.components.render.*;

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
    public static final ComponentMapper<PlayerMeleeAttackComponent> playerMeleeAttackMapper = ComponentMapper.getFor(PlayerMeleeAttackComponent.class);
    public static final ComponentMapper<ObstacleComponent> obstacleMapper = ComponentMapper.getFor(ObstacleComponent.class);
    public static final ComponentMapper<LootComponent> lootMapper = ComponentMapper.getFor(LootComponent.class);
    public static final ComponentMapper<LootItemComponent> lootItemMapper = ComponentMapper.getFor(LootItemComponent.class);
    public static final ComponentMapper<CoinComponent> coinMapper = ComponentMapper.getFor(CoinComponent.class);
    public static final ComponentMapper<UtilityComponent> utilityMapper = ComponentMapper.getFor(UtilityComponent.class);
    public static final ComponentMapper<BombComponent> bombMapper = ComponentMapper.getFor(BombComponent.class);
    public static final ComponentMapper<DeletionListenerComponent> deleteListenerMapper = ComponentMapper.getFor(DeletionListenerComponent.class);
    public static final ComponentMapper<WanderComponent> wanderMapper = ComponentMapper.getFor(WanderComponent.class);
    public static final ComponentMapper<ActorComponent> actorMapper = ComponentMapper.getFor(ActorComponent.class);
}
