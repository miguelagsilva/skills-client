package com.miguelagsilva.skills.ui;

import com.miguelagsilva.skills.client.SkillsClient;
import java.awt.*;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class Button {
    private String label;
    private Runnable onClick;
    private Window window;
    private int offsetX, offsetY, width, height;

    public Button(
            String label,
            Runnable onClick,
            Window window,
            int offsetX,
            int offsetY,
            int width,
            int height) {
        this.label = label;
        this.onClick = onClick;
        this.window = window;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {
        boolean isHovered = checkInside(mouseX, mouseY);
        int bgColor;
        if (SkillsClient.moduleManager.getModule(label) == null) {
            bgColor = isHovered ? 0x80AA5555 : 0x80333333;
        } else {
            bgColor =
                    SkillsClient.moduleManager.getModule(label).isEnabled()
                            ? 0x8000AA55
                            : (isHovered ? 0x80444444 : 0x80333333);
        }

        context.fill(
                window.getX() + offsetX,
                window.getY() + offsetY,
                window.getX() + offsetX + width,
                window.getY() + offsetY + height,
                bgColor);
        context.drawTextWithShadow(
                textRenderer,
                label,
                window.getX() + offsetX + 5,
                window.getY() + offsetY + 4,
                Color.WHITE.getRGB());
    }

    public Runnable getOnClick() {
        System.out.println("Button clicked: " + label);
        return onClick;
    }

    public String getLabel() {
        return label;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean checkInside(int x, int y) {
        return x >= window.getX() + this.offsetX
                && x <= window.getX() + this.offsetX + width
                && y >= window.getY() + this.offsetY
                && y <= window.getY() + this.offsetY + height;
    }
}
