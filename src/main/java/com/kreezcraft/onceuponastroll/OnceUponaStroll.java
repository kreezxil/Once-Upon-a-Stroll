package com.kreezcraft.onceuponastroll;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("onceuponastroll")
public class OnceUponaStroll {
    private static final Logger LOGGER = LogManager.getLogger();

    public OnceUponaStroll() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);

        StrollConfig.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        StrollConfig.load();
    }

    @Mod.EventBusSubscriber(modid = "onceuponastroll")
    public static class MakePath {
        private static void generatePath(Entity player) {
            World world = player.getEntityWorld();

            if (!world.isRemote && (Math.abs(player.getMotion().getX()) > 0.0D || Math.abs(player.getMotion().getY()) > 0.0D || Math.abs(player.getMotion().getZ()) > 0.0D)) {

                BlockPos entityLocation = new BlockPos(player.getPosX(), player.getPosY() + player.getYOffset(), player.getPosZ());
                BlockState state = world.getBlockState(entityLocation);
                Block block = state.getBlock();

                double random = Math.random() * StrollConfig.mob_upperLimit + StrollConfig.mob_lowerLimit;

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

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void makeMobPath(LivingEvent event) {
            if (event.getEntityLiving() != null && StrollConfig.pathMobs) {
                Entity player = event.getEntityLiving();
                generatePath(player);
            }
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void makePlayerPath(PlayerEvent event) {
            if (event.getPlayer() != null && StrollConfig.pathPlayers) {
                Entity player = event.getPlayer();
                generatePath(player);
            }
        }
    }
}
