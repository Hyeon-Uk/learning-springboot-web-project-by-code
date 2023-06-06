package com.example.mreview.controller;

import com.example.mreview.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {
    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName){
        ResponseEntity<byte[]> result = null;
        try{
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");

            log.info("display) fileName : {}",srcFileName);

            StringBuffer sf = new StringBuffer();
            sf.append(uploadPath).append(File.separator).append(srcFileName);

            File file = new File(sf.toString());

            log.info("display) file : {}",file);

            HttpHeaders header = new HttpHeaders();


            //MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile : uploadFiles){
            //이미지 파일인지 체크
            if(!uploadFile.getContentType().startsWith("image")){
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            //파일 이름 추출
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);

            log.info("fileName = {}",fileName);

            //날짜에 맞는 폴더 생성
            String folderPath = makeFolder();

            //UUID생성
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 _를 이용해서 구분
            StringBuffer sf = new StringBuffer();
            sf.append(uploadPath).append(File.separator).append(folderPath).append(File.separator).append(uuid).append('_').append(fileName);
            String saveName = sf.toString();

            Path savePath = Paths.get(saveName);

            try{
                uploadFile.transferTo(savePath);

                sf = new StringBuffer();
                sf.append(uploadPath).append(File.separator).append(folderPath).append(File.separator)
                                .append("s_").append(uuid).append('_').append(fileName);

                String thumbnailSaveName = sf.toString();

                File thumbnailFile = new File(thumbnailSaveName);

                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,100,100);

                resultDTOList.add(new UploadResultDTO(fileName,folderPath,uuid));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(resultDTOList,HttpStatus.OK);
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName = null;

        try{
            srcFileName = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath+File.separator+srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(),"s_"+file.getName());
            result = thumbnail.delete();

            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String makeFolder() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = date.replace("/", File.separator);

        File uploadFolder = new File(uploadPath,folderPath);

        if(!uploadFolder.exists()){
            uploadFolder.mkdirs();
        }

        return folderPath;
    }
}
