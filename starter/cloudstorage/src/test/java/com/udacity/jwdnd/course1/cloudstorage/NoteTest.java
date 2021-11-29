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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTest {

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

    public void getNoteTab(){
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();" , driver.findElement(By.xpath("//a[@href='#nav-notes']")));
    }

    /**
     * Write a Selenium test that logs in an existing user, creates a note and verifies that the note details are visible in the note list.
     * @throws InterruptedException
     */
    @Test
    public void testAddNewNote() throws InterruptedException {
        signupLoginAddNoteLogoutUser1();
        Thread.sleep(2000);
        loginPage.login("ivopogace", "123456");
        Thread.sleep(2000);
        getNoteTab();
        //check if we already have a note to an existing user.
        Assertions.assertEquals(1,driver.findElements(By.id("tableNoteTitle")).size());
        // add second note and return to note tab
        Thread.sleep(2000);
        homePage.addSecondNoteUser1();
        //check if the new note is added.
        Assertions.assertEquals(2,driver.findElements(By.id("tableNoteTitle")).size());
        // print our noteTitles to the console
        Thread.sleep(2000);
        List<WebElement> noteTitleList = driver.findElements(By.id("tableNoteTitle"));
        for (WebElement noteTitle : noteTitleList) {
            System.out.println("Note: " + noteTitle.getAttribute("innerHTML"));
        }
    }

    /**
     * Write a Selenium test that logs in an existing user with existing notes,
     * clicks the edit note button on an existing note, changes the note data, saves the changes,
     * and verifies that the changes appear in the note list.
     * @throws InterruptedException
     */
    @Test
    public void testUpdateExistingNote() throws InterruptedException {
        signupLoginAddNoteLogoutUser2();
        loginPage.login("ivopogace1", "123456");
        Thread.sleep(2000);
        getNoteTab();
        //check if we already have a note to update
        Assertions.assertEquals(1,driver.findElements(By.id("tableNoteTitle")).size());
        String existingNoteTitle = driver.findElement(By.id("tableNoteTitle")).getAttribute("innerHTML");
        // print our existing noteTitle list to the console
        System.out.println("Note: " + existingNoteTitle);
        Thread.sleep(2000);
        //update existing note and return to note tab
        homePage.updateExistingNoteUser2();
        Thread.sleep(2000);
        // print our updated noteTitle list to the console
        String updatedNoteTitle = driver.findElement(By.id("tableNoteTitle")).getAttribute("innerHTML");
        // print our updated noteTitle list to the console
        System.out.println("Note: " + updatedNoteTitle);
        //check if update happened
        Assertions.assertNotEquals(existingNoteTitle,updatedNoteTitle);
    }

    /**
     * Write a Selenium test that logs in an existing user with existing notes,
     * clicks the delete note button on an existing note,
     * and verifies that the note no longer appears in the note list.
     * @throws InterruptedException
     */
    @Test
    public void testDeleteExistingNote() throws InterruptedException {
        signupLoginAddNoteLogoutUser3();
        loginPage.login("ivopogace2", "123456");
        Thread.sleep(2000);
        getNoteTab();
        Thread.sleep(2000);
        //check if we already have a note to delete
        Assertions.assertEquals(1,driver.findElements(By.id("tableNoteTitle")).size());
        homePage.deleteExistingNoteUser3();
        Thread.sleep(2000);
        // print our updated noteTitle list to the console after deletion (should be empty)
        List<WebElement> updatedNoteTitleList = driver.findElements(By.id("tableNoteTitle"));
        if (updatedNoteTitleList.isEmpty()) {
            System.out.println("The note list is empty after deletion");
        }
        //check if note is deleted
        Assertions.assertEquals(0,driver.findElements(By.id("tableNoteTitle")).size());
    }

    public void signupLoginAddNoteLogoutUser1() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        signupPage.signUp();
        loginPage.login("ivopogace", "123456");
        Thread.sleep(1000);
        getNoteTab();
        Thread.sleep(1000);
        // add first note and return to note tab
        homePage.addFirstNoteUser1();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='btn btn-secondary float-right']")).submit(); //log out
        Thread.sleep(2000);
    }
    public void signupLoginAddNoteLogoutUser2() throws InterruptedException {
        //user 2
        driver.get("http://localhost:" + this.port + "/signup");
        signupPage.signUpUser2();
        loginPage.login("ivopogace1", "123456");
        Thread.sleep(1000);
        getNoteTab();
        Thread.sleep(1000);
        // add first note and return to note tab
        homePage.addFirstNoteUser2();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='btn btn-secondary float-right']")).submit(); //log out
        Thread.sleep(2000);
    }
    public void signupLoginAddNoteLogoutUser3() throws InterruptedException {
        //user 2
        driver.get("http://localhost:" + this.port + "/signup");
        signupPage.signUpUser3();
        loginPage.login("ivopogace2", "123456");
        Thread.sleep(1000);
        getNoteTab();
        Thread.sleep(1000);
        // add first note and return to note tab
        homePage.addFirstNoteUser3();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='btn btn-secondary float-right']")).submit(); //log out
        Thread.sleep(2000);
    }

    /**
     * Write a Selenium test that logs in an existing user, creates a note and verifies that the note details are visible in the note list.
     *
     * Write a Selenium test that logs in an existing user with existing notes,
     * clicks the edit note button on an existing note, changes the note data, saves the changes,
     * and verifies that the changes appear in the note list.
     *
     * Write a Selenium test that logs in an existing user with existing notes, clicks the delete note button on an existing note, and verifies that the note no longer appears in the note list.
     */
}
