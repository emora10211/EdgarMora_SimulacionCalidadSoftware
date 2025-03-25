/**
 * @author Edgar Mora Miranda
 * @version 1.0
 * @fecha 21-03-2025
 * @curso SC-405 Calidad del Software
 * @simulación Mi primera ejecución de pruebas
 */
package Pruebas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Métodos que representan acciones en la página
    public void escribirUsuario(String usuario) {
        driver.findElement(By.id("user-name")).sendKeys(usuario);
    }

    public void escribirClave(String clave) {
        driver.findElement(By.id("password")).sendKeys(clave);
    }

    public void hacerLogin() {
        driver.findElement(By.id("login-button")).click();
    }

    public boolean loginExitoso() {
        WebElement titulo = driver.findElement(By.className("product_label"));
        return titulo.getText().contains("Products");
    }
}
