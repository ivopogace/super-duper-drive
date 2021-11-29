package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Tests for User Signup, Login, and Unauthorized Access Restrictions.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.signupPage = new SignupPage(driver);
        this.loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    /**
     *Unauthenticated users can access only the login and signup pages.
     */
    @Test
    public void testUnauthenticatedUserAccess() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
        Thread.sleep(1000);

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
        Thread.sleep(1000);

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
        Thread.sleep(1000);

        driver.get("http://localhost:" + this.port + "/result");
        Assertions.assertEquals("Login", driver.getTitle());
        Thread.sleep(1000);
    }

    /**
     * Sign up a new user, logs in, verifies that the home page is accessible,
     * logs out, and verifies that the home page is no longer accessible.
     */
    @Test
    public void testUserSignupLoginFlow() throws InterruptedException {
        String username = "ivopogace";
        String password = "123456";

        driver.get("http://localhost:" + this.port + "/signup");
        signupPage.signUp();
        Thread.sleep(1000);
        loginPage.login(username, password);
        Assertions.assertEquals("Home", driver.getTitle());
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='btn btn-secondary float-right']")).submit(); //log out
        Assertions.assertEquals("Login", driver.getTitle());
        Thread.sleep(1000);
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(1000);
        Assertions.assertEquals("Login", driver.getTitle());
    }
}
