package io.performia.mixins;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(RenderItem.class)
public interface IMixinRenderItem {

    @Invoker
    void callRenderQuads(WorldRenderer renderer, List<BakedQuad> quads, int color, ItemStack stack);




}
