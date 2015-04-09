package com.deepika.model;


	import org.springframework.web.multipart.MultipartFile;
	 
public class PdfFiles {
	    private MultipartFile file;
	 
	    public MultipartFile getFile() {
	        return file;
	    }
	 
    public void setFile(MultipartFile file) {
	        this.file = file;
	    }
	 
	}
