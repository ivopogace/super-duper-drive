package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home1Page {
    //note
    @FindBy(id = "btnAddNote")
    private WebElement btnAddNote;
    @FindBy(id = "btnDeleteNote")
    private WebElement btnDeleteNote;
    @FindBy(xpath ="//button[@class='btn btn-success mybutton']")
    private WebElement btnEditNote;
    @FindBy(id = "note-title")
    private WebElement noteTitle;
    @FindBy(id = "note-description")
    private WebElement noteDescription;
    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    //credential
    @FindBy(id = "btnAddCredential")
    private WebElement btnAddCredential;
    @FindBy(id = "btnEditCredential")
    private WebElement btnEditCredential;
    @FindBy(id = "btnDeleteCredential")
    private WebElement btnDeleteCredential;
    @FindBy(id = "credential-url")
    private WebElement credentialUrl;
    @FindBy(id = "credential-username")
    private WebElement credentialUsername;
    @FindBy(id = "credential-password")
    private WebElement credentialPassword;
    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    public Home1Page(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //Note Methods

    public void addFirstNoteUser1() throws InterruptedException {
        btnAddNote.click();
        Thread.sleep(1000);
        noteTitle.sendKeys("User 1 to-do");
        noteDescription.sendKeys("User 1 first todo");
        noteSubmit.submit();
    }

    public void addFirstNoteUser2() throws InterruptedException {
        btnAddNote.click();
        Thread.sleep(1000);
        noteTitle.sendKeys("User 2 to-do");
        noteDescription.sendKeys("User 2 first todo");
        noteSubmit.submit();
    }

    public void addFirstNoteUser3() throws InterruptedException {
        btnAddNote.click();
        Thread.sleep(1000);
        noteTitle.sendKeys("User 3 to-do");
        noteDescription.sendKeys("User 3 first todo");
        noteSubmit.submit();
    }

    public void addSecondNoteUser1() throws InterruptedException {
        Thread.sleep(1000);
        btnAddNote.click();
        Thread.sleep(1000);
        noteTitle.sendKeys("User 1 second to-do");
        noteDescription.sendKeys("User 1 add second todo");
        noteSubmit.submit();
    }

    public void updateExistingNoteUser2() throws InterruptedException {
        Thread.sleep(1000);
        btnEditNote.click();
        Thread.sleep(1000);
        noteTitle.sendKeys(" updated");
        noteDescription.sendKeys(" updated");
        Thread.sleep(1000);
        noteSubmit.submit();
    }

    public void deleteExistingNoteUser3() throws InterruptedException {
        Thread.sleep(1000);
        btnDeleteNote.click();
        Thread.sleep(2000);
    }

    //Credential Methods

    public void addFirstCredentialUser1() throws InterruptedException {
        btnAddCredential.click();
        Thread.sleep(1000);
        credentialUrl.sendKeys("http://localhost:8080/login");
        credentialUsername.sendKeys("user-1");
        credentialPassword.sendKeys("123456");
        credentialSubmit.submit();
        Thread.sleep(1000);
    }

    public void addFirstCredentialUser2() throws InterruptedException {
        btnAddCredential.click();
        Thread.sleep(1000);
        credentialUrl.sendKeys("http://localhost:8080/login");
        credentialUsername.sendKeys("user-2");
        credentialPassword.sendKeys("123456");
        credentialSubmit.submit();
        Thread.sleep(1000);
    }

    public void addFirstCredentialUser3() throws InterruptedException {
        btnAddCredential.click();
        Thread.sleep(1000);
        credentialUrl.sendKeys("http://localhost:8080/login");
        credentialUsername.sendKeys("user-2");
        credentialPassword.sendKeys("123456");
        credentialSubmit.submit();
        Thread.sleep(1000);
    }

    public void addSecondCredentialUser1() throws InterruptedException {
        btnAddCredential.click();
        Thread.sleep(1000);
        credentialUrl.sendKeys("https://www.facebook.com/login");
        credentialUsername.sendKeys("user-1");
        credentialPassword.sendKeys("123456789");
        credentialSubmit.submit();
        Thread.sleep(1000);
    }

    public void updateExistingCredentialUser2() throws InterruptedException {
        Thread.sleep(1000);
        btnEditCredential.click();
        Thread.sleep(1000);
        credentialUrl.clear();
        credentialUrl.sendKeys("https://de.linkedin.com/login");
        credentialUsername.clear();
        credentialUsername.sendKeys("user-2-update");
        credentialPassword.sendKeys("789");
        Thread.sleep(2000);
        credentialSubmit.submit();
        Thread.sleep(1000);
    }

    public void deleteExistingCredentialUser3() throws InterruptedException {
        Thread.sleep(1000);
        btnDeleteCredential.click();
        Thread.sleep(2000);
    }

    public WebElement getBtnEditCredential() {
        return btnEditCredential;
    }
}
