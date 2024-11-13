package com.kl.book.file;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.logging.Logger;

//@Slf4j
public class FileUtils {
   private static final Logger log=Logger.getLogger(FileUtils.class.getName());

    public static byte[] readFileFromLocation(String fileUrl) {

        if (StringUtils.isBlank(fileUrl)){
            return null;
        }
        try {
            Path filePath=new File(fileUrl).toPath();
            byte[] fileContent = Files.readAllBytes(filePath);

            // Convert the file content to a Base64 string
            return Files.readAllBytes(filePath);

        } catch (IOException e) {
           log.warning(String.format("No File found in the path {} %s",fileUrl));
        }
        return null;
    }
}
