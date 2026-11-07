package net.codexstudio.postify.screens;

import net.codexstudio.postify.PostifyMain;
import net.codexstudio.postify.screens.messages.MessageList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class Posters extends Screen {
    private final ResourceLocation background = ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "textures/gui/posters_bg.png");
    private final WidgetSprites[] buttonIcons = {
            new WidgetSprites(ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "textures/gui/button_icon/news.png"), ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "textures/gui/button_icon/news_hover.png")),
            new WidgetSprites(ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "textures/gui/button_icon/missions.png"), ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "textures/gui/button_icon/missions_hover.png")),
            new WidgetSprites(ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "textures/gui/button_icon/messages.png"), ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "textures/gui/button_icon/messages_hover.png")),
            new WidgetSprites(ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "textures/gui/button_icon/patch_notes.png"), ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "textures/gui/button_icon/patch_notes_hover.png"))
    };

    private int selectedTab = 0;
    ImageButton[] tabs = new ImageButton[4];

    private EditBox search;
    private MessageList[] messageList;

    public Posters() {
        super(Component.translatable("posters.title"));
    }

    @Override
    protected void init() {
        int messageX = 30;
        int tabsYpos = 60;

        int tabSize = 30;

        for (var tabIndexReference = new Object() {
            int i = 0;
        }; tabIndexReference.i < buttonIcons.length; tabIndexReference.i++) {
            tabs[tabIndexReference.i] = new ImageButton(messageX, tabIndexReference.i == 0 ? tabsYpos : tabsYpos * (tabIndexReference.i+2) + 2, tabSize, tabSize, buttonIcons[tabIndexReference.i], (onPress) -> {
               selectedTab = tabIndexReference.i;
            });
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        for (ImageButton tab : tabs) {
            tab.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(background, 0, 0, 0, 0, this.width, this.height);
    }

    public String getSearch() {
        return search.getValue();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
