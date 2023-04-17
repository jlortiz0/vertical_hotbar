package dzwdz.vertical_hotbar.mixin;

import com.redlimerl.detailab.render.InGameDrawer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static dzwdz.vertical_hotbar.client.EntryPoint.config;

@Mixin(InGameDrawer.class)
public class DetailArmorBarMixin {
    @ModifyVariable(at = @At(value="HEAD"), ordinal=0, argsOnly = true, require = 0,
            method = "drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIFFIIIIZ)V")
    private static int drawTextureX(int x) {
        if (!config.enabled) {
            return x;
        }
        Window mc = MinecraftClient.getInstance().getWindow();
        int scaledWidth = mc.getScaledWidth();
        if (config.leftAlign) {
            x -= scaledWidth / 2;
            return x + config.xOffset;
        } else {
            x += scaledWidth / 2;
            return x - config.xOffset;
        }
    }

    @ModifyVariable(at = @At(value="HEAD"), ordinal=1, argsOnly = true, require = 0,
            method = "drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIFFIIIIZ)V")
    private static int drawTextureY(int y) {
        if (!config.enabled) {
            return y;
        }
        if (config.topAlign) {
            Window mc = MinecraftClient.getInstance().getWindow();
            int scaledHeight = mc.getScaledHeight();
            y -= scaledHeight;
            return y + config.yOffset + 50;
        }
        return y - config.yOffset;
    }
}
