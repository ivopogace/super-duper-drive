package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class NoteController {
    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public NoteController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/notes")
    public String createUpdateNoteByUserId(Authentication auth,
                                           @ModelAttribute("noteForm") Note newNote,
                                           @ModelAttribute("credentialForm") Credential credential,
                                           Model model) {
        Integer userId = userService.getUser(auth.getName()).getUserId();
        Note existingNote = noteService.getNoteById(newNote.getId());
        Note duplicateNote = noteService.getNoteByTitleAndDescription(newNote.getTitle(), newNote.getDescription());
        if (newNote.getDescription().length() > 1000){
            model.addAttribute("error", "Description should be max 1000 char long!");}
        else if (duplicateNote != null){
            model.addAttribute("error", "This Note already exists!");
        }
        else if (existingNote == null) {
            noteService.createNoteByUserId(newNote, userId);
            model.addAttribute("result", "success");
        } else {
            noteService.updateNoteByUserId(existingNote.getId(), newNote.getTitle(), newNote.getDescription(), userId);
            model.addAttribute("result", "success");
        }

        model.addAttribute("tab", "nav-notes-tab");
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("files", fileService.getFileListByUserId(userId));
        model.addAttribute("credentials", credentialService.getCredentialListByUserId(userId));
        model.addAttribute("notes", noteService.getNoteListByUserId(userId));
        return "home";
    }

    @GetMapping("/delete/notes/{id}")
    public String deleteNoteByIdAndUserId(@PathVariable Integer id, Authentication auth, RedirectAttributes redirectAttributes){
        Integer userId = userService.getUser(auth.getName()).getUserId();
        noteService.deleteNoteByIdAndUserId(id, userId);
        redirectAttributes.addFlashAttribute("tab", "nav-notes-tab");
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/home";
    }
}
