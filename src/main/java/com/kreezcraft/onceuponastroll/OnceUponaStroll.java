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
public class OnceUponaStroll
{
    private static final Logger LOGGER = LogManager.getLogger();

    public OnceUponaStroll() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class MakePath {
        @SubscribeEvent
        public static void makePath(LivingEvent.LivingUpdateEvent event) {
            if (event.getEntity() != null && event.getEntity() instanceof net.minecraft.entity.player.PlayerEntity) {
                Entity player = event.getEntity();
                BlockPos entityLocation = new BlockPos(player.posX,player.posY+player.getYOffset(), player.posZ);
                World world = player.getEntityWorld();
                BlockState state = world.getBlockState(entityLocation);
                Block block = state.getBlock();

                double random =  Math.random() * 1000.0D + 1.0D;

                LOGGER.info("Attempting to make a path");
                LOGGER.info("Random is ",random,"\n");

                if (!world.isRemote && (Math.abs(player.getMotion().getX()) > 0.0D || Math.abs(player.getMotion().getY()) > 0.0D || Math.abs(player.getMotion().getZ()) > 0.0D)) {

                    if (block == Blocks.GRASS_BLOCK)
                    {
                        if (random < 50.0D) {
                            world.setBlockState(entityLocation, Blocks.DIRT.getDefaultState());
                            return;
                        }
                    }

                    if (block == Blocks.DIRT)
                    {
                        if (random < 10.0D) {
                            world.setBlockState(entityLocation, Blocks.GRASS_PATH.getDefaultState());

                            return;
                        }
                    }
                    if (block == Blocks.GRASS_PATH)
                    {
                        if (random < 1.0D) {
                            world.setBlockState(entityLocation, Blocks.GRAVEL.getDefaultState());

                            return;
                        }
                    }
                    if (block == Blocks.GRAVEL)
                    {
                        if (random < 10.0D) {
                            world.setBlockState(entityLocation, Blocks.STONE.getDefaultState());

                            return;
                        }
                    }
                    if (block == Blocks.STONE)
                    {
                        if (random < 10.0D) {
                            world.setBlockState(entityLocation, Blocks.STONE_BRICKS.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}
