package com.exmaple.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.exmaple.model.Files;
import com.exmaple.repo.FileRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class FileService {

	@Autowired
	private FileRepository repo;
	
	@Autowired
    private JavaMailSender mailSender;

	@Transactional(rollbackFor = Exception.class)
	public void addFile(MultipartFile file, String fromEmail, String toEmail) throws Exception {
		Files files = new Files();
		files.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		files.setFileId(UUID.randomUUID().toString());
		files.setFileName(file.getOriginalFilename());
		files.setSenderEmail(fromEmail);
		files.setRecipientEmail(toEmail);
		files.setUploadUser("User");
		files.setUploadDate(new Date());
		repo.save(files);
		emailTrigger(file, fromEmail, toEmail);
	}

	public List<Files> getFiles() {
		return repo.findAll();
	}
	
	private void emailTrigger(MultipartFile file, String fromEmail, String toEmail) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(toEmail);
		helper.setSubject("Attachment Test");
		helper.setText("This is a test email");

		helper.addAttachment(file.getOriginalFilename(), file);

		mailSender.send(message);
	}
}
