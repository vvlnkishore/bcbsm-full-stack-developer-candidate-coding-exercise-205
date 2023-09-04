package com.exmaple.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exmaple.model.Files;
import com.exmaple.repo.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository repo;

	public void addFile(MultipartFile file, String fromEmail, String toEmail) throws IOException {
		Files files = new Files();
		files.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		files.setFileId(UUID.randomUUID().toString());
		files.setFileName(file.getOriginalFilename());
		files.setSenderEmail(fromEmail);
		files.setRecipientEmail(toEmail);
		files.setUploadUser("User");
		files.setUploadDate(new Date());
		repo.save(files);
	}

	public List<Files> getFiles() {
		return repo.findAll();
	}
}
