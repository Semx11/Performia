package io.performia.mixins;

import io.performia.fixes.PerformiumChatStyle;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatStyle.class)
public abstract class MixinChatStyle {

    @Shadow
    private ChatStyle parentStyle;


    private PerformiumChatStyle performiumChatStyle = new PerformiumChatStyle((ChatStyle) (Object) (this));

    /**
     * @author Sk1er
     */
    @Inject(method = "getFormattingCode", at = @At("HEAD"), cancellable = true)
    public void getFormattingCode(CallbackInfoReturnable<String> info) {
        info.setReturnValue(performiumChatStyle.getFormattingCode(parentStyle));
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "setColor", at = @At("HEAD"))
    public void setColor(EnumChatFormatting color, CallbackInfoReturnable<ChatStyle> info) {
        performiumChatStyle.resetCache();
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "setBold", at = @At("HEAD"))
    public void setBold(Boolean boldIn, CallbackInfoReturnable<ChatStyle> callbackInfoReturnable) {
        performiumChatStyle.resetCache();
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "setItalic", at = @At("HEAD"))
    public void setItalic(Boolean boldIn, CallbackInfoReturnable<ChatStyle> callbackInfoReturnable) {
        performiumChatStyle.resetCache();
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "setStrikethrough", at = @At("HEAD"))
    public void setStrikethrough(Boolean boldIn, CallbackInfoReturnable<ChatStyle> callbackInfoReturnable) {
        performiumChatStyle.resetCache();
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "setUnderlined", at = @At("HEAD"))
    public void setUnderlined(Boolean boldIn, CallbackInfoReturnable<ChatStyle> callbackInfoReturnable) {
        performiumChatStyle.resetCache();
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "setObfuscated", at = @At("HEAD"))
    public void setObfuscated(Boolean boldIn, CallbackInfoReturnable<ChatStyle> callbackInfoReturnable) {
        performiumChatStyle.resetCache();
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "setChatClickEvent", at = @At("HEAD"))
    public void setChatClickEvent(ClickEvent boldIn, CallbackInfoReturnable<ChatStyle> callbackInfoReturnable) {
        performiumChatStyle.resetCache();
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "setChatHoverEvent", at = @At("HEAD"))
    public void setChatHoverEvent(HoverEvent boldIn, CallbackInfoReturnable<ChatStyle> callbackInfoReturnable) {
        performiumChatStyle.resetCache();
    }

    /**
     * @author Sk1er
     */

    @Inject(method = "setInsertion", at = @At("HEAD"))
    public void setChatHoverEvent(String boldIn, CallbackInfoReturnable<ChatStyle> callbackInfoReturnable) {
        performiumChatStyle.resetCache();
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "setParentStyle", at = @At("HEAD"))
    public void setChatHoverEvent(ChatStyle boldIn, CallbackInfoReturnable<ChatStyle> callbackInfoReturnable) {
        performiumChatStyle.resetCache();
    }
}
