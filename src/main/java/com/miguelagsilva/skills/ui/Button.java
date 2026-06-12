package com.miguelagsilva.skills.ui;

import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class Button {
    public static final int HEIGHT = 15;

    private final String label;
    private final Runnable onClick;
    private final Supplier<Boolean> isActive;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int BUTTON_COLOR = 0x804A100C;
    private final int BUTTON_ACTIVE_COLOR = 0x807A1614;
    private final int BUTTON_HOVERED_COLOR = 0x806B010E;

    public Button(
            String label,
            Runnable onClick,
            Supplier<Boolean> isActive,
            int offsetX,
            int offsetY,
            int width) {
        this.label = label;
        this.onClick = onClick;
        this.isActive = isActive;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
    }

    public void render(
            DrawContext context,
            TextRenderer textRenderer,
            int winX,
            int winY,
            int scroll,
            int mouseX,
            int mouseY) {
        int absX = winX + offsetX;
        int absY = winY + offsetY - scroll;

        boolean hovered = checkInside(mouseX, mouseY, winX, winY, scroll);
        int bgColor =
                (isActive != null && isActive.get())
                        ? BUTTON_ACTIVE_COLOR
                        : (hovered ? BUTTON_HOVERED_COLOR : BUTTON_COLOR);

        context.fill(absX, absY, absX + width, absY + HEIGHT, bgColor);
        context.drawTextWithShadow(textRenderer, label, absX + 5, absY + 4, Color.WHITE.getRGB());
    }

    public boolean checkInside(int x, int y, int winX, int winY, int scroll) {
        int absX = winX + offsetX;
        int absY = winY + offsetY - scroll;
        return x >= absX && x <= absX + width && y >= absY && y <= absY + HEIGHT;
    }

    public void click() {
        onClick.run();
    }

    public String getLabel() {
        return label;
    }
}
