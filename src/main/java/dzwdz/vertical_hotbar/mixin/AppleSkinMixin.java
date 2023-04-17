package dzwdz.vertical_hotbar.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import squeek.appleskin.client.HUDOverlayHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static dzwdz.vertical_hotbar.client.EntryPoint.*;

@Mixin(HUDOverlayHandler.class)
public class AppleSkinMixin {

    private int scaledWidth;
    private int scaledHeight;
    @ModifyVariable(at = @At(value="HEAD"), ordinal=0, argsOnly = true, require = 0,
            method = "drawSaturationOverlay(Lnet/minecraft/client/util/math/MatrixStack;FFLnet/minecraft/client/MinecraftClient;IIF)V")
    public int drawSaturationOverlayX(int x) {
        if (!config.enabled) {
            return x;
        }
        Window mc = MinecraftClient.getInstance().getWindow();
        this.scaledWidth = mc.getScaledWidth();
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
            return x + config.xOffset;
        } else {
            x += this.scaledWidth / 2;
            return x - config.xOffset;
        }
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=2, argsOnly = true, require = 0,
            method = "drawHungerOverlay(Lnet/minecraft/client/util/math/MatrixStack;IILnet/minecraft/client/MinecraftClient;IIFZ)V")
    public int drawHungerOverlayX(int x) {
        if (!config.enabled) {
            return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
            return x + config.xOffset;
        } else {
            x += this.scaledWidth / 2;
            return x - config.xOffset;
        }
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=0, argsOnly = true, require = 0,
            method = "drawHealthOverlay(Lnet/minecraft/client/util/math/MatrixStack;FFLnet/minecraft/client/MinecraftClient;IIF)V")
    public int drawHealthOverlayX(int x) {
        if (!config.enabled) {
            return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
            return x + config.xOffset;
        } else {
            x += this.scaledWidth / 2;
            return x - config.xOffset;
        }
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=0, argsOnly = true, require = 0,
            method = "drawExhaustionOverlay(Lnet/minecraft/client/util/math/MatrixStack;FLnet/minecraft/client/MinecraftClient;IIF)V")
    public int drawExhaustionOverlayX(int x) {
        if (!config.enabled) {
            return x;
        }
        if (config.leftAlign) {
            x -= this.scaledWidth / 2;
            return x + config.xOffset;
        } else {
            x += this.scaledWidth / 2;
            return x - config.xOffset;
        }
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=1, argsOnly = true, require = 0,
            method = "drawSaturationOverlay(Lnet/minecraft/client/util/math/MatrixStack;FFLnet/minecraft/client/MinecraftClient;IIF)V")
    public int drawSaturationOverlayY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            Window mc = MinecraftClient.getInstance().getWindow();
            this.scaledHeight = mc.getScaledHeight();
            y -= this.scaledHeight;
            return y + config.yOffset + 50;
        }
        return y - config.yOffset;
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=3, argsOnly = true, require = 0,
            method = "drawHungerOverlay(Lnet/minecraft/client/util/math/MatrixStack;IILnet/minecraft/client/MinecraftClient;IIFZ)V")
    public int drawHungerOverlayY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
            return y + config.yOffset + 50;
        }
        return y - config.yOffset;
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=1, argsOnly = true, require = 0,
            method = "drawHealthOverlay(Lnet/minecraft/client/util/math/MatrixStack;FFLnet/minecraft/client/MinecraftClient;IIF)V")
    public int drawHealthOverlayY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
            return y + config.yOffset + 50;
        }
        return y - config.yOffset;
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=1, argsOnly = true, require = 0,
            method = "drawExhaustionOverlay(Lnet/minecraft/client/util/math/MatrixStack;FLnet/minecraft/client/MinecraftClient;IIF)V")
    public int drawExhaustionOverlayY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            y -= this.scaledHeight;
            return y + config.yOffset + 50;
        }
        return y - config.yOffset;
    }
}
