package com.exmaple.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.exmaple.model.Files;

public interface FileRepository extends MongoRepository<Files, String> {

	public Files findByFileId(String fileId);
}
