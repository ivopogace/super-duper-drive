package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = ${userId}")
    List<Credential> findAllByUserId(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential findByCredentialId(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url} AND username = #{username}")
    Credential findByUrlAndUsername(String url, String username);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void save(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialId = #{credentialId} AND userId = #{userId}")
    void updateCredentialByUserId(Integer credentialId, String url, String username, String password, Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId} AND userId = #{userId}")
    void delete(Integer credentialId, Integer userId);
}
