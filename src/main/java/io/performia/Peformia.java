package io.performia;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;


@Mod(modid = Peformia.MOD_ID, version = Peformia.VERSION, name = Peformia.MOD_NAME)
public class Peformia {
    public static final String MOD_ID = "performia";
    public static final String MOD_NAME = "Performia";
    public static final String VERSION = ".1";
    @Instance
    public static Peformia INSTANCE;


    private File suggestedConfigurationFile;

    public Peformia() {
        INSTANCE = this;
    }


    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {

        suggestedConfigurationFile = event.getSuggestedConfigurationFile();

    }



}
