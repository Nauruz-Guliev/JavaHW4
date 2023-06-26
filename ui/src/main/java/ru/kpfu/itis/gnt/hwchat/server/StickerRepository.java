package ru.kpfu.itis.gnt.hwchat.server;

import ru.kpfu.itis.gnt.hwchat.models.Sticker;
import ru.kpfu.itis.gnt.hwchat.utils.ImageConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class StickerRepository {
    private final Set<Sticker> stickers;
    private final File folder;

    public StickerRepository() {
        this.stickers = new HashSet<>();
        this.folder = new File("src/main/resources/stickers/");
        this.initStickers();
    }

    private void initStickers() {
        ArrayList<String> fileList = (ArrayList<String>) listFilesForFolder(folder);
        for (String stickerFileName : fileList) {
            try {
                Sticker sticker = new Sticker(ImageConverter.encodeToString(stickerFileName), stickerFileName.substring(stickerFileName.lastIndexOf("\\") + 1, stickerFileName.lastIndexOf(".")));
                stickers.add(sticker);
            } catch (Exception exception) {
                System.err.println("Failed to load file : " + stickerFileName + ". Reason : " + exception.getMessage());
            }
        }
    }

    public List<String> listFilesForFolder(final File folder) {
        ArrayList<String> list = new ArrayList<>();
        try {
            for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    if (fileEntry.isFile() && fileEntry.getName().endsWith(".png")) {
                        list.add(Path.of(folder.getPath(), fileEntry.getName()).toString());
                    }
                }
            }
            return list;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<String> getAvailableStickerCodes() {
        return stickers.stream().map(
                Sticker::code
        ).toList();
    }

    public Set<Sticker> getStickers() {
        return stickers;
    }

    public Sticker getStickerByCode(String stickerCode) throws IOException {
        return stickers.stream().filter(sticker -> sticker.code().contains(stickerCode)).findFirst().orElseThrow(
                () -> new IOException("Sticker not found")
        );
    }

    /*
    public static void main(String[] args) {
        // для тестов
        StickerRepository stickerRepository = new StickerRepository();
       System.out.println(stickerRepository.getStickerByCode("football"));
    }

     */

}
