package io.performia.mixins;

import io.performia.Settings;
import io.performia.events.EventBus;
import io.performia.events.WorldChangeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {


    @Shadow
    @Final
    public Profiler mcProfiler;

    @Inject(method = "checkGLError", at = @At("HEAD"), cancellable = true)
    public void checkGLError(String message, CallbackInfo info) {
        if (Settings.DISABLE_GL_ERROR_CHECKING)
            info.cancel();
    }

    @Inject(method = "runTick", at = @At("RETURN"))
    private void runTick(CallbackInfo ci) {
        mcProfiler.startSection("hyperium_tick");
        EventBus.INSTANCE.post(new io.performia.events.TickEvent());
        mcProfiler.endSection();
    }

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;)V", at = @At("HEAD"))
    private void loadWorld(WorldClient worldClient, CallbackInfo ci) {
        EventBus.INSTANCE.post(new WorldChangeEvent());
    }

}
