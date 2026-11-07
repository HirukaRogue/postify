package net.codexstudio.postify.data;

import com.mojang.datafixers.util.Either;
import net.codexstudio.postify.PostifyMain;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public record Message(String title, ResourceLocation image, Date date, String from, String category, MessageContent[] content) implements CustomPacketPayload {
    private static final Set<String> VALID_CATEGORIES = Set.of(
            "news", "missions", "messages", "patch_notes"
    );

    public record MessageContent(Either<String, MessageContentImage> value) {
        public record MessageContentImage(ResourceLocation image, int position) {

        }
    }

    public static final CustomPacketPayload.Type<Message> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(PostifyMain.MOD_ID, "message_packet"));

    public Message {
        if (category == null || !VALID_CATEGORIES.contains(category)) {
            PostifyMain.LOGGER.error("Invalid category: {}. Valid categories are: {}", category, VALID_CATEGORIES);
            throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    public static final StreamCodec<net.minecraft.network.FriendlyByteBuf, Message> STREAM_CODEC = StreamCodec.of(
            (buffer, payload) -> {
                buffer.writeUtf(payload.title());
                buffer.writeResourceLocation(payload.image());
                buffer.writeLong(payload.date().getTime());
                buffer.writeUtf(payload.from());
                buffer.writeUtf(payload.category());

                // Exemplo de como você lidaria com a escrita do array se precisar serializar
                buffer.writeVarInt(payload.content().length);
                for (MessageContent mc : payload.content()) {
                    // Como você está usando Either (Texto ou Imagem), você pode usar um indicador (ex: boolean ou byte)
                    if (mc.value().left().isPresent()) {
                        buffer.writeBoolean(true); // É uma String/Texto
                        buffer.writeUtf(mc.value().left().get());
                    } else {
                        buffer.writeBoolean(false); // É uma Imagem
                        MessageContent.MessageContentImage img = mc.value().right().get();
                        buffer.writeResourceLocation(img.image());
                        buffer.writeInt(img.position());
                    }
                }
            },
            buffer -> {
                int length = buffer.readVarInt();
                MessageContent[] contents = new MessageContent[length];

                for (int i = 0; i < length; i++) {
                    boolean isText = buffer.readBoolean();
                    if (isText) {
                        contents[i] = new MessageContent(Either.left(buffer.readUtf()));
                    } else {
                        ResourceLocation res = buffer.readResourceLocation();
                        int pos = buffer.readInt();
                        contents[i] = new MessageContent(Either.right(new MessageContent.MessageContentImage(res, pos)));
                    }
                }

                return new Message(
                        buffer.readUtf(),
                        buffer.readResourceLocation(),
                        new Date(buffer.readLong()),
                        buffer.readUtf(),
                        buffer.readUtf(),
                        contents
                );
            }
    );

    public String id() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
