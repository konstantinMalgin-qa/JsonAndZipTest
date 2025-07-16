package tests;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class SelenideFilesTest {
    @Test
    void downloadFilesTest() throws Exception{
        open("https://github.com/junit-team/junit5/blob/main/README.md");
        File textFile = $(".react-blob-header-edit-and-raw-actions [href*='/main/README.md']").download();

        try (InputStream is = new FileInputStream(textFile)) {
            byte[] fileContent = is.readAllBytes();
            String strContent = new String(fileContent, StandardCharsets.UTF_8);
        }


    }

}
