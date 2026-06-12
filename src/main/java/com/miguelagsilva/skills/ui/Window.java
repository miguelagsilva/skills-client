package com.miguelagsilva.skills.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class Window {
    public static final int WIDTH = 120;
    public int height;
    public static final int TITLE_HEIGHT = 15;
    private static final int WINDOW_BACKGROUND_COLOR = 0x80860111;
    private static final int WINDOW_OUTLINE_COLOR = 0x80A20114;

    private final String title;
    private final List<Button> buttons = new ArrayList<>();
    private int x;
    private int y;
    private int scrollOffset = 0;

    public Window(String title, int x, int y, int height) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void render(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {
        drawOutlines(context);
        context.fill(x, y, x + WIDTH, y + TITLE_HEIGHT, WINDOW_BACKGROUND_COLOR);
        context.drawTextWithShadow(textRenderer, title, x + 4, y + 4, Color.WHITE.getRGB());

        context.enableScissor(x, y + TITLE_HEIGHT, x + WIDTH, y + height);
        for (Button button : buttons) {
            button.render(context, textRenderer, x, y, scrollOffset, mouseX, mouseY);
        }
        context.disableScissor();
    }

    private void drawOutlines(DrawContext context) {
        context.drawHorizontalLine(x, x + WIDTH, y, WINDOW_OUTLINE_COLOR);
        context.drawHorizontalLine(x, x + WIDTH, y + height, WINDOW_OUTLINE_COLOR);
        context.drawVerticalLine(x, y, y + height, WINDOW_OUTLINE_COLOR);
        context.drawVerticalLine(x + WIDTH, y, y + height, WINDOW_OUTLINE_COLOR);
    }

    public boolean handleClick(int mouseX, int mouseY) {
        for (Button button : buttons) {
            if (button.checkInside(mouseX, mouseY, x, y, scrollOffset)) {
                button.click();
                return true;
            }
        }
        return false;
    }

    public boolean hitsTitleBar(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + WIDTH && mouseY >= y && mouseY <= y + TITLE_HEIGHT;
    }

    public boolean checkInside(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + WIDTH && mouseY >= y && mouseY <= y + height;
    }

    public void setScrollOffset(int offset) {
        int maxScroll = Math.max(0, buttons.size() * Button.HEIGHT - (height - TITLE_HEIGHT));
        this.scrollOffset = Math.max(0, Math.min(offset, maxScroll));
    }

    public int getScrollOffset() {
        return scrollOffset;
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
}
