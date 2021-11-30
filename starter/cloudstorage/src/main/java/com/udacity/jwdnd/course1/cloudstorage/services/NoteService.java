package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /**
     * Find all notes by userId
     * @param userId userId
     * @return List of notes
     */
    public List<Note> getNoteListByUserId(Integer userId){
        return noteMapper.findAllNotesByUserId(userId);
    }

    /**
     * Find a note by notedId
     * @param noteId notedId
     * @return Note
     */
    public Note getNoteById(Integer noteId) {
        return this.noteMapper.findNoteById(noteId);
    }

    /**
     * Check if we have previously added this note
     * @param title title
     * @param description description
     * @return Existing Note
     */
    public Note getNoteByTitleAndDescription(String title, String description) {
        return this.noteMapper.findNoteByTitleAndDescription(title, description);
    }

    /**
     * Save a new note by userId
     * @param note note
     * @param userId userId
     */
    public void createNoteByUserId(Note note, Integer userId){
        note.setUserId(userId);
        this.noteMapper.save(note);
    }

    /**
     * Save changes to a current note by userId
     * @param id id
     * @param title title
     * @param description description
     * @param userId userId
     */
    public void updateNoteByUserId(Integer id, String title, String description, Integer userId) {
        this.noteMapper.update(id,title, description, userId);
    }

    /**
     * Delete note by id and userId
     * @param id id
     * @param userId userId
     */
    public void deleteNoteByIdAndUserId(Integer id, Integer userId){
        this.noteMapper.delete(id,userId);
    }
}
