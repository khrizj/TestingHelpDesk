package com.helpdesk.testing;


import java.util.ArrayList;
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
public class crearComentarios {
    
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
        sleep(1000);
    }
    
    @Test(priority = 1)
    public void revisarComentario() throws InterruptedException {
        System.out.println("Starting Login Usuario");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

//		Enviar parametros
        driver.findElement(By.id("usu_correo"))
                .sendKeys("test@gmail.com");
        
        driver.findElement(By.id("usu_pass"))
                .sendKeys("test");

//		click "Ingresar" 
        WebElement ingresarButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresarButton.click();

        // implicit wait
        sleep(1000);

        //Seleccionar Consultar ticket
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\ConsultarTicket\\']/span[@class='lbl']")).click();

        //Selecciona Icono de "VER"
        sleep(500);
        driver.findElement(By.cssSelector("tr:nth-of-type(2)  .btn.btn-inline.btn-primary.btn-sm.ladda-buttom  .fa.fa-eye")).click();
        sleep(500);
        
        String windowHandle = driver.getWindowHandle();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        System.out.println(tabs.size());
        driver.switchTo().window(tabs.get(1));
        sleep(1000);
        
                //Ingreso de datos en "Ingrese su duda o consulta"
        driver.findElement(By.xpath("/html//div[@id='pnldetalle']/div[@class='row']/div[1]//div[@class='note-editable panel-body']")).sendKeys("Prueba de Comentarios");
        sleep(1000);
        
        //Click en Enviar
        driver.findElement(By.xpath("/html//button[@id='btnenviar']")).click();
        
        
        //Verificar que el comentario esta presente
        
        WebElement successMessage = driver.findElement(By.xpath("//section[@id='lbldetalle']/article[@class='activity-line-item box-typical']//section[@class='activity-line-action']//p[.='Prueba de Comentarios']"));
        String expectedMessage = "Prueba de Comentarios";
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);
        
        driver.close();
        driver.switchTo().window(tabs.get(0));

        sleep(500);
    
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
