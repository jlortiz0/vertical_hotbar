package dzwdz.vertical_hotbar.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EntryPoint implements ClientModInitializer {

    public static ModConfig config;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
