package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Test {
	private final String FOLDER_PATH = "C:\\Users\\sudheendra.a\\Desktop\\ProjectPics\\";

	public void uploadImageToFileSystem(MultipartFile file) throws IOException {
		String filePath = FOLDER_PATH + file.getOriginalFilename();

		file.transferTo(new File(filePath));
	}
   
	@GetMapping("/")
	public String gethome() {
		return "project";
	}

	@PostMapping("/validation")
	public String Validate(@RequestParam String idnum, @RequestParam MultipartFile image, Model m) throws Exception {
		uploadImageToFileSystem(image);
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath( 
				"C:\\Users\\sudheendra.a\\Documents\\workspace-spring-tool-suite-4-4.16.1.RELEASE\\verification_id\\src\\main\\resources\\static\\tessdata");

		String text = tesseract.doOCR(new File(FOLDER_PATH.concat(image.getOriginalFilename())));
		if (idnum!=null && text.contains(idnum)) {
			System.out.println(text);
			
			
			m.addAttribute("message", "Verified Success");
		} else {
//			System.out.println("Error");
			m.addAttribute("message", "Failed To Verify");
		}
//		System.out.println(text); 
		File f = new File(FOLDER_PATH.concat(image.getOriginalFilename()));
		f.delete();
		return "project";
	}
}
