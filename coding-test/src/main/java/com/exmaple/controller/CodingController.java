package com.exmaple.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exmaple.model.Files;
import com.exmaple.service.FileService;

@RestController
@RequestMapping("/coding")
@CrossOrigin(origins = "http://localhost:4200")
public class CodingController {

	@Autowired
	private FileService service;

	@PostMapping("/save-file")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	public void addFile(@RequestParam("file") MultipartFile file, @RequestParam("fromEmail") String fromEmail,
			@RequestParam("toEmail") String toEmail) throws Exception {
		service.addFile(file, fromEmail, toEmail);
	}

	@GetMapping("/files")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	public List<Files> getDocuments() {
		return service.getFiles();
	}

	@GetMapping
	public String helloMessage() {
		return "Hello World";
	}
}
