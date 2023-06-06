package com.example.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
    private String fileName;
    private String folderPath;
    private String uuid;

    public String getImageURL(){
        try{
            StringBuffer sf = new StringBuffer();
            sf.append(folderPath).append('/').append(uuid).append('_').append(fileName);
            return URLEncoder
                    .encode(sf.toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getThumbnailURL(){
        try{
            StringBuffer sf = new StringBuffer();
            sf.append(folderPath).append("/s_").append(uuid).append('_').append(fileName);

            return URLEncoder
                    .encode(sf.toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
