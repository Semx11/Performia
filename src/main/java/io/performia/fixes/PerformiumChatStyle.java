package io.performia.fixes;

import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class PerformiumChatStyle {
    private String cachedState;
    private ChatStyle parent;

    public PerformiumChatStyle(ChatStyle parent) {
        this.parent = parent;
    }

    public String getFormattingCode(ChatStyle parentStyle) {
        if (cachedState != null)
            return cachedState;
        if (parent.isEmpty()) {
            return parentStyle != null ? parentStyle.getFormattingCode() : "";
        } else {
            StringBuilder stringbuilder = new StringBuilder();

            if (parent.getColor() != null) {
                stringbuilder.append(parent.getColor());
            }

            if (parent.getBold()) {
                stringbuilder.append(EnumChatFormatting.BOLD);
            }

            if (parent.getItalic()) {
                stringbuilder.append(EnumChatFormatting.ITALIC);
            }

            if (parent.getUnderlined()) {
                stringbuilder.append(EnumChatFormatting.UNDERLINE);
            }

            if (parent.getObfuscated()) {
                stringbuilder.append(EnumChatFormatting.OBFUSCATED);
            }

            if (parent.getStrikethrough()) {
                stringbuilder.append(EnumChatFormatting.STRIKETHROUGH);
            }

            String s = stringbuilder.toString();
            cachedState = s;
            return s;
        }
    }

    public void resetCache() {
        this.cachedState = null;
    }
}
