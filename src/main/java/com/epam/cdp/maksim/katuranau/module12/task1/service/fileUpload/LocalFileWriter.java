package com.epam.cdp.maksim.katuranau.module12.task1.service.fileUpload;

import com.epam.cdp.maksim.katuranau.module12.task1.exception.WriteFileException;
import com.epam.cdp.maksim.katuranau.module12.task1.service.impl.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The local file writer.
 * Writes files to a folder that is processed by the Apache server
 *
 * @author Katuranau Maksimilyan
 * @see FileWriter
 */
@Component
public class LocalFileWriter implements FileWriter {

    @Value("${default.image.name}")
    private String DEFAULT_IMAGE_NAME;
    @Value("${image.absolute.path}")
    private String IMAGES_ABSOLUTE_PATH;

    private final MessageSourceService messageSourceService;

    @Autowired
    public LocalFileWriter(final MessageSourceService messageSourceService) {
        this.messageSourceService = messageSourceService;
    }

    /**
     * Delete file if exist.
     *
     * @param fileName the image name
     */
    @Override
    public void deleteFile(final String fileName) {
        if (Objects.nonNull(fileName) && !fileName.isEmpty() && !fileName.equals(DEFAULT_IMAGE_NAME)) {
            File file = new File(IMAGES_ABSOLUTE_PATH + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Write file.
     *
     * @param file the file to write
     */
    @Override
    public void writeFile(final MultipartFile file, final String fileName) {
        Path localFile = Paths.get(IMAGES_ABSOLUTE_PATH + fileName);
        try {
            Files.write(localFile, file.getBytes());
            if (!Objects.equals(Files.size(localFile), file.getSize())) {
                throw new WriteFileException(messageSourceService
                        .getLocaleMessage("order.errorFileWasNotWritten"));
            }
        } catch (IOException e) {
            throw new WriteFileException(messageSourceService
                    .getLocaleMessage("order.errorWritingToFile"));
        }
    }
}
