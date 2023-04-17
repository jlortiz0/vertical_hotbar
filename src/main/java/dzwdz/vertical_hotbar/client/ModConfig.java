package dzwdz.vertical_hotbar.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "vertical_hotbar")
public class ModConfig implements ConfigData {
    public boolean enabled = false;
    public int xOffset = 100;
    public int yOffset = 10;
    public boolean leftAlign = true;
    public boolean topAlign = false;
}