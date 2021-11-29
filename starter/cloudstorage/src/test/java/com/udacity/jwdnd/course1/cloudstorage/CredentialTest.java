package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.Home1Page;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private Home1Page homePage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.homePage = new Home1Page(driver);
        this.signupPage = new SignupPage(driver);
        this.loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    public void getCredentialTab(){
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();" , driver.findElement(By.xpath("//a[@href='#nav-credentials']")));
    }

    /**
     * Write a Selenium test that logs in an existing user, creates a credential and verifies
     * that the credential details are visible in the credential list.
     * @throws InterruptedException
     */
    @Test
    public void testAddNewCredential() throws InterruptedException {
        signupLoginAddCredentialLogoutUser1();
        Thread.sleep(2000);
        loginPage.login("ivopogace", "123456");
        Thread.sleep(2000);
        getCredentialTab();
        //check if we already have a credential to an existing user.
        Assertions.assertEquals(1,driver.findElements(By.id("tableCredentialUrl")).size());
        // add second credential and return to credential tab
        Thread.sleep(2000);
        homePage.addSecondCredentialUser1();

        //check if the new credential is added.
        Assertions.assertEquals(2,driver.findElements(By.id("tableCredentialUrl")).size());
        // print our credentialUrl to the console
        Thread.sleep(2000);
        List<WebElement> credentialPasswordList = driver.findElements(By.id("tableCredentialPassword"));
        for (WebElement credentialPassword : credentialPasswordList) {
            // Print encrypted passwords in the console
            System.out.println("Credential Password: " + credentialPassword.getAttribute("innerHTML"));
            // Check if both of our passwords shown in the list are encrypted
            assertNotEquals("123456", credentialPassword.getAttribute("tableCredentialPassword"));
            assertNotEquals("123456789", credentialPassword.getAttribute("tableCredentialPassword"));
        }
        //verify that when we press edit credential button on the first element in the list, password is shown(decrypted)
        homePage.getBtnEditCredential().click();
        Thread.sleep(2000);
    }

    /**
     * Write a Selenium test that logs in an existing user with existing credentials,
     * clicks the edit credential button on an existing credential, changes the credential data, saves the changes,
     * and verifies that the changes appear in the credential list.
     * @throws InterruptedException
     */
    @Test
    public void testUpdateExistingCredential() throws InterruptedException {
        signupLoginAddCredentialLogoutUser2();
        loginPage.login("ivopogace1", "123456");
        Thread.sleep(2000);
        getCredentialTab();
        //check if we already have a credential to update
        Assertions.assertEquals(1,driver.findElements(By.id("tableCredentialUrl")).size());
        String existingCredentialUrl = driver.findElement(By.id("tableCredentialUrl")).getAttribute("innerHTML");
        // print our existing noteTitle list to the console
        System.out.println("Credential Url: " + existingCredentialUrl);
        Thread.sleep(2000);
        //update existing credential and return to credential tab
        homePage.updateExistingCredentialUser2();
        Thread.sleep(2000);
        String updatedCredentialUrl = driver.findElement(By.id("tableCredentialUrl")).getAttribute("innerHTML");
        // print our updated credential url list to the console
        System.out.println("Credential Url: " + updatedCredentialUrl);
        //check if update happened
        Assertions.assertNotEquals(existingCredentialUrl,updatedCredentialUrl);
        //check if the password is decrypted when edit button is pressed
        String updatedCredentialPassword = driver.findElement(By.id("btnEditCredential")).getAttribute("password");
        Assertions.assertEquals("123456789", updatedCredentialPassword);
    }

    /**
     * Write a Selenium test that logs in an existing user with existing credentials,
     * clicks the delete credential button on an existing credential,
     * and verifies that the credential no longer appears in the credential list.
     * @throws InterruptedException
     */
    @Test
    public void testDeleteExistingCredential() throws InterruptedException {
        signupLoginAddCredentialLogoutUser3();
        loginPage.login("ivopogace2", "123456");
        Thread.sleep(2000);
        getCredentialTab();
        Thread.sleep(2000);
        //check if we already have a credential to delete
        Assertions.assertEquals(1,driver.findElements(By.id("tableCredentialUrl")).size());
        homePage.deleteExistingCredentialUser3();
        Thread.sleep(2000);
        // print our updated credential url list to the console after deletion (should be empty)
        List<WebElement> updatedCredentialUrlList = driver.findElements(By.id("tableCredentialUrl"));
        if (updatedCredentialUrlList.isEmpty()) {
            System.out.println("The credential url list is empty after deletion");
        }
        //check if credential is deleted
        Assertions.assertEquals(0,driver.findElements(By.id("tableCredentialUrl")).size());
    }


    public void signupLoginAddCredentialLogoutUser1() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        signupPage.signUp();
        loginPage.login("ivopogace", "123456");
        Thread.sleep(1000);
        getCredentialTab();
        Thread.sleep(1000);
        // add first credential and return to credential tab
        homePage.addFirstCredentialUser1();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='btn btn-secondary float-right']")).submit(); //log out
        Thread.sleep(2000);
    }
    public void signupLoginAddCredentialLogoutUser2() throws InterruptedException {
        //user 2
        driver.get("http://localhost:" + this.port + "/signup");
        signupPage.signUpUser2();
        loginPage.login("ivopogace1", "123456");
        Thread.sleep(1000);
        getCredentialTab();
        Thread.sleep(1000);
        // add first credential and return to credential tab
        homePage.addFirstCredentialUser2();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='btn btn-secondary float-right']")).submit(); //log out
        Thread.sleep(2000);
    }
    public void signupLoginAddCredentialLogoutUser3() throws InterruptedException {
        //user 2
        driver.get("http://localhost:" + this.port + "/signup");
        signupPage.signUpUser3();
        loginPage.login("ivopogace2", "123456");
        Thread.sleep(1000);
        getCredentialTab();
        Thread.sleep(1000);
        // add first credential and return to credential tab
        homePage.addFirstCredentialUser3();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='btn btn-secondary float-right']")).submit(); //log out
        Thread.sleep(2000);
    }
}
