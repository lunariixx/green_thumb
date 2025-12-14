package net.luni.greenthumb.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ModConfig {

    private static final File CONFIG_FILE = new File("config/green_thumb.properties");
    private static final Properties PROPERTIES = new Properties();
    private static ModConfig INSTANCE;

    public AutoReplantConfig AutoReplant = new AutoReplantConfig();

    public static ModConfig get() {
        if (INSTANCE == null) INSTANCE = new ModConfig();
        return INSTANCE;
    }

    public static void load() {
        ModConfig config = get();
        if (!CONFIG_FILE.exists()) save();

        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            PROPERTIES.load(in);
            config.AutoReplant.enabled = Boolean.parseBoolean(PROPERTIES.getProperty("auto_replant.enabled", "true"));
            config.AutoReplant.requireSneak = Boolean.parseBoolean(PROPERTIES.getProperty("auto_replant.require_sneak", "true"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        ModConfig config = get();
        PROPERTIES.setProperty("auto_replant.enabled", Boolean.toString(config.AutoReplant.enabled));
        PROPERTIES.setProperty("auto_replant.require_sneak", Boolean.toString(config.AutoReplant.requireSneak));

        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            PROPERTIES.store(out, "Green Thumb Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class AutoReplantConfig {
        public boolean enabled = true;
        public boolean requireSneak = true;
    }
}