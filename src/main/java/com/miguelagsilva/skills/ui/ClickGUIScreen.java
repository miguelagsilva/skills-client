package com.miguelagsilva.skills.ui;

import com.miguelagsilva.skills.client.SkillsClient;
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
import org.slf4j.Logger;

import static com.miguelagsilva.skills.ui.Button.buttonHeight;
import static com.miguelagsilva.skills.ui.Window.titleSectionHeight;

public class ClickGUIScreen extends Screen {
    private final Logger logger = SkillsClient.Logger;

    public static final float UI_SCALE = 1.0f;

    private Map<ModuleCategory, Window> windows = new HashMap<>();
    private Map<ModuleCategory, List<AbstractModule>> modulesInCategories =
            SkillsClient.moduleManager.getModulesInCategories();

    private int frameX = 10;
    private int frameY = 10;

    private double dragOffsetX = 0;
    private double dragOffsetY = 0;
    private Window dragWindow = null;

    public ClickGUIScreen() {
        super(Text.literal("ClickGUI"));
        registerWindows();
    }

    private void registerWindows() {
        for (AbstractModule module : SkillsClient.moduleManager.getModules().values()) {
            if (!windows.containsKey(module.getCategory())) {
                Window newWindow = new Window(module.getCategory().toString(), frameX, frameY);

                windows.put(module.getCategory(), newWindow);

                int newFrameX = newWindow.getX() + newWindow.getWindowWidth() + 10;
                if (newFrameX + Window.windowWidth > this.client.getWindow().getScaledWidth()) {
                    frameX = 100;
                    frameY += newWindow.getHeight() + 10;
                } else {
                    frameX += newWindow.getWindowWidth() + 10;
                }

                int buttonOffsetX = 0;
                int buttonOffsetY = titleSectionHeight;
                for (AbstractModule mod : modulesInCategories.get(module.getCategory())) {
                    newWindow.addButton(
                            new Button(
                                    mod.getName(),
                                    () -> {
                                        mod.toggle();
                                        logger.info("Toggled module: " + mod.getName());
                                    },
                                    newWindow,
                                    buttonOffsetX,
                                    buttonOffsetY,
                                    newWindow.getWindowWidth(),
                                    buttonHeight));
                    buttonOffsetY += buttonHeight;
                }
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        Matrix3x2fStack matrix = context.getMatrices().pushMatrix();
        context.getMatrices().scale(UI_SCALE, UI_SCALE, matrix);

        windows.forEach(
                (category, window) -> {
                    window.render(context, textRenderer, mouseX, mouseY);
                });

        context.getMatrices().popMatrix();
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (click.button() == 0) {

            for (Window window : windows.values()) {
                if (window.checkInside((int) click.x(), (int) click.y())) {
                    for (Button button : window.getButtons()) {
                        if (button.checkInside((int) click.x(), (int) click.y())) {
                            button.getOnClick().run();
                            return true;
                        }
                    }
                    // If we clicked the title bar, start dragging
                    if (click.x() >= window.getX()
                            && click.x() <= window.getX() + window.getWindowWidth()
                            && click.y() >= window.getY()
                            && click.y() <= window.getY() + buttonHeight) {
                        dragOffsetX = click.x() - window.getX();
                        dragOffsetY = click.y() - window.getY();
                        dragWindow = window;
                        return true;
                    }
                }
            }
        }

        // Call super if we didn't click any of our custom buttons
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        logger.info(
                String.format(
                        "Dragging with offsetX: %.3f, offsetY: %.3f", dragOffsetX, dragOffsetY));
        logger.info(String.format("Current frame position: (%d, %d)", frameX, frameY));
        if (click.button() == 0 && dragWindow != null) {
            this.dragWindow.setX((int) (click.x() - this.dragOffsetX));
            this.dragWindow.setY((int) (click.y() - this.dragOffsetY));
        }
        return super.mouseDragged(click, offsetX, offsetY);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (click.button() == 0) {
            this.dragWindow = null;
        }
        return super.mouseReleased(click);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (dragWindow == null) {
            for (Window window : windows.values()) {
                if (window.checkInside((int) mouseX, (int) mouseY)) {
                    window.setScrollOffset((int) (window.getScrollOffset()-(verticalAmount)*10));
                    return true;
                }
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
