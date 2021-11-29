package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /**
     * Find all files by userId
     * @param userId userId
     * @return List of Files
     */
    public List<File> getFileListByUserId(Integer userId) {
        return this.fileMapper.findAllByUserId(userId);
    }

    /**
     * Find a specific file by its name
     * @param fileName fileName
     * @return File
     */
    public File getFileByFileName(String fileName) {
        return this.fileMapper.findFileByFileName(fileName);
    }

    /**
     * Save a new file by userId in the Database
     * @param file file
     * @param userId userId
     * @throws IOException IOException
     */
    public void saveFileByUserId(MultipartFile file, Integer userId) throws IOException {
        byte[] fileData = file.getBytes();
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        String fileName = file.getOriginalFilename();

        this.fileMapper.save(new File(null, fileName, contentType, fileSize, userId, fileData));

    }

    /**
     * Delete a file by fileId and userId
     * @param fileId fileId
     * @param userId userId
     */
    public void deleteByFileIdAndUserId(Integer fileId, Integer userId) {
        this.fileMapper.delete(fileId, userId);
    }
}
