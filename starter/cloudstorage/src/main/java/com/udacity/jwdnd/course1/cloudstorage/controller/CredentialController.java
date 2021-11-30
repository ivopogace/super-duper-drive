package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
    @Value("${mySecretKey}")
    private String key;

    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final NoteService noteService;
    private final FileService fileService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService, NoteService noteService, FileService fileService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @PostMapping("/credentials")
    public String createUpdateCredentialByUserId(Authentication auth,
                                                 @ModelAttribute("credentialForm") Credential credential,
                                                 @ModelAttribute("noteForm") Note newNote,
                                                 Model model){
        Integer userId = userService.getUser(auth.getName()).getUserId();
        Credential existingCredential = credentialService.getByCredentialId(credential.getCredentialId());
        Credential duplicateCredential = credentialService.getByUrlAndUsername(credential.getUrl(), credential.getUsername());
        if (duplicateCredential != null){
            model.addAttribute("error", "This Credential already exists!");
        }
        else if (existingCredential == null){
            credentialService.createCredentialByUserId(credential, userId);
            model.addAttribute("result", "success");
        }else {
            String updatedPassword = encryptionService.encryptValue(credential.getPassword(), key);
            credentialService.updateCredentialByUserId(existingCredential.getCredentialId(), credential.getUrl(), credential.getUsername(), updatedPassword, userId);
            model.addAttribute("result", "success");
        }

        model.addAttribute("tab", "nav-credentials-tab");
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", credentialService.getCredentialListByUserId(userId));
        model.addAttribute("notes", noteService.getNoteListByUserId(userId));
        model.addAttribute("files", fileService.getFileListByUserId(userId));
        return "home";
    }

    @GetMapping("/delete/credentials/{credentialId}")
    public String deleteCredentialByIdAndUserId(@PathVariable Integer credentialId, RedirectAttributes redirectAttributes, Authentication auth){
        Integer userId = userService.getUser(auth.getName()).getUserId();
        credentialService.deleteByCredentialIdAndUserId(credentialId, userId);
        redirectAttributes.addFlashAttribute("tab", "nav-credentials-tab");
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/home";
    }
}
