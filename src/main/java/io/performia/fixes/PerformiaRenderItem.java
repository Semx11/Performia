package io.performia.fixes;

import io.performia.Settings;
import io.performia.mixins.IMixinRenderItem;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PerformiaRenderItem {
    private RenderItem parent;
    private HashMap<CachedItem, Integer> cache = new HashMap<>();
    private ConcurrentLinkedQueue<CachedItem> queue = new ConcurrentLinkedQueue<>();

    public PerformiaRenderItem(RenderItem parent) {
        this.parent = parent;
    }

    public void renderModel(IBakedModel model, int color, ItemStack stack) {
        int i = 0;
        //Needs to be reworked for forge since the model param is different per frame.
        // In Vanilla there is 1 persistent object per item
        CachedItem cachedItem = null;
        if (Settings.OPTIMIZED_ITEM_RENDERER) {
            if (cache.size() > 5000) {
                for (int c = 0; c < 50; c++) {
                    CachedItem poll = queue.poll();
                    Integer integer = cache.get(poll);
                    if (integer != null) {
                        GL11.glDeleteLists(integer, 1);
                        cache.remove(poll);
                    }
                }
            }

            cachedItem = new CachedItem(model, color, stack != null ? stack.getUnlocalizedName() : "", stack != null ? stack.getItemDamage() : 0, stack != null ? stack.getMetadata() : 0, stack != null ? stack.getTagCompound() : null);
            Integer integer = cache.get(cachedItem);
            if (integer != null) {
                GlStateManager.callList(integer);
                GlStateModifier.INSTANCE.resetColor();
                return;
            }
            queue.add(cachedItem);
            i = GLAllocation.generateDisplayLists(1);
            GL11.glNewList(i, GL11.GL_COMPILE_AND_EXECUTE);
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.ITEM);

        for (EnumFacing enumfacing : EnumFacing.values()) {
            ((IMixinRenderItem) parent).callRenderQuads(worldrenderer, model.getFaceQuads(enumfacing), color, stack);
        }

        ((IMixinRenderItem) parent).callRenderQuads(worldrenderer, model.getGeneralQuads(), color, stack);
        tessellator.draw();
        if (Settings.OPTIMIZED_ITEM_RENDERER) {
            GL11.glEndList();
            cache.put(cachedItem, i);
        }
    }
}
