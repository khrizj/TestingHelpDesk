package com.helpdesk.testing;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author Christian Aguilar Alvarado
 */
public class testLogin {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("chrome") String browser) {
//		Create driver
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver = new FirefoxDriver();
                break;

            default:
                System.out.println("Do not know how to start " + browser + ", starting chrome instead");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;
        }

        // maximize browser window
        driver.manage().window().maximize();

        // implicit wait
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(priority = 1)
    public void LoginUsario() throws InterruptedException {
        System.out.println("Starting Login Usuario");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

//		verificaciones:
//              Acceso Usuario
        WebElement loginMessage = driver.findElement(By.id("lbltitulo"));
        String positiveMessage = "Acceso Usuario";
        String currentMessage = loginMessage.getText();
        Assert.assertTrue(currentMessage.equals(positiveMessage), "Actual message does not contain expected message.\nActual Message: " + currentMessage
                + "\nExpected Message: " + positiveMessage);

//		Enviar parametros
        driver.findElement(By.xpath("/html//input[@id='usu_correo']"))
                .sendKeys("test@gmail.com");

        driver.findElement(By.xpath("/html//input[@id='usu_pass']"))
                .sendKeys("test");

//		click "Ingresar" 
        WebElement ingresarButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresarButton.click();

        // implicit wait
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        sleep(500);

//		verificaciones:
//		 new url
        String expectedUrl = "http://calidadsoftware.tk/helpdesk/views/Home/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");

//		 Mensaje de login: Test test presente 
        WebElement successMessage = driver.findElement(By.xpath("/html/body/header[@class='site-header']//div[@class='dropdown dropdown-typical']/a[@href='#']/span[@class='lblcontactonomx']"));
        String expectedMessage = "Test Test";
        String actualMessage = successMessage.getText();

        sleep(1000);

        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        // Close browser
        driver.quit();
    }

    @Test(priority = 2)
    public void LoginSoporte() throws InterruptedException {

        System.out.println("Starting Login Soporte");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

//         verificaciones
//         Link acceso soporte
        WebElement loginMessage = driver.findElement(By.id("btnsoporte"));
        String positiveMessage = "Acceso Soporte";
        String currentMessage = loginMessage.getText();
        Assert.assertTrue(currentMessage.equals(positiveMessage), "Actual message does not contain expected message.\nActual Message: " + currentMessage
                + "\nExpected Message: " + positiveMessage);

        driver.findElement(By.id("btnsoporte")).click();
        sleep(500);

//		verificaciones:
//              Acceso Soporte
        WebElement successMessage = driver.findElement(By.id("lbltitulo"));
        String expectedMessage = "Acceso Soporte";
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.equals(expectedMessage), "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);

        //		Enviar parametros
        driver.findElement(By.xpath("/html//input[@id='usu_correo']"))
                .sendKeys("soporte@gmail.com");

        sleep(500);

        driver.findElement(By.xpath("/html//input[@id='usu_pass']"))
                .sendKeys("soporte");

        sleep(500);
//		click "Ingresar" 
        driver.findElement(By.id("login_form")).click();

    }

    @Test(priority = 3)
    public void CambioContraseña() throws InterruptedException {

        System.out.println("Starting Cambio de Conraseña");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

//         verificaciones
//         Link acceso soporte
        WebElement loginMessage = driver.findElement(By.xpath("/html//form[@id='login_form']//a[@href='reset-password.html']"));
        String positiveMessage = "Cambiar Contraseña";
        String currentMessage = loginMessage.getText();
        Assert.assertTrue(currentMessage.equals(positiveMessage), "Actual message does not contain expected message.\nActual Message: " + currentMessage
                + "\nExpected Message: " + positiveMessage);

        driver.findElement(By.xpath("Cambiar Contraseña")).click();
        
        sleep(1000);

//		verificaciones:
//              Acceso Soporte
        String newURL = "http://calidadsoftware.tk/helpdesk/reset-password.html";
        driver.get(newURL);
        System.out.println("Page is opened.");

        WebElement successMessage = driver.findElement(By.id("lbltitulo"));
        String expectedMessage = "Reset Password";
        String actualMessage = successMessage.getText();
        if (actualMessage.equals(expectedMessage)) {
            System.out.println("");
        }
        Assert.assertTrue(actualMessage.equals(expectedMessage), "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);

        //		Enviar parametros
        driver.findElement(By.xpath("/html//input[@id='usu_pass']"))
                .sendKeys("test2");

//		click "Ingresar" 
        driver.findElement(By.id("submit_form")).click();

    }

//Casos Negativos de Ingreso Contraseña o Usuario invalidos    
    @Test(priority = 4)
    public void wrongPassUsario() throws InterruptedException {
        System.out.println("Starting Login Usuario");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

//		verificaciones:
//              Acceso Usuario
        WebElement loginMessage = driver.findElement(By.id("lbltitulo"));
        String positiveMessage = "Acceso Usuario";
        String currentMessage = loginMessage.getText();
        Assert.assertTrue(currentMessage.equals(positiveMessage), "Actual message does not contain expected message.\nActual Message: " + currentMessage
                + "\nExpected Message: " + positiveMessage);

//		Enviar parametros
        driver.findElement(By.xpath("/html//input[@id='usu_correo']"))
                .sendKeys("test@gmail.com");

        driver.findElement(By.xpath("/html//input[@id='usu_pass']"))
                .sendKeys("wrongPass");

//		click "Ingresar" 
        WebElement ingresarButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresarButton.click();

        // implicit wait
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

//		verificaciones:
//		 new url
        String expectedUrl = "http://calidadsoftware.tk/helpdesk/index.php?m=1";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");

//		 Mensaje de login: Test test presente 
        WebElement successMessage = driver.findElement(By.xpath("//form[@id='login_form']/div[@role='alert']"));
        String expectedMessage = "El Correo y/o Contraseña son Incorrectos.";
        String actualMessage = successMessage.getText();
        // Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not
        // the same as expected");
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);
    }

    @Test(priority = 5)
    public void wrongLoginUsario() throws InterruptedException {
        System.out.println("Starting Login Usuario");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

//		verificaciones:
//              Acceso Usuario
        WebElement loginMessage = driver.findElement(By.id("lbltitulo"));
        String positiveMessage = "Acceso Usuario";
        String currentMessage = loginMessage.getText();
        Assert.assertTrue(currentMessage.equals(positiveMessage), "Actual message does not contain expected message.\nActual Message: " + currentMessage
                + "\nExpected Message: " + positiveMessage);

//		Enviar parametros
        driver.findElement(By.xpath("/html//input[@id='usu_correo']"))
                .sendKeys("wrong@gmail.com");

        driver.findElement(By.xpath("/html//input[@id='usu_pass']"))
                .sendKeys("test");

//		click "Ingresar" 
        WebElement ingresarButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresarButton.click();

        // implicit wait
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

//		verificaciones:
//		 new url
        String expectedUrl = "http://calidadsoftware.tk/helpdesk/index.php?m=1";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");

//		 Mensaje de fallo esta presente
        WebElement successMessage = driver.findElement(By.xpath("//form[@id='login_form']/div[@role='alert']"));
        String expectedMessage = "El Correo y/o Contraseña son Incorrectos.";
        String actualMessage = successMessage.getText();
        // Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not
        // the same as expected");
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);
    }

    @Test(priority = 6)
    public void wrongLoginSoporte() throws InterruptedException {

        System.out.println("Starting Login Soporte");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

//         verificaciones
//         Link acceso soporte
        WebElement loginMessage = driver.findElement(By.id("btnsoporte"));
        String positiveMessage = "Acceso Soporte";
        String currentMessage = loginMessage.getText();
        Assert.assertTrue(currentMessage.equals(positiveMessage), "Actual message does not contain expected message.\nActual Message: " + currentMessage
                + "\nExpected Message: " + positiveMessage);

        driver.findElement(By.id("btnsoporte")).click();

        sleep(500);
//		verificaciones:
//              Acceso Soporte
        WebElement successMessage = driver.findElement(By.id("lbltitulo"));
        String expectedMessage = "Acceso Soporte";
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.equals(expectedMessage), "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);

        //		Enviar parametros
        driver.findElement(By.xpath("/html//input[@id='usu_correo']"))
                .sendKeys("wrongSoporte@gmail.com");

        driver.findElement(By.xpath("/html//input[@id='usu_pass']"))
                .sendKeys("soporte");

//		click "Ingresar" 
        driver.findElement(By.id("login_form")).click();

        sleep(500);

        //		verificaciones
        String expectedUrl = "http://calidadsoftware.tk/helpdesk/#";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");
        sleep(500);

    }
    
    @Test(priority = 7)
    public void wrongPassSoporte() throws InterruptedException {

        System.out.println("Starting Login Soporte");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

//         verificaciones
//         Link acceso soporte
        WebElement loginMessage = driver.findElement(By.id("btnsoporte"));
        String positiveMessage = "Acceso Soporte";
        String currentMessage = loginMessage.getText();
        Assert.assertTrue(currentMessage.equals(positiveMessage), "Actual message does not contain expected message.\nActual Message: " + currentMessage
                + "\nExpected Message: " + positiveMessage);

        driver.findElement(By.id("btnsoporte")).click();

        sleep(500);
//		verificaciones:
//              Acceso Soporte
        WebElement successMessage = driver.findElement(By.id("lbltitulo"));
        String expectedMessage = "Acceso Soporte";
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.equals(expectedMessage), "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);

        //		Enviar parametros
        driver.findElement(By.xpath("/html//input[@id='usu_correo']"))
                .sendKeys("soporte@gmail.com");

        driver.findElement(By.xpath("/html//input[@id='usu_pass']"))
                .sendKeys("wrongPass");

//		click "Ingresar" 
        driver.findElement(By.id("login_form")).click();

        sleep(500);

        //		verificaciones
        String expectedUrl = "http://calidadsoftware.tk/helpdesk/#";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");
        sleep(500);

    }

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
