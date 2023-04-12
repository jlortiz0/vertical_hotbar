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
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dzwdz.vertical_hotbar.client.EntryPoint.*;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Shadow protected abstract void renderHotbarItem(int i, int j, float f, PlayerEntity playerEntity, ItemStack itemStack, int seed);

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow protected abstract LivingEntity getRiddenEntity();

    @Shadow @Final private MinecraftClient client;

    @Shadow @Final private static Identifier WIDGETS_TEXTURE;

    @Inject(at = @At("HEAD"), cancellable = true,
            method = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V")
    public void renderHotbar(float f, MatrixStack matrixStack, CallbackInfo callbackInfo) {
        if (!config.enabled) return;

        PlayerEntity playerEntity = getCameraPlayer();
        if (playerEntity == null) return;

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);

        if (config.hotbarBorder) {
            client.getTextureManager().bindTexture(BARS);
            Vec2i pos = getSlotPos(0, scaledWidth, scaledHeight);
            drawTexture(matrixStack, pos.x - 1, pos.y - 1, 20, 0, 22, 182);
        }

        for(int i = 0; i < 9; i++) {
            Vec2i pos = getSlotPos(i, scaledWidth, scaledHeight);
            drawTexture(matrixStack, pos.x, pos.y, 1+20*i, 1, 20, 20);
            if (i == playerEntity.getInventory().selectedSlot)
                drawTexture(matrixStack, pos.x - 2, pos.y - 2, 0, 22, 24, 24);
        }

        { // selected slot overlay
            Vec2i pos = getSlotPos(playerEntity.getInventory().selectedSlot, scaledWidth, scaledHeight);
            drawTexture(matrixStack, pos.x - 2, pos.y - 2, 0, 22, 24, 24);
        }

        { // offhand
            Vec2i pos = getSlotPos(9, scaledWidth, scaledHeight);
            drawTexture(matrixStack, pos.x - 1, pos.y - 2, 24, 22, 29, 24);
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int m = 1;
        for (int i = 0; i < 9; i++) {
            Vec2i pos = getSlotPos(i, scaledWidth, scaledHeight);
            renderHotbarItem(pos.x + 2, pos.y + 2, f, playerEntity, playerEntity.getInventory().main.get(i), m++);
        }

        {
            Vec2i pos = getSlotPos(9, scaledWidth, scaledHeight);
            renderHotbarItem(pos.x + 2, pos.y + 2, f, playerEntity, playerEntity.getOffHandStack(), m++);
        }

        RenderSystem.disableBlend();

        callbackInfo.cancel();
    }

    public void verticality$drawStatusBar(MatrixStack matrixStack, int i, int u1, int u2, int v, int val, int color) {
        if (!config.enabled) return;

        Vec2i pos = getStatusPos(i, scaledWidth, scaledHeight);

        drawTexture(matrixStack, pos.x, pos.y, u1, v, 9, 9);
        drawTexture(matrixStack, pos.x, pos.y, u2, v, 9, 9);
        String s = Integer.toString(val);
        int w = client.textRenderer.getWidth(s);
        client.textRenderer.drawWithShadow(matrixStack, Integer.toString(val), pos.x - 2 - w, pos.y, color);
        client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
    }

    @Inject(at = @At("HEAD"), cancellable = true,
            method = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
    public void renderStatusBars(MatrixStack matrixStack, CallbackInfo callbackInfo) {
        if (!config.enabled) return;

        PlayerEntity playerEntity = getCameraPlayer();
        if (playerEntity == null) return;

        int i = 0;

        LivingEntity riddenEntity = getRiddenEntity();
        if (riddenEntity != null) {
            int hp = (int)riddenEntity.getHealth();
            int maxHP = (int)riddenEntity.getMaxHealth();
            verticality$drawStatusBar(matrixStack, i++, 52, 88 + (hp <= maxHP*.75 ? 9 : 0), 9, hp, 0xFFFFFF);
        }

        int playerHP = (int)playerEntity.getHealth();
        int playerMaxHP = (int)playerEntity.getMaxHealth();
        verticality$drawStatusBar(matrixStack, i++, 16, 52 + (playerHP <= playerMaxHP*.75 ? 9 : 0), 0, playerHP, 0xFFFFFF);

        int absorption = (int)playerEntity.getAbsorptionAmount();
        if (absorption > 0)
            verticality$drawStatusBar(matrixStack, i++, 16, 160, 0, absorption, 0xFFFF00);

        int hunger = playerEntity.getHungerManager().getFoodLevel();
        verticality$drawStatusBar(matrixStack, i++, 16, 52 + (hunger <= 15 ? 9 : 0), 27, hunger, 0xFFFFFF);

        int armor = playerEntity.getArmor();
        if (armor > 0)
            verticality$drawStatusBar(matrixStack, i++, 16, 34, 9, armor, 0xFFFFFF);

        int air = (playerEntity.getAir()*20)/playerEntity.getMaxAir();
        if (air < 0) air = 0;
        if (playerEntity.getAir() < playerEntity.getMaxAir())
            verticality$drawStatusBar(matrixStack, i++, 16, 16, 18, air, 0xFFFFFF);

        callbackInfo.cancel();
    }

    @Inject(at = @At("HEAD"), cancellable = true,
            method = "Lnet/minecraft/client/gui/hud/InGameHud;renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public void renderExperienceBar(MatrixStack matrixStack, int _x, CallbackInfo callbackInfo) {
        if (!config.enabled) return;

        client.getTextureManager().bindTexture(EntryPoint.BARS);

        int nextLevelXP = client.player.getNextLevelExperience();
        if (nextLevelXP > 0) {
            int n = (int)(client.player.experienceProgress * 183f);
            Vec2i pos = getBarPos(scaledWidth, scaledHeight);
            drawTexture(matrixStack, pos.x, pos.y, 15, 0, 5, 182);
            if (n > 0) {
                if (config.flipBar ^ config.attachToTop)
                    drawTexture(matrixStack, pos.x, pos.y + 182 - n, 10, 182 - n, 5, n);
                else
                    drawTexture(matrixStack, pos.x, pos.y, 10, 0, 5, n);
            }
        }

        if (client.player.experienceLevel > 0) {
            String s = "" + this.client.player.experienceLevel;
            Vec2i pos = getLevelPos(scaledWidth, scaledHeight);
            int x = pos.x - client.textRenderer.getWidth(s);
            client.textRenderer.draw(matrixStack, s, x + 1, pos.y, 0);
            client.textRenderer.draw(matrixStack, s, x - 1, pos.y, 0);
            client.textRenderer.draw(matrixStack, s, x, pos.y + 1, 0);
            client.textRenderer.draw(matrixStack, s, x, pos.y - 1, 0);
            client.textRenderer.draw(matrixStack, s, x, pos.y, 0x80FF20);
        }

        client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
        callbackInfo.cancel();
    }

    @Inject(at = @At("HEAD"), cancellable = true,
            method = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountJumpBar(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public void renderMountJumpBar(MatrixStack matrixStack, int _x, CallbackInfo callbackInfo) {
        if (!config.enabled) return;

        client.getTextureManager().bindTexture(EntryPoint.BARS);

        int n = (int)(client.player.getMountJumpStrength() * 183f);
        Vec2i pos = getBarPos(scaledWidth, scaledHeight);
        drawTexture(matrixStack, pos.x, pos.y, 5, 0, 5, 182);
        if (n > 0) {
            if (config.flipBar ^ config.attachToTop)
                drawTexture(matrixStack, pos.x, pos.y + 182 - n, 0, 182 - n, 5, n);
            else
                drawTexture(matrixStack, pos.x, pos.y, 0, 0, 5, n);
        }

        callbackInfo.cancel();
    }

    @Inject(at = @At("HEAD"), cancellable = true,
            method = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountHealth(Lnet/minecraft/client/util/math/MatrixStack;)V")
    public void renderMountHealth(MatrixStack matrixStack, CallbackInfo callbackInfo) {
        if (!config.enabled) return;

        callbackInfo.cancel();
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/StringRenderable;FFI)I"),
               method = "Lnet/minecraft/client/gui/hud/InGameHud;renderHeldItemTooltip(Lnet/minecraft/client/util/math/MatrixStack;)V", index = 3)
    public float centerItemTooltip(float y) {
        if (!config.enabled) return y;

        if (!config.itemTooltip) return scaledHeight + 1000;
        return scaledHeight / 2 - 18;
    }
}
