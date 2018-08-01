package io.performia.mixins;

import io.performia.fixes.PerformiaItemModelMesher;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModelMesher.class)
public abstract class MixinModelMesher {
    private PerformiaItemModelMesher mesher = new PerformiaItemModelMesher((ItemModelMesher) (Object) this);

    @Inject(method = "getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;", at = @At("RETURN"))
    public void getItemReturn(ItemStack stack, CallbackInfoReturnable<IBakedModel> info) {
        mesher.updateItem(stack, info.getReturnValue());
    }

    @Inject(method = "getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;", at = @At("HEAD"), cancellable = true)
    public void getItemHead(ItemStack stack, CallbackInfoReturnable<IBakedModel> infoReturnable) {
        IBakedModel item = mesher.getItem(stack);
        if (item != null) {
            infoReturnable.setReturnValue(item);
            infoReturnable.cancel();
        }
    }
}
