package tests;

import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CheckXlsxTest{

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
   @DisplayName("Проверка XLSX-файла в архиве")
    void checkXlsxInZip() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("testfiles.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".xlsx")) {
                    XLS xls = new XLS(zis);
                    String actualCellValue = xls.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue();
                    Assertions.assertEquals("Surname", actualCellValue);
                    break;
                }
            }
        }
    }
}