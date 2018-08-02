/*
 *     Copyright (C) 2018  Hyperium <https://hyperium.cc/>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.performia.mixins;

import io.performia.fixes.PerformiaTextureManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(TextureManager.class)
public abstract class MixinTextureManager {


    @Shadow
    @Final
    private static Logger logger;
    private PerformiaTextureManager performiaTextureManager = new PerformiaTextureManager((TextureManager) (Object) this);

    @Shadow
    private IResourceManager theResourceManager;

    @Shadow
    @Final
    private Map<String, Integer> mapTextureCounters;


    @Shadow
    @Final
    private List<ITickable> listTickables;

    /**
     * @author Sk1er
     */
    @Inject(method = "loadTexture", at = @At("HEAD"), cancellable = true)
    public void loadTexture(ResourceLocation textureLocation, ITextureObject textureObj, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(performiaTextureManager.loadTexture(textureLocation, textureObj, theResourceManager, logger));
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "loadTickableTexture", at = @At("HEAD"), cancellable = true)
    public void loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(performiaTextureManager.loadTickableTexture(textureLocation, textureObj, listTickables));
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "getDynamicTextureLocation", at = @At("HEAD"), cancellable = true)
    public void getDynamicTextureLocation(String name, DynamicTexture texture, CallbackInfoReturnable<ResourceLocation> info) {
        info.setReturnValue(performiaTextureManager.getDynamicTextureLocation(name, texture, mapTextureCounters));
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "onResourceManagerReload", at = @At("HEAD"), cancellable = true)
    public void onResourceManagerReload(IResourceManager resourceManager, CallbackInfo infoReturnable) {
        infoReturnable.cancel();
        performiaTextureManager.onResourceManagerReload(resourceManager);
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "bindTexture", at = @At("HEAD"), cancellable = true)
    public void bindTexture(ResourceLocation resource, CallbackInfo info) {
        info.cancel();
        performiaTextureManager.bindTexture(resource);
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    public void getTexture(ResourceLocation textureLocation, CallbackInfoReturnable<ITextureObject> info) {
        info.setReturnValue(performiaTextureManager.getTexture(textureLocation));
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo info) {
        info.cancel();
        performiaTextureManager.tick(listTickables);
    }

    /**
     * @author Sk1er
     */
    @Inject(method = "deleteTexture", at = @At("HEAD"), cancellable = true)
    public void deleteTexture(ResourceLocation textureLocation, CallbackInfo info) {
        info.cancel();
        performiaTextureManager.deleteTexture(textureLocation);
    }

}
