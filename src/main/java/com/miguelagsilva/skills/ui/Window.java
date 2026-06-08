package com.miguelagsilva.skills.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class Window {
    private final List<Button> buttons = new ArrayList<>();

    private final double UI_SCALE = 0.5;

    private final String title;
    private int x;
    private int y;
    private final int width = 120;
    private final int height = 100;

    public Window(String title, int x, int y) {
        this.title = title;
        this.x = x;
        this.y = y;
    }

    public void render(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {
        context.fill(x, y, x + width, y + height, 0x80222222);
        context.drawTextWithShadow(textRenderer, title, x + 5, y + 4, Color.WHITE.getRGB());
        renderButtons(context, textRenderer, mouseX, mouseY);
    }

    public void renderButtons(
            DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {
        for (Button button : buttons) {
            button.render(context, textRenderer, mouseX, mouseY);
        }
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    public String getTitle() {
        return title;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean checkInside(int posX, int posY) {
        return posX >= x && posX <= x + width && posY >= y && posY <= y + height;
    }

    public List<Button> getButtons() {
        return buttons;
    }
}
