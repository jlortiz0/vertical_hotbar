package dzwdz.vertical_hotbar.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dzwdz.vertical_hotbar.Vec2i;
import dzwdz.vertical_hotbar.client.EntryPoint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dzwdz.vertical_hotbar.client.EntryPoint.*;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=1,
            method = "renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V")
    public int renderHotbarRender(int x) {
        if (!config.enabled) {
			return x;
        }
		return x - this.scaledWidth / 2 + config.xOffset;
    }

    @ModifyVariable(at = @At(value="STORE", ordinal=0), ordinal=0,
            method = "renderHotbar")
    public Arm renderHotbarArm(Arm x) {
        if (!config.enabled) {
            return x;
        }
        return Arm.RIGHT;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=1,
            method = "renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
    public int renderStatusBars(int x) {
        if (!config.enabled) {
			return x;
        }
		return x - this.scaledWidth / 2 + config.xOffset;
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=0,
            method = "renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public int renderExperienceBar(int x) {
        if (!config.enabled) {
			return x;
        }
		return x - this.scaledWidth / 2 + config.xOffset;
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=0,
            method = "renderMountJumpBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public int renderMountJumpBar(int x) {
        if (!config.enabled) {
			return x;
        }
		return x - this.scaledWidth / 2 + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=1,
            method = "renderMountHealth(Lnet/minecraft/client/util/math/MatrixStack;)V")
    public int renderMountHealth(int x) {
        if (!config.enabled) {
			return x;
        }
		return x - this.scaledWidth / 2 + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V"), index=0,
            method = "renderHotbar")
    public int renderHotbarItem(int x) {
        if (!config.enabled) {
            return x;
        }
        return x - this.scaledWidth / 2 + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"), index=2,
            method = "renderStatusBars")
    public int renderHealthBar(int x) {
        if (!config.enabled) {
            return x;
        }
        return x - this.scaledWidth / 2 + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"),
               method = "renderHeldItemTooltip(Lnet/minecraft/client/util/math/MatrixStack;)V", index = 2)
    public float centerItemTooltip(float x) {
        if (!config.enabled) return x;

        return x - this.scaledWidth / 2 + config.xOffset;
    }
}
