// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: playerSerialize.proto

package me.partlysunny.shapewars.proto;

public interface PlayerInfoOrBuilder extends
        // @@protoc_insertion_point(interface_extends:shapewars.PlayerInfo)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional float inGameTime = 1;</code>
     *
     * @return Whether the inGameTime field is set.
     */
    boolean hasInGameTime();

    /**
     * <code>optional float inGameTime = 1;</code>
     *
     * @return The inGameTime.
     */
    float getInGameTime();

    /**
     * <code>optional float maxHealth = 2;</code>
     *
     * @return Whether the maxHealth field is set.
     */
    boolean hasMaxHealth();

    /**
     * <code>optional float maxHealth = 2;</code>
     *
     * @return The maxHealth.
     */
    float getMaxHealth();

    /**
     * <code>optional float health = 3;</code>
     *
     * @return Whether the health field is set.
     */
    boolean hasHealth();

    /**
     * <code>optional float health = 3;</code>
     *
     * @return The health.
     */
    float getHealth();

    /**
     * <code>optional int32 money = 4;</code>
     *
     * @return Whether the money field is set.
     */
    boolean hasMoney();

    /**
     * <code>optional int32 money = 4;</code>
     *
     * @return The money.
     */
    int getMoney();

    /**
     * <code>optional int32 level = 5;</code>
     *
     * @return Whether the level field is set.
     */
    boolean hasLevel();

    /**
     * <code>optional int32 level = 5;</code>
     *
     * @return The level.
     */
    int getLevel();

    /**
     * <code>optional .shapewars.PlayerInfo.AmmoManager ammoManager = 6;</code>
     *
     * @return Whether the ammoManager field is set.
     */
    boolean hasAmmoManager();

    /**
     * <code>optional .shapewars.PlayerInfo.AmmoManager ammoManager = 6;</code>
     *
     * @return The ammoManager.
     */
    me.partlysunny.shapewars.proto.PlayerInfo.AmmoManager getAmmoManager();

    /**
     * <code>optional .shapewars.PlayerInfo.AmmoManager ammoManager = 6;</code>
     */
    me.partlysunny.shapewars.proto.PlayerInfo.AmmoManagerOrBuilder getAmmoManagerOrBuilder();

    /**
     * <code>optional .shapewars.PlayerInfo.PlayerEquipment equipment = 7;</code>
     *
     * @return Whether the equipment field is set.
     */
    boolean hasEquipment();

    /**
     * <code>optional .shapewars.PlayerInfo.PlayerEquipment equipment = 7;</code>
     *
     * @return The equipment.
     */
    me.partlysunny.shapewars.proto.PlayerInfo.PlayerEquipment getEquipment();

    /**
     * <code>optional .shapewars.PlayerInfo.PlayerEquipment equipment = 7;</code>
     */
    me.partlysunny.shapewars.proto.PlayerInfo.PlayerEquipmentOrBuilder getEquipmentOrBuilder();
}
