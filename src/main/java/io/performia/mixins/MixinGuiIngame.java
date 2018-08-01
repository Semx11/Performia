package io.performia.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {
    @Shadow
    public abstract FontRenderer getFontRenderer();

    @Inject(method = "renderTooltip", at = @At(value = "HEAD"))
    private void renderGameOverlay(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        String fps = "FPS: " + Minecraft.getDebugFPS();
        getFontRenderer().drawString(fps, 1, 1, Color.WHITE.getRGB());
    }
}
