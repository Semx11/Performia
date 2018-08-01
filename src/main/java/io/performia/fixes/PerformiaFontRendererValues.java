package io.performia.fixes;

import io.performia.events.EventBus;
import io.performia.events.InvokeEvent;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformiaFontRendererValues {

    public static PerformiaFontRendererValues INSTANCE = new PerformiaFontRendererValues();
    private final int max = 1000;
    public Map<StringHash, CachedString> stringCache = new HashMap<>();
    public List<StringHash> obfuscated = new ArrayList<>();
    public Map<String, Integer> stringWidthCache = new HashMap<>();
    public boolean opt = true;
    private long time;
    private int e;
    private long count = 0;

    private PerformiaFontRendererValues() {
        EventBus.INSTANCE.register(this);
    }

    @InvokeEvent
    public void tick(TickEvent tickEvent) {

        opt = true;
        e++;
        for (StringHash hash : obfuscated) {
            CachedString cachedString = stringCache.get(hash);
            stringCache.remove(hash);
            if (cachedString != null)
                GLAllocation.deleteDisplayLists(cachedString.getListId());
        }
        obfuscated.clear();
        if (e >= 20) {
            e = 0;
            count = 0;
            time = 0;
            if (stringCache.size() > max) {
                for (CachedString cachedString : stringCache.values()) {
                    GLAllocation.deleteDisplayLists(cachedString.getListId());
                }
                stringCache.clear();
            }
        }


    }

    public void incTime(long l) {
        time += l;
        count++;
    }
}

