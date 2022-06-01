package com.leonardo.mangareader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MangareaderApplication {

    public static final String API_MANGA_URL_PREFIX = "/api/mangas?url=";
    public static final String API_CHAPTER_URL_PREFIX = "/api/chapters?url=";
	public static final String API_CHAPTER_DOWNLOAD_URL_PREFIX = "/api/download/chapters?url=";

	public static void main(String[] args) {
		SpringApplication.run(MangareaderApplication.class, args);
	}

}
