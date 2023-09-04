package com.exmaple.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exmaple.model.Files;
import com.exmaple.model.User;
import com.exmaple.service.FileService;

@RestController
@RequestMapping("/coding")
@CrossOrigin(origins = "http://localhost:4200")
public class CodingController {

	@Autowired
	private FileService service;

	@PostMapping("/save-file")
	public void addFile(@RequestParam("file") MultipartFile file, @RequestParam("fromEmail") String fromEmail,
			@RequestParam("toEmail") String toEmail) throws IOException {
		service.addFile(file, fromEmail, toEmail);
	}

	@GetMapping("/files")
	public List<Files> getDocuments() {
		return service.getFiles();
	}

	@GetMapping
	public String helloMessage() {
		return "Hello World";
	}

	@GetMapping("/users")
	public List<User> getUsers() {
		return Arrays.asList(new User(1, "Kishore", "kishore@abc.com"), new User(2, "Vadapalli", "vadapalli@abc.com"));
	}
}
