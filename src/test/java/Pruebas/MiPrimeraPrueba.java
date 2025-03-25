/**
 * @author Edgar Mora Miranda
 * @version 1.0
 * @fecha 21-03-2025
 * @curso SC-405 Calidad del Software
 * @simulación Mi primera ejecución de pruebas
 */
package Pruebas;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertTrue;

public class MiPrimeraPrueba {

    private WebDriver driver;

    /**
     * Este método se corre antes de cada prueba.
     * Aquí lo que hacemos es alistar el navegador (Chrome), abrirlo y entrar al sitio que vamos a probar.
     */
    @Before
    public void setUp() {
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");

        // Se indica la ruta del driver de Chrome
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/Driver/chromedriver.exe");

        driver = new ChromeDriver(co);
        driver.manage().window().maximize(); // Pantalla completa para evitar fallos de visibilidad
        driver.get("https://www.saucedemo.com/v1/"); // Cargamos el sitio de pruebas
    }

    /**
     * miPrimerTest: Probar que un usuario pueda iniciar sesión con datos válidos.
     */
    @Test
    public void miPrimerTest() {
        WebElement txtUsername = driver.findElement(By.id("user-name"));
        txtUsername.sendKeys("standard_user");

        WebElement txtPassword = driver.findElement(By.id("password"));
        txtPassword.sendKeys("secret_sauce");

        WebElement btnLogin = driver.findElement(By.id("login-button"));
        btnLogin.click();

        WebElement titulo = driver.findElement(By.className("product_label"));
        assertTrue("No se encontró el texto 'Products'. Puede que el login haya fallado.",
                titulo.getText().contains("Products"));
    }

    /**
     * miSegundoTest: Validar que el sistema saque un mensaje de error si uno se equivoca con la clave.
     */
    @Test
    public void miSegundoTest() {
        WebElement txtUsername = driver.findElement(By.id("user-name"));
        txtUsername.sendKeys("standard_user");

        WebElement txtPassword = driver.findElement(By.id("password"));
        txtPassword.sendKeys("clave_incorrecta");

        WebElement btnLogin = driver.findElement(By.id("login-button"));
        btnLogin.click();

        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        assertTrue("No apareció el mensaje de error esperado.",
                errorMsg.getText().contains("Username and password do not match"));
    }

    /**
     * miTercerTest: Probar que cuando se agregan productos al carrito, se muestren correctamente.
     */
    @Test
    public void miTercerTest() {
        WebElement txtUsername = driver.findElement(By.id("user-name"));
        WebElement txtPassword = driver.findElement(By.id("password"));
        WebElement btnLogin = driver.findElement(By.id("login-button"));

        txtUsername.sendKeys("standard_user");
        txtPassword.sendKeys("secret_sauce");
        btnLogin.click();

        if (driver.findElements(By.className("btn_inventory")).size() >= 2) {
            driver.findElements(By.className("btn_inventory")).get(0).click();
            driver.findElements(By.className("btn_inventory")).get(1).click();
        } else {
            assertTrue("No hay suficientes productos para la prueba.", false);
        }

        WebElement carrito = driver.findElement(By.className("shopping_cart_link"));
        carrito.click();

        int items = driver.findElements(By.className("cart_item")).size();
        assertTrue("No se agregaron bien los productos al carrito.", items == 2);
    }

    /**
     * miCuartoTest: Verificar que el filtro de precio de mayor a menor funcione.
     */
    @Test
    public void miCuartoTest() {
        WebElement txtUsername = driver.findElement(By.id("user-name"));
        WebElement txtPassword = driver.findElement(By.id("password"));
        WebElement btnLogin = driver.findElement(By.id("login-button"));

        txtUsername.sendKeys("standard_user");
        txtPassword.sendKeys("secret_sauce");
        btnLogin.click();

        WebElement filtro = driver.findElement(By.className("product_sort_container"));
        filtro.sendKeys("Price (high to low)");

        WebElement primerPrecio = driver.findElements(By.className("inventory_item_price")).get(0);
        String precioTexto = primerPrecio.getText();

        if (precioTexto.contains("49.99")) {
            System.out.println("Filtro aplicado bien. Precio encontrado: " + precioTexto);
            assertTrue(true);
        } else {
            System.out.println("Algo falló con el filtro. Precio mostrado: " + precioTexto);
            assertTrue("No se mostró el producto más caro primero.", false);
        }
    }

    /**
     * miQuintoTest: Validar que el usuario pueda cerrar sesión desde el menú lateral.
     */
    @Test
    public void miQuintoTest() {
        WebElement txtUsername = driver.findElement(By.id("user-name"));
        WebElement txtPassword = driver.findElement(By.id("password"));
        WebElement btnLogin = driver.findElement(By.id("login-button"));

        txtUsername.sendKeys("standard_user");
        txtPassword.sendKeys("secret_sauce");
        btnLogin.click();

        WebElement menuButton = driver.findElement(By.className("bm-burger-button"));
        menuButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement logoutBtn = driver.findElement(By.id("logout_sidebar_link"));
        logoutBtn.click();

        WebElement campoUsuario = driver.findElement(By.id("user-name"));
        assertTrue("No se redirigió al login después del logout.", campoUsuario.isDisplayed());
    }

    /**
     * miSextoTest: Verifica que al hacer scroll hasta el pie de página, se vea el logo del robot.
     */
    @Test
    public void miSextoTest() {
        System.out.println("Iniciando test: miSextoTest - Scroll hasta el footer para validar el logo del robot");

        WebElement txtUsername = driver.findElement(By.id("user-name"));
        WebElement txtPassword = driver.findElement(By.id("password"));
        WebElement btnLogin = driver.findElement(By.id("login-button"));

        txtUsername.sendKeys("standard_user");
        txtPassword.sendKeys("secret_sauce");
        btnLogin.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement robotLogo = driver.findElement(By.className("footer_robot"));
        assertTrue("No se encontró el logo del robot después del scroll.", robotLogo.isDisplayed());

        System.out.println("Test miSextoTest finalizado correctamente: el logo del robot es visible.");
    }
    
    /**
     * miSeptimoTestConPOM, valido el login usando el patrón Page Object Model (POM).
     */
    @Test
    public void miSeptimoTestConPOM() {
        LoginPage login = new LoginPage(driver);
        login.escribirUsuario("standard_user");
        login.escribirClave("secret_sauce");
        login.hacerLogin();

        assertTrue("No se validó el login con POM correctamente", login.loginExitoso());
    }

}
