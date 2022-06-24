package com.leonardo.mangareader.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.leonardo.mangareader.dtos.DetailedChapterDTO;
import com.leonardo.mangareader.dtos.DownloadDTO;
import com.leonardo.mangareader.exceptions.DownloadException;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.services.factories.ChapterGetterFactoryService;
import com.leonardo.mangareader.webscraping.chapterGetter.ChapterGetter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor

@Slf4j

@Service
public class PDFService {

    private final ChapterGetterFactoryService chapterGetterFactoryService;
    private final ParallelDownloadsCheckService parallelDownloadsCheckService;
    private final UserService userService;

    @RequiredArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode(of = "user")
    public class UserDownload {
        private final String user;
        private Integer count = 0;
    }

    public DownloadDTO getFromUrl(String url) {  

        ChapterGetter getter = chapterGetterFactoryService.getInstance(url);

        User user = userService.getLogged();
    
        UserDownload checkedUserDownload = parallelDownloadsCheckService.checkParallelDownloads(new UserDownload(user.getUsername()));

        if(checkedUserDownload == null){
            throw new DownloadException("Só é possível fazer até " + parallelDownloadsCheckService.getParallelDownloadsLimit() + " downloads simutânios.");
        }

        parallelDownloadsCheckService.increaseDownloadCount(checkedUserDownload);

        DetailedChapterDTO chapter = getter.getFromUrl();

        String fileName = chapter.getTitle().replaceAll(" ", "_") + "_" + chapter.getCurrentChapter() + ".pdf";

        Document document = new Document();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            document.open();

            document.setMargins(0, 0, 0, 0);

            for (String imgUrl : chapter.getPages()) {

                URL pageUrl = new URL(imgUrl);

                Image page = Image.getInstance(pageUrl);

                if(page.getHeight() > 14400){
                    
                    Image cropped1 = cropImage(page, writer, 0, page.getHeight() - 14400, page.getWidth(), 14400);
                    Image cropped2 = cropImage(page, writer, 0, 0, page.getWidth(), page.getHeight() - 14400);

                    appendImage(document, cropped1);
                    appendImage(document, cropped2);
                    
                    log.info(pageUrl + " was cropped and processed to download.");

                    continue;
                }

                appendImage(document, page);

                log.info(pageUrl + " was processed to download.");

            }

        } catch (DocumentException | IOException de) {
            throw new DownloadException("Algo de errado está acontecendo na construção do PDF.");
        } finally {
            document.close();
        }

        log.info("Your download is ready!!!");

        parallelDownloadsCheckService.decreaseDownloadCount(checkedUserDownload);

        return new DownloadDTO(fileName, baos.toByteArray());

    }

    private Image cropImage(Image image, PdfWriter writer, float x, float y, float width, float height) throws DocumentException {
        PdfTemplate t = writer.getDirectContent().createTemplate(width, height);
        float origWidth = image.getScaledWidth();
        float origHeight = image.getScaledHeight();
        t.addImage(image, origWidth, 0, 0, origHeight, -x, -y);
        return Image.getInstance(t);
    }

    private void appendImage(Document document, Image page) throws DocumentException{
        document.setPageSize(new Rectangle(page.getWidth(), page.getHeight()));
        document.newPage();
        document.add(page);
    }

}
