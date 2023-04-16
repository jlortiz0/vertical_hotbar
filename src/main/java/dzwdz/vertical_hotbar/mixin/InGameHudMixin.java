package dzwdz.vertical_hotbar.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static dzwdz.vertical_hotbar.client.EntryPoint.*;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @ModifyVariable(at = @At(value="STORE", ordinal=0), ordinal=0,
            method = "renderHotbar")
    public Arm renderHotbarArm(Arm x) {
        if (!config.enabled) {
            return x;
        }
        if (config.leftAlign) {
            return Arm.RIGHT;
        }
        return Arm.LEFT;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=1,
            method = "renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V")
    public int renderHotbarX(int x) {
        if (!config.enabled) {
			return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
        } else {
            x += this.scaledWidth / 2;
        }
		return x + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=1,
            method = "renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
    public int renderStatusBarsX(int x) {
        if (!config.enabled) {
			return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
        } else {
            x += this.scaledWidth / 2;
        }
        return x + config.xOffset;
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=0, argsOnly = true,
            method = "renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public int renderExperienceBarX(int x) {
        if (!config.enabled) {
			return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
        } else {
            x += this.scaledWidth / 2;
        }
        return x + config.xOffset;
    }

    @ModifyArg(at = @At(value="INVOKE", target="Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I"), index=2,
            method = "renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public float renderExperienceBarTextX(float x) {
        if (!config.enabled) {
            return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
        } else {
            x += this.scaledWidth / 2;
        }
        return x + config.xOffset;
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=0, argsOnly = true,
            method = "renderMountJumpBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public int renderMountJumpBarX(int x) {
        if (!config.enabled) {
			return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
        } else {
            x += this.scaledWidth / 2;
        }
        return x + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=1,
            method = "renderMountHealth(Lnet/minecraft/client/util/math/MatrixStack;)V")
    public int renderMountHealthX(int x) {
        if (!config.enabled) {
			return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
        } else {
            x += this.scaledWidth / 2;
        }
        return x + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V"), index=0,
            method = "renderHotbar")
    public int renderHotbarItemX(int x) {
        if (!config.enabled) {
            return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
        } else {
            x += this.scaledWidth / 2;
        }
        return x + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"), index=2,
            method = "renderStatusBars")
    public int renderHealthBarX(int x) {
        if (!config.enabled) {
            return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
        } else {
            x += this.scaledWidth / 2;
        }
        return x + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"),
               method = "renderHeldItemTooltip(Lnet/minecraft/client/util/math/MatrixStack;)V", index = 2)
    public float centerItemTooltipX(float x) {
        if (!config.enabled) return x;

        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
        } else {
            x += this.scaledWidth / 2;
        }
        return x + config.xOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=2,
            method = "renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V")
    public int renderHotbarY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
        }
        return y + config.yOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=2,
            method = "renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
    public int renderStatusBarsY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
        }
        return y + config.yOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=2,
            method = "renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public int renderExperienceBarY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
        }
        return y + config.yOffset;
    }

    @ModifyArg(at = @At(value="INVOKE", target="Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I"), index=3,
            method = "renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public float renderExperienceBarTextY(float y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
        }
        return y + config.yOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=2,
            method = "renderMountJumpBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public int renderMountJumpBarY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
        }
        return y + config.yOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index=2,
            method = "renderMountHealth(Lnet/minecraft/client/util/math/MatrixStack;)V")
    public int renderMountHealthY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
        }
        return y + config.yOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V"), index=1,
            method = "renderHotbar")
    public int renderHotbarItemY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
        }
        return y + config.yOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"), index=3,
            method = "renderStatusBars")
    public int renderHealthBarY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
        }
        return y + config.yOffset;
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"),
            method = "renderHeldItemTooltip(Lnet/minecraft/client/util/math/MatrixStack;)V", index = 3)
    public float centerItemTooltipY(float y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
        }
        return y + config.yOffset;
    }
}
