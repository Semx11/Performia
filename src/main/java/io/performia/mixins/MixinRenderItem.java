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

import io.performia.fixes.PerformiaRenderItem;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * A Mixin to the RenderItem class to provide ShinyPots support, not to be confused with the
 * ItemRenderer class, this class is entirely different
 *
 * @author boomboompower
 */
@Mixin(RenderItem.class)
public abstract class MixinRenderItem implements IResourceManagerReloadListener {

    private PerformiaRenderItem hyperiumRenderItem = new PerformiaRenderItem((RenderItem) (Object) this);




    /**
     * @author Sk1er
     */
    @Overwrite
    private void renderModel(IBakedModel model, int color, ItemStack stack) {
       hyperiumRenderItem.renderModel(model, color, stack);
    }




}

