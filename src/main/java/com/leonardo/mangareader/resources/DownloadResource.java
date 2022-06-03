package com.leonardo.mangareader.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.mangareader.dtos.DownloadDTO;
import com.leonardo.mangareader.services.PDFService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping("api/downloads")
public class DownloadResource {
    
    private final PDFService pdfService;

    @GetMapping("/chapter")
    public FileSystemResource donwloadChapterPDF(HttpServletResponse response, @RequestParam String url){        
        DownloadDTO download = pdfService.getFromUrl(url);
        
        response.setContentType("application/pdf");      
        response.setHeader("Content-Disposition", "attachment; filename=\"" + download.getFileName() + "\"");
        
        return new FileSystemResource(download.getFilePath()); 
    }

}
