package com.kreezcraft.onceuponastroll;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("onceuponastroll")
public class OnceUponaStroll {
    private static final Logger LOGGER = LogManager.getLogger();

    public OnceUponaStroll() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, MakePath::makePlayerPath);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, MakePath::makeMobPath);
        StrollConfig.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        StrollConfig.load();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class MakePath {
        private static void generatePath(Entity player) {
            BlockPos entityLocation = new BlockPos(player.getPosition().getX(), player.getPosition().getY() + player.getYOffset(), player.getPosition().getZ());
            World world = player.getEntityWorld();
            BlockState state = world.getBlockState(entityLocation);
            Block block = state.getBlock();

            double random = Math.random() * StrollConfig.mob_upperLimit + StrollConfig.mob_lowerLimit;

            if (!world.isRemote && (Math.abs(player.getMotion().getX()) > 0.0D || Math.abs(player.getMotion().getY()) > 0.0D || Math.abs(player.getMotion().getZ()) > 0.0D)) {


                if (block == Blocks.GRASS_BLOCK) {
                    if (random < StrollConfig.mob_grass2dirt) {
                        world.setBlockState(entityLocation, Blocks.DIRT.getDefaultState());
                        return;
                    }
                }
                if (StrollConfig.fullPaths) {
                    if (block == Blocks.DIRT) {
                        if (random < StrollConfig.mob_dirt2path) {
                            world.setBlockState(entityLocation, Blocks.GRASS_PATH.getDefaultState());

                            return;
                        }
                    }
                    if (block == Blocks.GRASS_PATH) {
                        if (random < StrollConfig.mob_path2gravel) {
                            world.setBlockState(entityLocation, Blocks.GRAVEL.getDefaultState());

                            return;
                        }
                    }
                    if (block == Blocks.GRAVEL) {
                        if (random < StrollConfig.mob_gravel2stone) {
                            world.setBlockState(entityLocation, Blocks.STONE.getDefaultState());

                            return;
                        }
                    }
                    if (block == Blocks.STONE) {
                        if (random < StrollConfig.mob_stone2stonebricks) {
                            world.setBlockState(entityLocation, Blocks.STONE_BRICKS.getDefaultState());
                        }
                    }

                }
            }

        }

        @SubscribeEvent
        public static void makeMobPath(LivingEvent event) {

            if (event.getEntityLiving() != null && StrollConfig.pathMobs) {
                Entity player = event.getEntityLiving();
                generatePath(player);
            }
        }

        @SubscribeEvent
        public static void makePlayerPath(PlayerEvent event) {
            if (event.getPlayer() != null && StrollConfig.pathPlayers) {
                Entity player = event.getPlayer();
                generatePath(player);
            }
        }
    }
}
