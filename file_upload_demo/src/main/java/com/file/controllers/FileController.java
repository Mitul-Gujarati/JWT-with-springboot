package com.file.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.file.payloads.FileResponse;
import com.file.services.FileService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image) {
		String fileName;
		try {
			fileName = fileService.uploadImage(path, image);
		} catch (IOException e) {
			FileResponse fileResponse = new FileResponse(null, e.getMessage());
			return new ResponseEntity<FileResponse>(fileResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		FileResponse fileResponse = new FileResponse(fileName, "File Uploaded Successfully!!");
		return new ResponseEntity<FileResponse>(fileResponse, HttpStatus.OK);
	}

	@GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadFile(@PathVariable String imageName, HttpServletResponse response) {
		try {
			InputStream resource = fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//setting the value in cookies
	@GetMapping("/set")
	public String setCookie(HttpServletResponse response) {
		// set a new cookie
		Cookie cookie = new Cookie("color", "blue");
		
		//updating the value of cookie.
		//cookie.setValue("red");
		
		// add cookie in server response
		response.addCookie(cookie);

		return "Spring Boot Cookies";
	}

	//getting the value from cookies
	@GetMapping("/get")
	public String getCookie(@CookieValue(value = "color", defaultValue = "No color found in cookie") String color) {

		return "Sky is: " + color;
	}

	//setting expiry of cookies value 
	@GetMapping("/expiry")
	public String setCookieExpiry(HttpServletResponse response) {

		int cookieAgeInSeconds = 10;

		Cookie cookie = new Cookie("website", "https://websparrow.org");
		cookie.setMaxAge(cookieAgeInSeconds); // expire in 10 seconds.
		response.addCookie(cookie);

		return "Cookie will expire in " + cookieAgeInSeconds + " seconds.";
	}
	
	//deleting cookies by cookie name
	@GetMapping("/delete")
	public String deleteCookie(HttpServletResponse response) {

	    Cookie cookie = new Cookie("color", null);
	    cookie.setMaxAge(0); // delete cookie
	    response.addCookie(cookie);

	    return "Cookie deleted with cookie name: " + cookie.getName();
	}
}
