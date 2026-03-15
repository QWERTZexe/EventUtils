/*
 * Copyright 2026 QWERTZexe ALL RIGHTS RESERVED
 */

package app.qwertz;

import cc.aabss.eventutils.EventUtils;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static net.minecraft.text.Text.translatable;


/**
 * Screen to choose which plus tag to display next to your name.
 * Shows only unlocked tags; hover shows how each was unlocked.
 */
public class PlusTagScreen extends Screen {
    @Nullable private final Screen parent;
    private final Set<PlusTag> unlocked;

    public PlusTagScreen(@Nullable Screen parent, @Nullable Set<PlusTag> unlocked) {
        super(translatable("eventutils.plustag.title"));
        this.parent = parent;
        this.unlocked = unlocked != null ? unlocked : Set.of();
    }

    @Override
    protected void init() {
        final PlusTag current = EventUtils.MOD.config.selectedPlusTag;
        int y = 40;
        final int centerX = width / 2;
        final int btnWidth = 200;
        final int spacing = 26;

        // Option: None (hide / linked)
        addTagButton(centerX - btnWidth / 2, y, btnWidth, PlusTag.NONE, current);
        y += spacing;

        if (unlocked.contains(PlusTag.WHITE)) {
            addTagButton(centerX - btnWidth / 2, y, btnWidth, PlusTag.WHITE, current);
            y += spacing;
        }

        if (unlocked.contains(PlusTag.RED)) {
            addTagButton(centerX - btnWidth / 2, y, btnWidth, PlusTag.RED, current);
            y += spacing;
        }

        if (unlocked.contains(PlusTag.BLUE)) {
            addTagButton(centerX - btnWidth / 2, y, btnWidth, PlusTag.BLUE, current);
            y += spacing;
        }

        if (unlocked.contains(PlusTag.ORANGE)) {
            addTagButton(centerX - btnWidth / 2, y, btnWidth, PlusTag.ORANGE, current);
            y += spacing;
        }

        if (unlocked.contains(PlusTag.PINK)) {
            addTagButton(centerX - btnWidth / 2, y, btnWidth, PlusTag.PINK, current);
            y += spacing;
        }

        addDrawableChild(ButtonWidget.builder(translatable("gui.done"), btn -> {
            if (client != null) client.setScreen(parent);
        }).dimensions(centerX - 60, height - 28, 120, 20).build());
    }

    private void addTagButton(int x, int y, int w, PlusTag tag, PlusTag current) {
        Text label = translatable("eventutils.plustag." + tag.getKey());
        Text tooltipText = translatable(tag.getUnlockKey());
        boolean selected = current == tag;
        ButtonWidget btn = ButtonWidget.builder(selected ? label.copy().append(" ✓") : label, b -> {
            EventUtils.MOD.config.setSelectedPlusTag(tag);
            if (client != null) client.setScreen(new PlusTagScreen(parent, unlocked));
        }).dimensions(x, y, w, 20).tooltip(Tooltip.of(tooltipText)).build();
        addDrawableChild(btn);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0xC0101010);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 14, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, translatable("eventutils.plustag.subtitle"), width / 2, 24, 0xA0A0A0);
        super.render(context, mouseX, mouseY, delta);
    }
}
