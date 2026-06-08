package com.miguelagsilva.skills.ui;

import static com.miguelagsilva.skills.ui.Button.buttonHeight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix3x2fStack;

public class Window {
    private final List<Button> buttons = new ArrayList<>();

    private final String title;
    private int x;
    private int y;
    private int scrollOffset = 0;
    protected static final int windowWidth = (int) 120;
    protected static final int windowHeight = (int) 100;
    protected static final float titleScale = 1.0f;
    protected static final int titleSectionHeight = 15;
    protected static final int buttonSectionHeight = windowHeight - titleSectionHeight;

    public Window(String title, int x, int y) {
        this.title = title;
        this.x = x;
        this.y = y;
    }

    public void render(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {
        context.fill(x, y, x + windowWidth, y + windowHeight, 0x80222222);

        Matrix3x2fStack matrix = context.getMatrices().pushMatrix();
        context.getMatrices().scale(titleScale, titleScale, matrix);
        context.drawTextWithShadow(textRenderer, title, x + 4, y + 4, Color.WHITE.getRGB());
        context.getMatrices().popMatrix();

        renderButtons(context, textRenderer, mouseX, mouseY);
    }

    public void renderButtons(
            DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {
        context.enableScissor(
                this.x, this.y + buttonHeight, this.x + windowWidth, this.y + windowHeight);
        for (Button button : buttons) {
            button.render(context, textRenderer, mouseX, mouseY);
        }
        context.disableScissor();
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

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getHeight() {
        return windowHeight;
    }

    public boolean checkInside(int posX, int posY) {
        return posX >= x && posX <= x + windowWidth && posY >= y && posY <= y + windowHeight;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setScrollOffset(int offset) {
        int maxScroll = Math.max(0, buttons.size() * buttonHeight - buttonSectionHeight);
        if (offset <= 0) offset = 0;
        else if (offset > maxScroll) offset = maxScroll;
        this.scrollOffset = offset;
    }

    public int getScrollOffset() {
        return this.scrollOffset;
    }
}
