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
public class contadorCasos {

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

    //Leer cantidad de casos 
    @Test(priority = 1)
    public void cuentaTiqueteUsuario() throws InterruptedException {
        String contadores;
        System.out.println("Starting Login Usuario");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

//		Enviar parametros
        driver.findElement(By.xpath("/html//input[@id='usu_correo']"))
                .sendKeys("test@gmail.com");

        driver.findElement(By.xpath("/html//input[@id='usu_pass']"))
                .sendKeys("test");

        sleep(500);
//		click "Ingresar" 
        WebElement ingresarButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresarButton.click();

        //Guardar contadores cantidad Total de tiquetes
        WebElement totalTiquetes = driver.findElement(By.xpath("/html//div[@id='lbltotal']"));
        WebElement totalTiqAbiertos = driver.findElement(By.xpath("/html//div[@id='lbltotalabierto']"));
        WebElement totalTiqCerrados = driver.findElement(By.xpath("/html//div[@id='lbltotalcerrado']"));
        sleep(500);
        int saldoTiquetes = Integer.parseInt(totalTiquetes.getText());
        int saldoTiquetesAbiertos = Integer.parseInt(totalTiqAbiertos.getText());
        int saldoTiquetesCerrados = Integer.parseInt(totalTiqCerrados.getText());
        sleep(500);

        //Click Nuevo ticket
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\NuevoTicket\\']/span[@class='lbl']")).click();
        sleep(500);
//		verificaciones:
//		 new url        
        String esperadaUrl = "http://calidadsoftware.tk/helpdesk/views/NuevoTicket/";
        String nuevaUrl = driver.getCurrentUrl();
        Assert.assertEquals(nuevaUrl, esperadaUrl, "Actual page url is not the same as expected");
        sleep(500);

//      Manejo del dropdown
        Select dropCategoria = new Select(driver.findElement(By.id("cat_id")));
        dropCategoria.selectByVisibleText("Software");

        driver.findElement(By.xpath("/html//input[@id='tick_titulo']"))
                .sendKeys("Tiquete Prueba");

        driver.findElement(By.xpath("/html//form[@id='ticket_form']/div[3]//div[@class='note-editable panel-body']"))
                .sendKeys("Creacion Prueba");

        sleep(500);

        // Click en "guardar"
        driver.findElement(By.xpath("//form[@id='ticket_form']//button[@name='action']")).click();

        //Click en "OK"
        driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary.confirm")).click();
        sleep(500);

        //Click en "Inicio" el contador debe aumentar en 1 para total de tiquetes y para abiertos pero no debe variar para "Cerrados"
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\Home\\']/span[@class='lbl']")).click();
        WebElement nuevoTotalTiquetes = driver.findElement(By.xpath("/html//div[@id='lbltotal']"));
        WebElement nuevoTotalTiqAbiertos = driver.findElement(By.xpath("/html//div[@id='lbltotalabierto']"));
        WebElement nuevoTotalTiqCerrados = driver.findElement(By.xpath("/html//div[@id='lbltotalcerrado']"));
        sleep(500);
        int nuevoSaldoTiquetes = Integer.parseInt(nuevoTotalTiquetes.getText());
        int nuevoSaldoTiquetesAbiertos = Integer.parseInt(nuevoTotalTiqAbiertos.getText());
        int nuevoSaldoTiquetesCerrados = Integer.parseInt(nuevoTotalTiqCerrados.getText());
        sleep(500);

        //verifiacion
        if (nuevoSaldoTiquetes == (saldoTiquetes + 1) && nuevoSaldoTiquetesAbiertos == (saldoTiquetesAbiertos + 1) && nuevoSaldoTiquetesCerrados == saldoTiquetesCerrados) {
            contadores = "Contadores correctos";
        } else {
            contadores = "Contadores incorrectos";
        }
        sleep(500);

        String expectedMessage = "Contadores correctos";
        String actualMessage = contadores;
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);
        sleep(500);

        //Click en "Consultar Tiquete"
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\ConsultarTicket\\']/span[@class='lbl']")).click();
        sleep(500);

        //Click en ver tiquete
        driver.findElement(By.xpath("//table[@id='ticket_data']/tbody/tr[3]//button[@type='button']//i[@class='fa fa-eye']")).click();
        sleep(500);

        String windowHandle = driver.getWindowHandle();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        System.out.println(tabs.size());
        driver.switchTo().window(tabs.get(1));

        //Click en "Cerrar Tiquete"
        driver.findElement(By.id("btncerrarticket")).click();
        sleep(500);
        //Click en "Si"
        driver.findElement(By.xpath("//body/div[9]//button[.='Si']")).click();
        //Click "OK"
        driver.findElement(By.xpath("//body/div[9]//button[.='OK']")).click();

        sleep(500);
        driver.close();
        driver.switchTo().window(tabs.get(0));

        //Click en "Inicio" el contador NO DEBE aumentar para total de tiquetes, DISMINUIR en 1 "Abiertos" y aumentar en 1 en "Cerrados"
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\Home\\']/span[@class='lbl']")).click();
        WebElement nuevoTotalTiquetes2 = driver.findElement(By.xpath("/html//div[@id='lbltotal']"));
        WebElement nuevoTotalTiqAbiertos2 = driver.findElement(By.xpath("/html//div[@id='lbltotalabierto']"));
        WebElement nuevoTotalTiqCerrados2 = driver.findElement(By.xpath("/html//div[@id='lbltotalcerrado']"));
        sleep(500);
        int nuevoSaldoTiquetes2 = Integer.parseInt(nuevoTotalTiquetes2.getText());
        int nuevoSaldoTiquetesAbiertos2 = Integer.parseInt(nuevoTotalTiqAbiertos2.getText());
        int nuevoSaldoTiquetesCerrados2 = Integer.parseInt(nuevoTotalTiqCerrados2.getText());

        //verifiacion
        if (nuevoSaldoTiquetes == nuevoSaldoTiquetes2 && nuevoSaldoTiquetesAbiertos2 == (nuevoSaldoTiquetesAbiertos - 1) && nuevoSaldoTiquetesCerrados2 == (nuevoSaldoTiquetesCerrados + 1)) {
            contadores = "Contadores correctos";
        } else {
            contadores = "Contadores incorrectos";
        }
        String esperadoMessage = "Contadores correctos";
        String generadoMessage = contadores;
        Assert.assertTrue(generadoMessage.contains(esperadoMessage),
                "Actual message does not contain expected message.\nActual Message: " + generadoMessage
                + "\nExpected Message: " + esperadoMessage);

    }

    @Test(priority = 2)
    public void cuentaTiqueteAdmin() throws InterruptedException {
        String contadores;
        System.out.println("Starting Login Soporte");

//		test de la pagina
        String url = "http://calidadsoftware.tk/helpdesk/";
        driver.get(url);
        System.out.println("Page is opened.");

        WebElement soporteButton = driver.findElement(By.xpath("/html//a[@id='btnsoporte']"));
        soporteButton.click();

//		Enviar parametros
        driver.findElement(By.id("usu_correo"))
                .sendKeys("soporte@gmail.com");

        driver.findElement(By.id("usu_pass"))
                .sendKeys("soporte");

//		click "Ingresar" 
        WebElement ingresaButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresaButton.click();

        // implicit wait
        sleep(500);

        //Guardo los contadores actuales
        WebElement totalTiquetes = driver.findElement(By.xpath("/html//div[@id='lbltotal']"));
        WebElement totalTiqAbiertos = driver.findElement(By.xpath("/html//div[@id='lbltotalabierto']"));
        WebElement totalTiqCerrados = driver.findElement(By.xpath("/html//div[@id='lbltotalcerrado']"));
        sleep(500);
        int saldoTiquetes = Integer.parseInt(totalTiquetes.getText());
        int saldoTiquetesAbiertos = Integer.parseInt(totalTiqAbiertos.getText());
        int saldoTiquetesCerrados = Integer.parseInt(totalTiqCerrados.getText());
        sleep(500);

        //Cerrar sesion
        driver.findElement(By.id("dd-user-menu")).click();
        driver.findElement(By.xpath("/html/body/header[@class='site-header']//div[@class='dropdown-menu dropdown-menu-right']/a[3]")).click();
        sleep(500);

        //Inicia sesion usuario Test
        System.out.println("Starting Login Usuario");

//		test de la pagina
        driver.get(url);
        System.out.println("Page is opened.");

//		Enviar parametros
        driver.findElement(By.xpath("/html//input[@id='usu_correo']"))
                .sendKeys("test@gmail.com");

        driver.findElement(By.xpath("/html//input[@id='usu_pass']"))
                .sendKeys("test");

        sleep(500);
//		click "Ingresar" 
        WebElement ingresarButton = driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']"));
        ingresarButton.click();

        //Click Nuevo ticket
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\NuevoTicket\\']/span[@class='lbl']")).click();
        sleep(500);
//		verificaciones:
//		 new url        
        String esperadaUrl = "http://calidadsoftware.tk/helpdesk/views/NuevoTicket/";
        String nuevaUrl = driver.getCurrentUrl();
        Assert.assertEquals(nuevaUrl, esperadaUrl, "Actual page url is not the same as expected");
        sleep(500);

//      Manejo del dropdown
        Select dropCategoria = new Select(driver.findElement(By.id("cat_id")));
        dropCategoria.selectByVisibleText("Software");

        driver.findElement(By.xpath("/html//input[@id='tick_titulo']"))
                .sendKeys("Segundo Tiquete Prueba");

        driver.findElement(By.xpath("/html//form[@id='ticket_form']/div[3]//div[@class='note-editable panel-body']"))
                .sendKeys("Creacion Prueba");

        sleep(500);

        // Click en "guardar"
        driver.findElement(By.xpath("//form[@id='ticket_form']//button[@name='action']")).click();

        //Click en "OK"
        driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary.confirm")).click();
        sleep(500);

        //Click cerrar sesion
        driver.findElement(By.id("dd-user-menu")).click();
        driver.findElement(By.xpath("/html/body/header[@class='site-header']//div[@class='dropdown-menu dropdown-menu-right']/a[3]")).click();
        sleep(500);

        //Inicia sesion como soporte
        System.out.println("Starting Login Soporte");

        driver.findElement(By.xpath("/html//a[@id='btnsoporte']")).click();

//		Enviar parametros
        driver.findElement(By.id("usu_correo"))
                .sendKeys("soporte@gmail.com");

        driver.findElement(By.id("usu_pass"))
                .sendKeys("soporte");

//		click "Ingresar" 
        driver.findElement(By.xpath("//form[@id='login_form']/button[@type='submit']")).click();

        // implicit wait
        sleep(500);

        //Click en "Inicio" el contador debe aumentar en 1 para total de tiquetes y para abiertos pero no debe variar para "Cerrados"
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\Home\\']/span[@class='lbl']")).click();
        WebElement nuevoTotalTiquetes = driver.findElement(By.xpath("/html//div[@id='lbltotal']"));
        WebElement nuevoTotalTiqAbiertos = driver.findElement(By.xpath("/html//div[@id='lbltotalabierto']"));
        WebElement nuevoTotalTiqCerrados = driver.findElement(By.xpath("/html//div[@id='lbltotalcerrado']"));
        sleep(500);
        int nuevoSaldoTiquetes = Integer.parseInt(nuevoTotalTiquetes.getText());
        int nuevoSaldoTiquetesAbiertos = Integer.parseInt(nuevoTotalTiqAbiertos.getText());
        int nuevoSaldoTiquetesCerrados = Integer.parseInt(nuevoTotalTiqCerrados.getText());
        sleep(500);

        //verifiacion
        if (nuevoSaldoTiquetes == (saldoTiquetes + 1) && nuevoSaldoTiquetesAbiertos == (saldoTiquetesAbiertos + 1) && nuevoSaldoTiquetesCerrados == saldoTiquetesCerrados) {
            contadores = "Contadores correctos";
        } else {
            contadores = "Contadores incorrectos";
        }
        sleep(500);

        String expectedMessage = "Contadores correctos";
        String actualMessage = contadores;
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Actual message does not contain expected message.\nActual Message: " + actualMessage
                + "\nExpected Message: " + expectedMessage);
        sleep(500);

        //Click en "Consultar Tiquete"
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\ConsultarTicket\\']/span[@class='lbl']")).click();
        sleep(500);

        //Click en ver tiquete
        driver.findElement(By.xpath("//table[@id='ticket_data']/tbody/tr[7]//button[@type='button']//i[@class='fa fa-eye']")).click();
        sleep(500);

        String windowHandle = driver.getWindowHandle();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        System.out.println(tabs.size());
        driver.switchTo().window(tabs.get(1));

        //Click en "Cerrar Tiquete"
        driver.findElement(By.id("btncerrarticket")).click();
        sleep(500);
        //Click en "Si"
        driver.findElement(By.xpath("//body/div[9]//button[.='Si']")).click();
        //Click "OK"
        driver.findElement(By.xpath("//body/div[9]//button[.='OK']")).click();

        sleep(500);
        driver.close();
        driver.switchTo().window(tabs.get(0));

        //Click en "Inicio" el contador NO DEBE aumentar para total de tiquetes, DISMINUIR en 1 "Abiertos" y aumentar en 1 en "Cerrados"
        driver.findElement(By.xpath("//body/nav[@class='side-menu']//ul[@class='side-menu-list']//a[@href='..\\Home\\']/span[@class='lbl']")).click();
        WebElement nuevoTotalTiquetes2 = driver.findElement(By.xpath("/html//div[@id='lbltotal']"));
        WebElement nuevoTotalTiqAbiertos2 = driver.findElement(By.xpath("/html//div[@id='lbltotalabierto']"));
        WebElement nuevoTotalTiqCerrados2 = driver.findElement(By.xpath("/html//div[@id='lbltotalcerrado']"));
        sleep(500);
        int nuevoSaldoTiquetes2 = Integer.parseInt(nuevoTotalTiquetes2.getText());
        int nuevoSaldoTiquetesAbiertos2 = Integer.parseInt(nuevoTotalTiqAbiertos2.getText());
        int nuevoSaldoTiquetesCerrados2 = Integer.parseInt(nuevoTotalTiqCerrados2.getText());

        //verifiacion
        if (nuevoSaldoTiquetes == nuevoSaldoTiquetes2 && nuevoSaldoTiquetesAbiertos2 == (nuevoSaldoTiquetesAbiertos - 1) && nuevoSaldoTiquetesCerrados2 == (nuevoSaldoTiquetesCerrados + 1)) {
            contadores = "Contadores correctos";
        } else {
            contadores = "Contadores incorrectos";
        }
        String esperadoMessage = "Contadores correctos";
        String generadoMessage = contadores;
        Assert.assertTrue(generadoMessage.contains(esperadoMessage),
                "Actual message does not contain expected message.\nActual Message: " + generadoMessage
                + "\nExpected Message: " + esperadoMessage);

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
            e.printStackTrace();
        }
    }

}
