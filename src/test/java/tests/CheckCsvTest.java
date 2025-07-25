package tests;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CheckCsvTest {
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
    @DisplayName("Проверка CSV-файла в архиве")
    void checkCsvInZip() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("testfiles.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".csv")) {
                    try (CSVReader reader = new CSVReader(new InputStreamReader(zis))) {
                        List<String[]> rows = reader.readAll();
                        Assertions.assertEquals(2, rows.size());
                        Assertions.assertArrayEquals(
                                new String[]{"ID", "Name", "Email", "Address", "Phone"},
                                rows.get(0)
                        );
                        Assertions.assertArrayEquals(
                                new String[]{"899", "Lora Mark", "cgor@conroy.com", "1682 Gwend Harbors"},
                                rows.get(1)
                        );
                    }
                    break;
                }
            }
        }
    }
}
