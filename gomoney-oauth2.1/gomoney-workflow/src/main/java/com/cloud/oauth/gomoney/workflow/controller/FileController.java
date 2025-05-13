package com.cloud.oauth.gomoney.workflow.controller;


import com.cloud.oauth.gomoney.core.utils.R;
import com.cloud.oauth.gomoney.workflow.common.utils.RRException;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/center/file")
public class FileController {

    private final String CATEGORY_TEST = "pics";

    @Schema(name = "MP4播放")
    @PostMapping("/player")
    @ResponseBody
    public void player(String url, HttpServletRequest request, HttpServletResponse response) {
        File dir = new File(CATEGORY_TEST);
        try {
            File file = new File(dir.getAbsolutePath() + File.separator + url);
            FileInputStream fis = null;
            OutputStream os = null;
            fis = new FileInputStream(file);
            int size = fis.available(); // 得到文件大小
            byte data[] = new byte[size];
            fis.read(data); // 读数据
            fis.close();
            fis = null;
            response.setContentType("video/mp4"); // 设置返回的文件类型
            os = response.getOutputStream();
            os.write(data);
            os.flush();
            os.close();
            os = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/downLoad")
    @ResponseBody
    public ResponseEntity<byte[]> downLoad(String url, String fileName, HttpServletResponse response, HttpServletRequest request) throws Exception {
        File dir = new File(CATEGORY_TEST);
        File file = new File(dir.getAbsolutePath() + File.separator + url);
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        byte[] byt = new byte[fis.available()];
        fis.read(byt);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + fileName);
        headers.add("Content-Length", "" + byt.length);
        List<String> hlist = new ArrayList<String>();
        hlist.add("Content-Disposition");
        hlist.add("Content-Length");
        headers.setAccessControlExposeHeaders(hlist);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byt.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byt);
    }

    @GetMapping("/appDownLoad")
    @ResponseBody
    public ResponseEntity<byte[]> appDownLoad(String url, String fileName, HttpServletResponse response, HttpServletRequest request) throws Exception {
        File dir = new File(CATEGORY_TEST);
        File file = new File(dir.getAbsolutePath() + File.separator + url);
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        byte[] byt = new byte[fis.available()];
        fis.read(byt);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + fileName);
        headers.add("Content-Length", "" + byt.length);
        List<String> hlist = new ArrayList<String>();
        hlist.add("Content-Disposition");
        hlist.add("Content-Length");
        headers.setAccessControlExposeHeaders(hlist);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byt.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byt);
    }

    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        File dir = new File(CATEGORY_TEST);
        if (!dir.exists()) {
            dir.mkdirs();  //判断并创建文件夹
        }
        byte[] bytes = file.getBytes();
        String uuid = UUID.randomUUID().toString();
        String url = dir.getAbsolutePath() + File.separator + uuid + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        File fileToSave = new File(url);
        FileCopyUtils.copy(bytes, fileToSave);
        return R.ok().put("url", uuid + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length()));
    }

    @GetMapping("/openImg")
    public void getImage(HttpServletResponse response, String url) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        File dir = new File(CATEGORY_TEST);
        File file = new File(dir.getAbsolutePath() + File.separator + url);
        BufferedImage im = ImageIO.read(file);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(im, url.substring(url.lastIndexOf(".") + 1), out);
        IOUtils.closeQuietly(out);
    }

    @GetMapping("/deleteFile")
    public R deleteFile(HttpServletResponse response, String url) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        String fileUrl = url.substring(url.indexOf("=") + 1, url.length());
        File dir = new File(CATEGORY_TEST);
        File file = new File(dir.getAbsolutePath() + File.separator + fileUrl);
        file.delete();
        return R.ok();
    }

    @PostMapping("/uploadfile")
    public R uploadfile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        File dir = new File(CATEGORY_TEST );
        if (!dir.exists()) {
            dir.mkdirs();  //判断并创建文件夹
        }
        //上传文件
        //String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        byte[] bytes = file.getBytes();
        String uuid = UUID.randomUUID().toString();
        String url = dir.getAbsolutePath() + File.separator + uuid + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        File fileToSave = new File(url);
        FileCopyUtils.copy(bytes, fileToSave);
        BufferedImage im = ImageIO.read(fileToSave);
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        ServletOutputStream out = sra.getResponse().getOutputStream();
        ImageIO.write(im, url.substring(url.indexOf(".") + 1, url.length()), out);
        IOUtils.closeQuietly(out);
        return R.ok();
    }
}
