package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> findAllNotesByUserId(Integer userId);

    @Select("SELECT * FROM NOTES WHERE id = #{id}")
    Note findNoteById(Integer id);

    @Insert("INSERT INTO NOTES (title, description, userid) " +
            "VALUES(#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Note note);

    @Update("UPDATE NOTES SET title = #{title}, description = #{description} WHERE id = #{id} AND userId = #{userId}")
    void update(Integer id, String title, String description, Integer userId);

    @Delete("DELETE FROM NOTES WHERE id = #{id} AND userId = #{userId}")
    void delete(Integer id, Integer userId);
}
