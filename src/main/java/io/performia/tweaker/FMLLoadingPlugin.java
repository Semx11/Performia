package io.performia.tweaker;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.List;

@MCVersion("1.8.9")
@SortingIndex(1)
public class FMLLoadingPlugin implements ITweaker {


    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {

    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        System.out.println("Performia Tweaker injecting...");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.performia.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);


        //Taken from chat triggers
        // fixing mixins. code from https://github.com/ReplayMod/ReplayMod/blob/0a26f59c9c5ac6ec72b8bdb758ef126f2d10e28d/src/main/java/com/replaymod/core/LoadingPlugin.java#L26
        CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();
        if (codeSource != null) {
            URL location = codeSource.getLocation();
            try {
                File file = new File(location.toURI());
                if (file.isFile()) {
                    CoreModManager.getIgnoredMods().remove(file.getName());
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No CodeSource, if this is not a development environment we might run into problems!");
            System.out.println(getClass().getProtectionDomain());
        }
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return new String[0];
    }
}
