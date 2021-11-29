package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@ControllerAdvice
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping(value = "download/files/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] getFile(@PathVariable String fileName) {
        File file = fileService.getFileByFileName(fileName);
        return file.getFileData();
    }


    @PostMapping("/upload/files")
    public String uploadFile(Authentication auth,
                             @RequestParam(value = "fileUpload") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(auth.getName()).getUserId();
        if (fileService.getFileByFileName(multipartFile.getOriginalFilename()) != null){
            log.info("File: " + multipartFile.getOriginalFilename() + " already exist!");
            String error ="File already exist!";
            redirectAttributes.addFlashAttribute("error", error);
            return "redirect:/home";
        } else if (multipartFile.isEmpty()){
            String error ="File can not be empty!";
            redirectAttributes.addFlashAttribute("error", error);
            return "redirect:/home";
        }else {
            try {
                fileService.saveFileByUserId(multipartFile,userId);
                log.info("File: " + multipartFile.getOriginalFilename() + " saved successfully!");
                redirectAttributes.addFlashAttribute("result", "success");
                Thread.sleep(300);
            }catch (Exception e){
                redirectAttributes.addFlashAttribute("result", "notSaved");
            }
        }
        return "redirect:/home";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String fileTooBig(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "You can not upload files bigger than 2 MB!");
        return "redirect:/home";
    }

    @GetMapping("/delete/files/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Authentication auth, RedirectAttributes redirectAttributes){
        Integer userId = userService.getUser(auth.getName()).getUserId();
        fileService.deleteByFileIdAndUserId(fileId, userId);
        redirectAttributes.addFlashAttribute("tab", "nav-files-tab");
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/home";
    }
}
