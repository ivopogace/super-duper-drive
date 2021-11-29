package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName} ")
    File findFileByFileName(String fileName);

    @Select("Select * FROM FILES WHERE userId = #{userId}")
    List<File> findAllByUserId(Integer userId);

    @Insert("INSERT INTO FILES (fileId, fileName, contentType, fileSize, userId, fileData)" +
            " VALUES(#{fileId}, #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void save(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
    void delete(Integer fileId, Integer userId);
}
