package io.performia.mixins;

import io.performia.fixes.PerformiumChatComponentStyle;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatComponentStyle.class)
public abstract class MixinChatComponentStyle implements IChatComponent {

    private PerformiumChatComponentStyle performiumChatComponentStyle = new PerformiumChatComponentStyle((ChatComponentStyle) (Object) this);

    @Shadow
    public abstract ChatStyle getChatStyle();

    @Inject(method = "setChatStyle", at = @At("HEAD"),cancellable = true)
    public void setChatStyle(ChatStyle style, CallbackInfoReturnable<IChatComponent> ci) {
        performiumChatComponentStyle.invalidateCache();
    }


    @Inject(method = "getFormattedText", at = @At("HEAD"),cancellable = true)
    public void getFormatedTextHeader(CallbackInfoReturnable<String> string) {
        performiumChatComponentStyle.getFormatedTextHeader(string);
    }

    @Inject(method = "getFormattedText", at = @At("RETURN"),cancellable = true)
    public void getFormatedTextReturn(CallbackInfoReturnable<String> string) {
        performiumChatComponentStyle.getFormatedTextReturn(string);
    }

}
