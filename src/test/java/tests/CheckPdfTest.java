package tests;

import com.codeborne.pdftest.PDF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CheckPdfTest {
    private final ClassLoader cl = ZipFileParsingTest.class.getClassLoader();
    @Test
    @DisplayName("Проверка наличии файлов в архиве")
    void checkZipNotNullSafe() throws Exception {
        InputStream stream = Objects.requireNonNull(
                cl.getResourceAsStream("testfiles.zip"),
                "Файл testfiles.zip не найден!"
        );

        try (ZipInputStream zis = new ZipInputStream(stream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println("Нашли файл: " + entry.getName());
            }
        }
    }
    @Test
    @DisplayName("Проверка PDF-файла в архиве")
    void checkPdfInZip() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("testfiles.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(
                            pdf.text.contains("Тестовый PDF-документ"),
                            "PDF не содержит нужный текст"
                    );
                    break;
                }
            }
        }
    }
}
