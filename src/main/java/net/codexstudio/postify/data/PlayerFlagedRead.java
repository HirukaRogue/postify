package net.codexstudio.postify.data;

import net.codexstudio.postify.PostifyMain;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public record PlayerFlagedRead (UUID playerUUID, String messageId, boolean hasRead) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PlayerFlagedRead> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "player_flagged_read"));

    public PlayerFlagedRead(UUID playerUUID, String messageId) {
        this(playerUUID, messageId, false);
    }

    public static final StreamCodec<net.minecraft.network.FriendlyByteBuf, PlayerFlagedRead> STREAM_CODEC = StreamCodec.of(
            (buffer, payload) -> {
                buffer.writeUUID(payload.playerUUID());
                buffer.writeUtf(payload.messageId());
                buffer.writeBoolean(payload.hasRead());
            },
            buffer -> new PlayerFlagedRead(buffer.readUUID(), buffer.readUtf(), buffer.readBoolean())
    );

    public PlayerFlagedRead markAsRead() {
        return new PlayerFlagedRead(this.playerUUID, this.messageId, true);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
