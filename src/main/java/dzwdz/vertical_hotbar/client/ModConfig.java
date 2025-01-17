package dzwdz.vertical_hotbar.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "vertical_hotbar")
public class ModConfig implements ConfigData {
    public boolean enabled = false;
    public boolean attachToTop = false;
    public boolean flipBar = true;
    public boolean hotbarBorder = true;
    public boolean itemTooltip = true;
}