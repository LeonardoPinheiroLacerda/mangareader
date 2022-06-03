package com.leonardo.mangareader.services;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.dtos.DownloadDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class PDFService {

    private final ChapterGetterFactoryService chapterGetterFactoryService;

    public DownloadDTO getFromUrl(String url) {

        DetailedChapterDTO chapter = chapterGetterFactoryService.getInstance(url).getFromUrl();

        String fileName = chapter.getTitle().replaceAll(" ", "_") + "_" + chapter.getCurrentChapter() + ".pdf";

        Document document = new Document();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);

            document.open();

            document.setMargins(0, 0, 0, 0);

            for (String imgUrl : chapter.getPages()) {

                Image page = Image.getInstance(new URL(imgUrl));

                document.setPageSize(new Rectangle(page.getWidth(), page.getHeight()));
                document.newPage();

                document.add(page);

            }

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        } finally {

            document.close();
        }

        return new DownloadDTO(fileName, baos.toByteArray());

    }

}
