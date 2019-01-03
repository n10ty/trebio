package com.trebio.context.bot.model;

import com.github.badoualy.telegram.api.Kotlogram;
import com.github.badoualy.telegram.api.TelegramApp;
import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.api.utils.MediaInput;
import com.github.badoualy.telegram.api.utils.TLMediaUtilsKt;
import com.github.badoualy.telegram.tl.api.*;
import com.github.badoualy.telegram.tl.api.auth.TLAuthorization;
import com.github.badoualy.telegram.tl.api.auth.TLSentCode;
import com.github.badoualy.telegram.tl.api.contacts.TLResolvedPeer;
import com.github.badoualy.telegram.tl.api.messages.TLAbsMessages;
import com.github.badoualy.telegram.tl.core.TLVector;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import com.trebio.context.channel.model.Channel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

@Component
public class KotloBot {
    @Value("${trebio.kotlobot.apiId}")
    private int apiId;

    @Value("${trebio.kotlobot.apiHash}")
    private String apiHash;

    @Value("${trebio.kotlobot.appVersion}")
    private String appVersion;

    @Value("${trebio.kotlobot.phoneNumber}")
    private String phoneNumber = "+380970407540"; // International format

    public static final String MODEL = "Trebio";
    public static final String SYSTEM_VERSION = "0.1";
    public static final String LANG_CODE = "en";
    private static final String PHOTO_DIR = String.format("%s/src/main/webapp/resources/image/photo/", System. getProperty("user.dir"));
    private TelegramClient client;
    private final static Logger LOGGER = Logger.getLogger("trebio");

    private void init() {
        TelegramApp application = new TelegramApp(apiId, apiHash, MODEL, SYSTEM_VERSION, appVersion, LANG_CODE);
        client = Kotlogram.getDefaultClient(application, new ApiStorage());
    }

    private void close() {
        client.close();
    }

    // Phone number used for tests
    public void auth() {
        try {
            // Send code to account
            TLSentCode sentCode = client.authSendCode(false, phoneNumber, true);
            System.out.println("Authentication code: ");
            String code = new Scanner(System.in).nextLine();
            // Auth with the received code
            TLAuthorization authorization = client.authSignIn(phoneNumber, sentCode.getPhoneCodeHash(), code);
            TLUser self = authorization.getUser().getAsUser();
            System.out.println("You are now signed in as " + self.getFirstName() + " " + self.getLastName() + " @" + self.getUsername());
        } catch (RpcErrorException | IOException e) {
            if (e instanceof RpcErrorException && ((RpcErrorException) e).getTag().equals("SESSION_PASSWORD_NEEDED")) {
                try {
                    TLAuthorization authorization = client.authCheckPassword("gri178512");
                    TLUser self = authorization.getUser().getAsUser();
                    System.out.println("You are now signed in as " + self.getFirstName() + " " + self.getLastName() + " @" + self.getUsername());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        } finally {
            client.close();
        }
    }

    public TLVector<TLAbsMessage> parseHistory(Channel channel, int page) throws IOException, RpcErrorException {
        init();
        Optional<TLInputPeerChannel> channelOption = parsePeer(channel);

        int offset = page * 10;

        if (channelOption.isPresent()) {
            TLInputPeerChannel tChannel = channelOption.get();
            TLAbsMessages messages = client.messagesGetHistory(tChannel, 0, 0, offset, 10, 0, 0);

            return messages.getMessages();
        }

        return new TLVector<>();
    }

    public TLVector<TLAbsMessage> parseHistory(Channel channel) throws IOException, RpcErrorException {
        return parseHistory(channel, 0);
    }

    public MediaInput parsePhoto(TLMessageMediaPhoto media) {
        try {
            File photo = new File(
                    String.format("%s/src/main/webapp/resources/image/photo/",System. getProperty("user.dir")),
                    String.format("%s_%s.jpg", media.getPhoto().getId(), "m")
            );
            if (!photo.exists()) {
                MediaInput mediaInput = TLMediaUtilsKt.getAbsMediaInput(media);
                FileOutputStream fos = new FileOutputStream(photo);
                client.downloadSync(mediaInput.getInputFileLocation(), mediaInput.getSize(), fos);
            }

            File photoThumb = new File(
                    String.format("%s/src/main/webapp/resources/image/photo/",System. getProperty("user.dir")),
                    String.format("%s_%s.jpg", media.getPhoto().getId(), "s")
            );
            if (!photoThumb.exists()) {
                MediaInput mediaInputThumb = TLMediaUtilsKt.getAbsMediaThumbnailInput(media);
                FileOutputStream fosThumb = new FileOutputStream(photoThumb);
                client.downloadSync(mediaInputThumb.getInputFileLocation(), mediaInputThumb.getSize(), fosThumb);
            }
        } catch (IOException | RpcErrorException e) {
            LOGGER.severe(e.getMessage());
        }

        return null;
    }

    public Optional<TLChannel> getChannel(TLAbsChat peer) {
        return peer instanceof TLChannel ? Optional.of((TLChannel) peer) : Optional.empty();
    }

    public Optional<TLChannel> getChannelInfo(Channel channel) throws IOException, RpcErrorException {
        TLResolvedPeer resolvedPeer = client.contactsResolveUsername(channel.getName());
        return resolvedPeer.getChats().stream()
                .filter(p -> p instanceof TLChannel)
                .map(this::getChannel)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    public Optional<TLInputPeerChannel> parsePeer(Channel channel) throws IOException, RpcErrorException {
        TLResolvedPeer resolvedPeer = client.contactsResolveUsername(channel.getName());

        return resolvedPeer.getChats().stream()
                .filter(p -> p instanceof TLChannel)
                .map(this::getChannel)
                .filter(o -> o.isPresent())
                .map(o -> new TLInputPeerChannel(o.get().getId(), o.get().getAccessHash()))
                .findFirst();
    }
}
