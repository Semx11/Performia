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
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
    @Overwrite
    public boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj) {
        return performiaTextureManager.loadTexture(textureLocation, textureObj, theResourceManager, logger);
    }

    /**
     * @author Sk1er
     */
    @Overwrite
    public boolean loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj) {
        return performiaTextureManager.loadTickableTexture(textureLocation, textureObj, listTickables);
    }

    /**
     * @author Sk1er
     */
    @Overwrite
    public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture) {
        return performiaTextureManager.getDynamicTextureLocation(name, texture, mapTextureCounters);
    }

    /**
     * @author Sk1er
     */
    @Overwrite
    public void onResourceManagerReload(IResourceManager resourceManager) {
        performiaTextureManager.onResourceManagerReload(resourceManager);
    }

    /**
     * @author Sk1er
     */
    @Overwrite
    public void bindTexture(ResourceLocation resource) {
        performiaTextureManager.bindTexture(resource);
    }

    /**
     * @author Sk1er
     */
    @Overwrite
    public ITextureObject getTexture(ResourceLocation textureLocation) {
        return performiaTextureManager.getTexture(textureLocation);
    }

    /**
     * @author Sk1er
     */
    @Overwrite
    public void tick() {
        performiaTextureManager.tick(listTickables);
    }

    /**
     * @author Sk1er
     */
    @Overwrite
    public void deleteTexture(ResourceLocation textureLocation) {
        performiaTextureManager.deleteTexture(textureLocation);
    }

}
