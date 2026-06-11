package com.miguelagsilva.skills.ui;

import com.miguelagsilva.skills.SkillsClient;
import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;

public class ClickGUIScreen extends Screen {
    private static final float TARGET_PHYSICAL_SCALE = 2.0f;
    private float uiScale;

    private final Map<ModuleCategory, Window> windows = new HashMap<>();
    private double dragOffsetX = 0;
    private double dragOffsetY = 0;
    private Window dragWindow = null;

    public ClickGUIScreen() {
        super(Text.literal("ClickGUI"));
    }

    @Override
    public void init() {
        super.init();
        uiScale = TARGET_PHYSICAL_SCALE / (float) client.getWindow().getScaleFactor();
        windows.clear();
        registerWindows();
    }

    private void registerWindows() {
        Map<ModuleCategory, List<AbstractModule>> categoryMap = SkillsClient.moduleManager.getModulesInCategories();
        int virtualWidth = (int) (this.width / uiScale);
        int curX = 10;
        int curY = 10;

        for (ModuleCategory category : ModuleCategory.values()) {
            List<AbstractModule> modules = categoryMap.get(category);
            if (modules == null || modules.isEmpty()) continue;
            int windowHeight = Window.TITLE_HEIGHT + modules.size() * Button.HEIGHT;

            if (curX + Window.WIDTH > virtualWidth - 10) {
                curX = 10;
                curY += windowHeight + 10;
            }

            Window window = new Window(category.getName(), curX, curY, windowHeight);
            int buttonOffsetY = Window.TITLE_HEIGHT;
            for (AbstractModule mod : modules) {
                window.addButton(new Button(mod.getName(), mod::toggle, mod::isEnabled, 0, buttonOffsetY, Window.WIDTH));
                buttonOffsetY += Button.HEIGHT;
            }

            windows.put(category, window);
            curX += Window.WIDTH + 10;
        }
    }

    private int scaledMouse(double coord) {
        return (int) (coord / uiScale);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Matrix3x2fStack matrix = context.getMatrices().pushMatrix();
        context.getMatrices().scale(uiScale, uiScale, matrix);

        int smx = scaledMouse(mouseX);
        int smy = scaledMouse(mouseY);
        windows.values().forEach(w -> w.render(context, textRenderer, smx, smy));

        context.getMatrices().popMatrix();
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (click.button() != 0) return super.mouseClicked(click, doubled);

        int mx = scaledMouse(click.x());
        int my = scaledMouse(click.y());

        for (Window window : windows.values()) {
            if (!window.checkInside(mx, my)) continue;
            if (window.handleClick(mx, my)) return true;
            if (window.hitsTitleBar(mx, my)) {
                dragOffsetX = mx - window.getX();
                dragOffsetY = my - window.getY();
                dragWindow = window;
                return true;
            }
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if (click.button() == 0 && dragWindow != null) {
            dragWindow.setX((int) (scaledMouse(click.x()) - dragOffsetX));
            dragWindow.setY((int) (scaledMouse(click.y()) - dragOffsetY));
        }
        return super.mouseDragged(click, offsetX, offsetY);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (click.button() == 0) dragWindow = null;
        return super.mouseReleased(click);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (dragWindow != null) return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);

        int smx = scaledMouse(mouseX);
        int smy = scaledMouse(mouseY);
        for (Window window : windows.values()) {
            if (window.checkInside(smx, smy)) {
                window.setScrollOffset((int) (window.getScrollOffset() - verticalAmount * 10));
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        InputUtil.Key key = InputUtil.fromKeyCode(input);
        KeyBinding.setKeyPressed(key, true);
        if (SkillsClient.moduleManager.getModule("ClickGUI").isEnabled()) {
            if (key.getCode() == InputUtil.GLFW_KEY_ESCAPE) {
                SkillsClient.moduleManager.getModule("ClickGUI").toggle();
                return true;
            }
        }
        return super.keyPressed(input);
    }
}
