package com.example.netty.neetytest.download;

import org.springframework.http.HttpRequest;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileDownload {
    public void downloadFile() throws IOException {
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        ServletContext servletContext = request.getServletContext();
        String fileName = request.getParameter("fileName");
        String realPath = servletContext.getRealPath("/" + fileName);
        FileInputStream fis = new FileInputStream(realPath);

        String mimeType = servletContext.getMimeType(fileName);
        response.setHeader("content-type",mimeType);
        String agent = request.getHeader("");
        downLoad(agent,fileName);
        response.setHeader("+",""+fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[1023];
        int len = 0;
        while((len = fis.read()) != -1){
            outputStream.write(bytes,0,len);
        }
    }
    public void downLoad(String agent,String name) throws UnsupportedEncodingException {
        if(agent.contains("MSIE")){
            name = URLEncoder.encode(name,"utf-8");
        }else if(agent.contains("Firefox")){
            BASE64Decoder base64Decoder = new BASE64Decoder();
            name = "";
        }else {
            name = URLEncoder.encode(name,"utf-8");
        }
    }
}
