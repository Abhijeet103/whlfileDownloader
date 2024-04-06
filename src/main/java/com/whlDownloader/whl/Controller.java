package com.whlDownloader.whl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URI;


@org.springframework.stereotype.Controller
public class Controller {


    @Autowired
    Service service;

    @GetMapping("/")
    public String myPage() {
        return "home"; // Without the .html extension
    }
    @GetMapping("download")
    public ResponseEntity<byte []> download(@RequestParam String packageName , @RequestParam(required = false , defaultValue = "latest") String version ,@RequestParam(required =false ,defaultValue = "false") boolean deps ) throws IOException, InterruptedException {

        try {

            byte[] zipContents = service.whlfile_downloader(packageName , deps , version);
            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "download.zip");
            headers.setLocation(URI.create("/success.html"));
            // Return the zip contents
            return ResponseEntity.ok().headers(headers).body(zipContents);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }



    }
}
