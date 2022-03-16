package com.helpdesk.testing;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Christian Aguilar Alvarado
 */
@Test
public class testCrearTiquete {

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
    public void crearTiqueteUsuario() throws InterruptedException {
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

        sleep(3000);
//		click "Ingresar" 
        WebElement ingresarButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresarButton.click();

        // implicit wait
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

//		verificaciones:
//		 new url
        String expectedUrl = "http://calidadsoftware.tk/helpdesk/views/Home/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");

//      Verificacion de pagina
        WebElement successMessage = driver.findElement(By.xpath("/html/body/header[@class='site-header']//div[@class='dropdown dropdown-typical']/a[@href='#']/span[@class='lblcontactonomx']"));
        String expectedMessage = "Test Test";
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);

//Click Nuevo ticket
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\NuevoTicket\\']/span[@class='lbl']")).click();
//		verificaciones:
//		 new url        
        String esperadaUrl = "http://calidadsoftware.tk/helpdesk/views/NuevoTicket/";
        String nuevaUrl = driver.getCurrentUrl();
        Assert.assertEquals(nuevaUrl, esperadaUrl, "Actual page url is not the same as expected");

//      Manejo del dropdown
        Select dropCategoria = new Select(driver.findElement(By.id("cat_id")));
        dropCategoria.selectByVisibleText("Software");

        driver.findElement(By.xpath("/html//input[@id='tick_titulo']"))
                .sendKeys("Tiquete Prueba");

        driver.findElement(By.xpath("/html//form[@id='ticket_form']/div[3]//div[@class='note-editable panel-body']"))
                .sendKeys("Creacion Prueba");

        sleep(3000);
        
        driver.findElement(By.xpath("//form[@id='ticket_form']//button[@name='action']")).click();

        //Verificaciones
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\ConsultarTicket\\']/span[@class='lbl']")).click();

        WebElement ticketSuccessMessage = driver.findElement(By.xpath("//table[@id='ticket_data']/tbody/tr[3]/td[.='Software']"));
        String ticketExpectedMessage = "Software";
        String ticketActualMessage = ticketSuccessMessage.getText();
        Assert.assertTrue(ticketActualMessage.contains(ticketExpectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + ticketActualMessage
                + "\nExpected Message: " + ticketExpectedMessage);

        WebElement tituloSuccessMessage = driver.findElement(By.xpath("//table[@id='ticket_data']/tbody/tr[3]/td[.='Tiquete Prueba']"));
        String tituloExpectedMessage = "Tiquete Prueba";
        String tituloActualMessage = tituloSuccessMessage.getText();
        Assert.assertTrue(tituloActualMessage.contains(tituloExpectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + tituloActualMessage
                + "\nExpected Message: " + tituloExpectedMessage);

        WebElement estadoSuccessMessage = driver.findElement(By.xpath("//table[@id='ticket_data']/tbody/tr[3]//span[@class='label label-pill label-success']"));
        String estadoExpectedMessage = "Abierto";
        String estadoActualMessage = estadoSuccessMessage.getText();
        Assert.assertTrue(estadoActualMessage.contains(estadoExpectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + estadoActualMessage
                + "\nExpected Message: " + estadoExpectedMessage);

    }

    @Test(priority = 2)
    public void verTiqueteUsuario() throws InterruptedException {
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

//      Verificacion de pagina
        WebElement successMessage = driver.findElement(By.xpath("/html/body/header[@class='site-header']//div[@class='dropdown dropdown-typical']/a[@href='#']/span[@class='lblcontactonomx']"));
        String expectedMessage = "Test Test";
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);

//Click Consultar Ticket
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\ConsultarTicket\\']/span[@class='lbl']")).click();
//		verificaciones:
//		 new url        
        String esperadaUrl = "http://calidadsoftware.tk/helpdesk/views/ConsultarTicket/";
        String nuevaUrl = driver.getCurrentUrl();
        Assert.assertEquals(nuevaUrl, esperadaUrl, "Actual page url is not the same as expected");

        driver.findElement(By.xpath("//table[@id='ticket_data']/tbody/tr[3]//button[@type='button']//i[@class='fa fa-eye']")).click();

        // implicit wait
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Cambiar al tab nuevo
        String windowHandle = driver.getWindowHandle();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        System.out.println(tabs.size());
        driver.switchTo().window(tabs.get(1));
        sleep(3000);
        driver.close();
        driver.switchTo().window(tabs.get(0));

        sleep(2000);

    }

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
