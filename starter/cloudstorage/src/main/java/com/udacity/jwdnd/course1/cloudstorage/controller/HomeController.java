package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String getHomePage(Authentication auth,
                              @ModelAttribute("noteForm") Note newNote,
                              @ModelAttribute("fileForm") File file,
                              @ModelAttribute("credentialForm") Credential credential,
                              Model model){
        User user = userService.getUser(auth.getName());
        if(user==null){
            return "redirect:/login";
        }else {
            model.addAttribute("encryptionService", encryptionService);
            model.addAttribute("credentials", credentialService.getCredentialListByUserId(user.getUserId()));
            model.addAttribute("notes", noteService.getNoteListByUserId(user.getUserId()));
            model.addAttribute("files", fileService.getFileListByUserId(user.getUserId()));
            return "home";
        }
    }
}
