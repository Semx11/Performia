package io.performia.fixes;

import io.performia.Settings;
import io.performia.events.EventBus;
import io.performia.events.InvokeEvent;
import io.performia.events.WorldChangeEvent;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class PerformiaItemModelMesher {

    private ItemModelMesher parent;
    private HashMap<ItemStack, IBakedModel> models = new HashMap<>();

    public PerformiaItemModelMesher(ItemModelMesher parent) {
        this.parent = parent;
        EventBus.INSTANCE.register(this);
    }

    @InvokeEvent
    public void worldChange(WorldChangeEvent event) {
        models.clear();
    }

    public void updateItem(ItemStack stack, IBakedModel model) {
        if (stack == null)
            return;
        if (Settings.OPTIMIZED_ITEM_RENDERER) {
            models.put(stack, model);
        } else models.clear(); // clear to not have things sitting in ram if they wont be used
    }

    public IBakedModel getItem(ItemStack stack) {
        if (stack == null || !Settings.OPTIMIZED_ITEM_RENDERER) {
            return null;
        }

//        System.out.println(iBakedModel +" (" + models.size()+")");
        return models.get(stack);
    }
}
