package dzwdz.vertical_hotbar.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import slimeknights.mantle.client.ExtraHeartRenderHandler;

import static dzwdz.vertical_hotbar.client.EntryPoint.config;

@Mixin(ExtraHeartRenderHandler.class)
public class MantleMixin {
    @ModifyVariable(at=@At("HEAD"), ordinal=0, method = "blit", require = 0, argsOnly = true)
    public int modifyHeartX(int x) {
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

    @ModifyVariable(at=@At("HEAD"), ordinal=1, method = "blit", require = 0, argsOnly = true)
    public int modifyHeartY(int y) {
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
