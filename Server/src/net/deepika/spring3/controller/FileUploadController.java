package net.deepika.spring3.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.viralpatel.spring3.form.FileUploadForm;

import org.apache.catalina.tribes.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rohit.midsemdemo.model.PdfFiles;





@Controller
public class FileUploadController {
	private static ArrayList<PdfFiles> PdfList = new ArrayList<PdfFiles>();
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String displayForm() {
		return "file_upload_form";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			@ModelAttribute("uploadForm") FileUploadForm uploadForm,
					Model map) {
		
		List<MultipartFile> files = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();
		
		if(null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();
				fileNames.add(fileName);
				//Handle file content - multipartFile.getInputStream()

			}
		} 
		
		map.addAttribute("files", fileNames);
		return "file_upload_success";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload( 
            @RequestParam("file") MultipartFile file){
            String name = "test11";
        if (!file.isEmpty()) {
            try {
            	PdfFiles pf = new PdfFiles();
            	pf.setFile(file);
            	PdfList.add(pf);
            	
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                System.out.println("You successfully uploaded"+PdfList.get(0).getFile().getOriginalFilename()+file.getSize());
                
              /*  for(int x= 0 ; x < bytes.length; x++) {
                    //printing the characters
                    System.out.print((char)bytes[x]  + "   "); 
                 }
                 System.out.println("   ");*/
             
                
                return "display";
              //  return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
            	System.out.println("You got exception");
               return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
        	System.out.println("file empty exception");
           return "You failed to upload " + name + " because the file was empty.";
        }
    }
	
	
	
	
	
	@RequestMapping(value = "/showfiles", method = RequestMethod.GET)
	public @ResponseBody ArrayList<String> showFiles() {
		
		ArrayList<String> list = new ArrayList<String> ();
		for(PdfFiles v : this.PdfList){
			list.add("File Name : "+ v.getFile().getOriginalFilename()+ ", Size:"+v.getFile().getSize()+"\n");
			System.out.println("File Name : "+ v.getFile().getOriginalFilename()+ ", Size:"+v.getFile().getSize()+"\n");
			}
		return list;
		
		
	}
}
