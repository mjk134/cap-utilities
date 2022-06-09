package me.mjk134.caputilities;

import me.x150.renderer.renderer.font.TTFFontRenderer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.function.Function;

public class CapUtilities implements ModInitializer {

    public static final String MOD_ID = "cap-utilities";
    public static final String MOD_NAME = "Cap Utilities";
    public static final String MOD_VERSION = "1.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Cap Utilities");
    }
}