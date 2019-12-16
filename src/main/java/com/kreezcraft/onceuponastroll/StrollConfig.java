package com.kreezcraft.onceuponastroll;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;


public class StrollConfig {
    private static ForgeConfigSpec.ConfigValue<Boolean> v_pathPlayers;
    private static ForgeConfigSpec.ConfigValue<Boolean> v_pathMobs;
    private static ForgeConfigSpec.ConfigValue<Boolean> v_fullPaths;

    private static ForgeConfigSpec.ConfigValue<Double> v_player_upperLimit;
    private static ForgeConfigSpec.ConfigValue<Double> v_player_lowerLimit;
    private static ForgeConfigSpec.ConfigValue<Double> v_player_grass2dirt;
    private static ForgeConfigSpec.ConfigValue<Double> v_player_dirt2path;
    private static ForgeConfigSpec.ConfigValue<Double> v_player_path2gravel;
    private static ForgeConfigSpec.ConfigValue<Double> v_player_gravel2stone;
    private static ForgeConfigSpec.ConfigValue<Double> v_player_stone2stonebricks;

    private static ForgeConfigSpec.ConfigValue<Double> v_mob_upperLimit;
    private static ForgeConfigSpec.ConfigValue<Double> v_mob_lowerLimit;
    private static ForgeConfigSpec.ConfigValue<Double> v_mob_grass2dirt;
    private static ForgeConfigSpec.ConfigValue<Double> v_mob_dirt2path;
    private static ForgeConfigSpec.ConfigValue<Double> v_mob_path2gravel;
    private static ForgeConfigSpec.ConfigValue<Double> v_mob_gravel2stone;
    private static ForgeConfigSpec.ConfigValue<Double> v_mob_stone2stonebricks;

    public static boolean pathPlayers = true;
    public static boolean pathMobs = false;
    public static boolean fullPaths = false;

    public static double player_upperLimit =1000.0D;
    public static double player_lowerLimit =1.0D;
    public static double player_grass2dirt =50.0D;
    public static double player_dirt2path =10.0D;
    public static double player_path2gravel= 1.0D;
    public static double player_gravel2stone= 10.0D;
    public static double player_stone2stonebricks =10.0D;

    public static double mob_upperLimit =1000.0D;
    public static double mob_lowerLimit =1.0D;
    public static double mob_grass2dirt =50.0D;
    public static double mob_dirt2path =10.0D;
    public static double mob_path2gravel= 1.0D;
    public static double mob_gravel2stone= 10.0D;
    public static double mob_stone2stonebricks =10.0D;

    public static void init() {
        Pair<Loader, ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(Loader::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (ForgeConfigSpec)specPair.getRight());
    }

    public static void load() {
        pathPlayers = v_pathPlayers.get();
        pathMobs = v_pathMobs.get();
        fullPaths = v_fullPaths.get();

        player_upperLimit = v_player_upperLimit.get();
        player_lowerLimit = v_player_lowerLimit.get();

        player_grass2dirt = v_player_grass2dirt.get();
        player_dirt2path = v_player_dirt2path.get();
        player_path2gravel = v_player_path2gravel.get();
        player_gravel2stone = v_player_gravel2stone.get();
        player_stone2stonebricks = v_player_stone2stonebricks.get();

        mob_upperLimit = v_mob_upperLimit.get();
        mob_lowerLimit = v_mob_lowerLimit.get();

        mob_grass2dirt = v_mob_grass2dirt.get();
        mob_dirt2path = v_mob_dirt2path.get();
        mob_path2gravel = v_mob_path2gravel.get();
        mob_gravel2stone = v_mob_gravel2stone.get();
        mob_stone2stonebricks = v_mob_stone2stonebricks.get();
    }

    static class Loader
    {
        public Loader(ForgeConfigSpec.Builder builder) {
            builder.push("General Booleans"); //category
            v_pathPlayers = (ForgeConfigSpec.ConfigValue<Boolean>)builder.comment("Should player paths render?").define("v_pathPlayers",StrollConfig.pathPlayers);
            v_pathMobs = (ForgeConfigSpec.ConfigValue<Boolean>)builder.comment("Should mob paths render? This can lag your world").define("v_pathMobs",StrollConfig.pathMobs);
            v_fullPaths = (ForgeConfigSpec.ConfigValue<Boolean>)builder.comment("Should paths be more than dirt and thus permanent") .define("v_fullPaths",StrollConfig.fullPaths);
            builder.pop();

            builder.push("Player Paths");
            v_player_upperLimit = builder.comment("Upper bound for the randomizer default: 1000.0D").define("v_player_upperLimit",StrollConfig.player_upperLimit);
            v_player_lowerLimit= builder.comment("The lower bound for the randomizer default: 1.0D").define("v_player_lowerLimit",StrollConfig.player_lowerLimit);
            builder.pop();

            builder.push("Chance versus the Player's upper and lower bounds that the following happens");
            v_player_grass2dirt= builder.comment("grass becomes dirt default: 50.0D").define("v_player_grass2dirt",StrollConfig.player_grass2dirt);
            v_player_dirt2path= builder.comment("dirt becomes grass path default: 10.0D").define("v_player_dirt2path",StrollConfig.player_dirt2path);
            v_player_path2gravel= builder.comment("grass path becomes gravel default: 1.0D").define("v_player_path2gravel",StrollConfig.player_path2gravel);
            v_player_gravel2stone= builder.comment("gravel becomes stone default: 10.0D").define("v_player_gravel2stone",StrollConfig.player_gravel2stone);
            v_player_stone2stonebricks= builder.comment("stone becomes stone bricks default: 10.0D").define("v_player_stone2stonebricks",StrollConfig.player_stone2stonebricks);
            builder.pop();

            builder.push("Mob Paths");
            v_mob_upperLimit =  builder.comment("Upper bound for the randomizer default: 1000.0D").define("v_mob_upperLimit",StrollConfig.mob_upperLimit);
            v_mob_lowerLimit= builder.comment("The lower bound for the randomizer default: 1.0D").define("v_mob_lowerLimit",StrollConfig.mob_lowerLimit);
            builder.pop();

            builder.push("Chance versus the Mob's upper and lower bounds that the following happens");
            v_mob_grass2dirt= builder.comment("grass becomes dirt default: 50.0D").define("v_mob_grass2dirt",StrollConfig.mob_grass2dirt);
            v_mob_dirt2path= builder.comment("dirt becomes grass path default: 10.0D").define("v_mob_dirt2path",StrollConfig.mob_dirt2path);
            v_mob_path2gravel= builder.comment("grass path becomes gravel default: 1.0D").define("v_mob_path2gravel",StrollConfig.mob_path2gravel);
            v_mob_gravel2stone= builder.comment("gravel becomes stone default: 10.0D").define("v_mob_gravel2stone",StrollConfig.mob_gravel2stone);
            v_mob_stone2stonebricks= builder.comment("stone becomes stone bricks default: 10.0D").define("v_mob_stone2stonebricks",StrollConfig.mob_stone2stonebricks);
            builder.pop();
        }
    }
}
