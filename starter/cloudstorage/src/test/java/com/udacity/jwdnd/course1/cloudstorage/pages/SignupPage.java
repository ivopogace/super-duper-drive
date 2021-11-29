package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "submit-button")
    private WebElement signUpButton;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstNameField;

    @FindBy(id = "inputLastName")
    private WebElement inputLastNameField;

    @FindBy(id = "inputUsername")
    private WebElement inputUsernameField;

    @FindBy(id = "inputPassword")
    private WebElement inputPasswordField;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signUp() throws InterruptedException {
        inputFirstNameField.sendKeys("Ivo");
        inputLastNameField.sendKeys("Pogace");
        inputUsernameField.sendKeys("ivopogace");
        inputPasswordField.sendKeys("123456");
        Thread.sleep(1000);
        signUpButton.submit();
    }

    public void signUpUser2() throws InterruptedException {
        inputFirstNameField.sendKeys("Ivo");
        inputLastNameField.sendKeys("Pogace");
        inputUsernameField.sendKeys("ivopogace1");
        inputPasswordField.sendKeys("123456");
        Thread.sleep(1000);
        signUpButton.submit();
    }

    public void signUpUser3() throws InterruptedException {
        inputFirstNameField.sendKeys("Ivo");
        inputLastNameField.sendKeys("Pogace");
        inputUsernameField.sendKeys("ivopogace2");
        inputPasswordField.sendKeys("123456");
        Thread.sleep(1000);
        signUpButton.submit();
    }
}
