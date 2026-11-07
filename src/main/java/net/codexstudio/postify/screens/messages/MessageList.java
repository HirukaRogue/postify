package net.codexstudio.postify.screens.messages;

import net.codexstudio.postify.data.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class MessageList extends AbstractSelectionList<MessageList.Entry> {
    public MessageList(Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
    }

    public void setMessages(Message[] messages) {
        this.clearEntries();
        for (Message message : messages) {
            this.addEntry(new Entry(message));
        }
    }

    @Override
    public int getRowWidth() {
        return this.width - 20; // largura da linha
    }

    @Override
    protected int getScrollbarPosition() {
        return this.getX() + this.width - 6;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public class Entry extends ObjectSelectionList.Entry<Entry> {
        private final Message message;

        public Entry(Message message) {
            this.message = message;
        }

        public Message getMessage() {
            return message;
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            // Título
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    Component.literal(message.title()),
                    left + 4,
                    top + 4,
                    hovering ? 0xFFFFFF : 0xAAAAAA,
                    false
            );

            // From + data (exemplo)
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    Component.literal(message.from()),
                    left + 4,
                    top + 16,
                    0x888888,
                    false
            );
        }

        @Override
        public Component getNarration() {
            return Component.literal(message.title());
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            MessageList.this.setSelected(this);
            return true;
        }
    }


}
