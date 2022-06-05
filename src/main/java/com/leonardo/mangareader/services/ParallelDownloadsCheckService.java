package com.leonardo.mangareader.services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.services.PDFService.UserDownload;

@Service
public class ParallelDownloadsCheckService {
    
    private final static List<UserDownload> USER_DOWNLOADS_LIST = new CopyOnWriteArrayList<>();
    private final static Integer PARALLEL_DOWNLOADS = 3;

    public UserDownload checkParallelDownloads(UserDownload userDownload){
    
        if(USER_DOWNLOADS_LIST.contains(userDownload)){

            userDownload = getFromList(userDownload);

            System.out.println(userDownload);
            
            if(userDownload.getCount() >= PARALLEL_DOWNLOADS){
                return null;
            }
            
        }else{
            USER_DOWNLOADS_LIST.add(userDownload);
        }
        
        return userDownload;
    }

    public void increaseDownloadCount(UserDownload userDownload){
        userDownload.setCount(userDownload.getCount() + 1);
    }

    public void decreaseDownloadCount(UserDownload userDownload){
        userDownload.setCount(userDownload.getCount() - 1);
    }

    public UserDownload getFromList(UserDownload userDownload){
        return USER_DOWNLOADS_LIST
                .stream()
                .filter(sd -> userDownload.equals(sd))
                .findFirst()
                .get();
    }

    public Integer getParallelDownloadsLimit(){
        return PARALLEL_DOWNLOADS;
    }


}
