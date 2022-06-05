package com.leonardo.mangareader.services;

import java.io.ByteArrayOutputStream;
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
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor

@Slf4j

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

                URL pageUrl = new URL(imgUrl);

                Image page = Image.getInstance(pageUrl);

                document.setPageSize(new Rectangle(page.getWidth(), page.getHeight()));
                document.newPage();
                
                log.info(pageUrl + " was processed to download.");

                document.add(page);

            }

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        } finally {

            document.close();
        }

        log.info("Your download is ready!!!");

        return new DownloadDTO(fileName, baos.toByteArray());

    }

}
