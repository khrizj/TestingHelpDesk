package com.helpdesk.testing;

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
@Test
public class crearEliminarUsuarios {

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
        sleep(2000);
    }

    @Test(priority = 1)
    public void crearUsario() throws InterruptedException {
        System.out.println("Starting Login Soporte");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

        driver.findElement(By.xpath("/html//a[@id='btnsoporte']")).click();

//		Enviar parametros
        driver.findElement(By.id("usu_correo"))
                .sendKeys("soporte@gmail.com");

        driver.findElement(By.id("usu_pass"))
                .sendKeys("soporte");

//		click "Ingresar" 
        WebElement ingresarButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresarButton.click();

        // implicit wait
        sleep(500);

//      Mensaje de login: Soporte Soporte presente 
        WebElement successMessage = driver.findElement(By.xpath("/html/body/header[@class='site-header']//div[@class='dropdown dropdown-typical']/a[@href='#']/span[@class='lblcontactonomx']"));
        String expectedMessage = "Soporte Soporte";
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);

        //Click en Mantenimiento Usuario
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\MntUsuario\\']/span[@class='lbl']")).click();

        //Click nuevo Registro
        driver.findElement(By.xpath("/html//button[@id='btnnuevo']")).click();
        sleep(500);

        driver.findElement(By.xpath("/html//input[@id='usu_nom']")).click();
        sleep(500);
        driver.findElement(By.xpath("/html//input[@id='usu_nom']")).sendKeys("Usuario1");
        sleep(500);

        driver.findElement(By.xpath("/html//input[@id='usu_ape']")).click();
        sleep(500);
        driver.findElement(By.xpath("/html//input[@id='usu_ape']")).sendKeys("Prueba");
        sleep(500);

        driver.findElement(By.id("usu_correo")).click();
        sleep(500);
        driver.findElement(By.id("usu_correo")).sendKeys("test1@gmail.com");
        sleep(500);

        driver.findElement(By.id("usu_pass")).click();
        sleep(500);
        driver.findElement(By.id("usu_pass")).sendKeys("test1");
        sleep(500);
        //      Manejo del dropdown
//                List<WebElement> element = driver.findElements(By.xpath("/html//span[@id='select2-rol_id-container']"));
//                sleep(1000);
//                element.get(0).click();
//                sleep(1000);

        driver.findElement(By.xpath("/html//button[@id='#']")).click();
        sleep(500);

////             Seleccionar "OK"
        driver.findElement(By.xpath("//body/div[6]//button[.='OK']")).click();
        sleep(500);

        //              Verificar creacion de usuario
        WebElement newUserMessage = driver.findElement(By.xpath("//table[@id='usuario_data']/tbody/tr[4]/td[@class='sorting_1']"));
        String positiveMessage = "Usuario1";
        String currentMessage = newUserMessage.getText();
        Assert.assertTrue(currentMessage.equals(positiveMessage), "Actual message does not contain expected message.\nActual Message: " + currentMessage
                + "\nExpected Message: " + positiveMessage);

        WebElement newApellidoMessage = driver.findElement(By.xpath("//table[@id='usuario_data']/tbody/tr[4]/td[@class='sorting_1']"));
        String apellidoMessage = "Usuario1";
        String apellidoActualMessage = newApellidoMessage.getText();
        Assert.assertTrue(apellidoActualMessage.equals(apellidoMessage), "Actual message does not contain expected message.\nActual Message: " + apellidoActualMessage
                + "\nExpected Message: " + apellidoMessage);

        WebElement newRolMessage = driver.findElement(By.xpath("//table[@id='usuario_data']/tbody/tr[4]/td[@class='sorting_1']"));
        String rolMessage = "Usuario1";
        String rolActualMessage = newRolMessage.getText();
        Assert.assertTrue(rolActualMessage.equals(rolMessage), "Actual message does not contain expected message.\nActual Message: " + rolActualMessage
                + "\nExpected Message: " + rolMessage);

    }

    /*
    ****************************************************************************
    ****************************************************************************
    ****************************************************************************
     */
    @Test(priority = 2)
    public void eliminarUsario() throws InterruptedException {
        System.out.println("Starting Login Soporte");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

        driver.findElement(By.xpath("/html//a[@id='btnsoporte']")).click();

//		Enviar parametros
        driver.findElement(By.id("usu_correo"))
                .sendKeys("soporte@gmail.com");

        driver.findElement(By.id("usu_pass"))
                .sendKeys("soporte");

//		click "Ingresar" 
        WebElement ingresarButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresarButton.click();

        // implicit wait
        sleep(3000);

//      Mensaje de login: Soporte Soporte presente 
        WebElement successMessage = driver.findElement(By.xpath("/html/body/header[@class='site-header']//div[@class='dropdown dropdown-typical']/a[@href='#']/span[@class='lblcontactonomx']"));
        String expectedMessage = "Soporte Soporte";
        String actualMessage = successMessage.getText();
        // Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not
        // the same as expected");
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);

        //Click en Mantenimiento Usuario
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\MntUsuario\\']/span[@class='lbl']")).click();
        sleep(500);

        //Click en eliminar
        driver.findElement(By.xpath("//table[@id='usuario_data']/tbody/tr[4]/td[7]/button[@type='button']//i[@class='fa fa-trash']")).click();
        sleep(500);

        //Click en "Si"
        driver.findElement(By.xpath("//body/div[6]//button[.='Si']")).click();
        sleep(500);

        //Click en "OK"
        driver.findElement(By.xpath("//body/div[6]//button[.='OK']")).click();

        //              Verificar eliminacion de usuario
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\MntUsuario\\']/span[@class='lbl']")).click();
        sleep(4000);
    }

    /*
    ****************************************************************************
    ****************************************************************************
    ****************************************************************************
     */
    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        // Close browser
        driver.quit();
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
