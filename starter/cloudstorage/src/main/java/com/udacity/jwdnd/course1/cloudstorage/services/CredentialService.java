package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    @Value("${mySecretKey}")
    private String key;

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    /**
     * Find all credentials by userId
     * @param userId userId
     * @return List of Credentials
     */
    public List<Credential> getCredentialListByUserId(Integer userId) {
        return this.credentialMapper.findAllByUserId(userId);
    }

    /**
     * Find credential by credentialId
     * @param credentialId
     * @return Credential
     */
    public Credential getByCredentialId(Integer credentialId) {
        return this.credentialMapper.findByCredentialId(credentialId);
    }

    /**
     * Save a new credential by userId
     * @param credential credential
     * @param userId userId
     */
    public void createCredentialByUserId(Credential credential, Integer userId) {
        credential.setUserId(userId);
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        this.credentialMapper.save(credential);
    }

    /**
     * Save changes to a current credential by userId
     * @param credentialId credentialId
     * @param url url
     * @param username username
     * @param password password
     * @param userId userId
     */
    public void updateCredentialByUserId(Integer credentialId, String url, String username, String password, Integer userId) {
        this.credentialMapper.updateCredentialByUserId(credentialId, url, username, password, userId);
    }

    /**
     * Delete credential by credentialId
     * @param credentialId credentialId
     */
    public void deleteByCredentialIdAndUserId(Integer credentialId, Integer userId) {
        this.credentialMapper.delete(credentialId, userId);
    }

}
