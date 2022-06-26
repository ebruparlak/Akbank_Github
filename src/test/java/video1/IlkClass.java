package video1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import javax.swing.*;
import java.time.Duration;

import java.awt.*;
import java.io.File;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import static io.netty.handler.codec.rtsp.RtspHeaders.Values.URL;

public class IlkClass {
    private ScreenRecorder screenRecorder;

    public static void main (String[] args) throws InterruptedException {

        video1.IlkClass screenVideo = new video1.IlkClass();
        try {
            screenVideo.startRecording();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        WebDriverManager.chromedriver().setup();
        WebDriver driver =new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        driver.get("http://www.akbank.com");
        driver.findElement(By.id("vl-close")).click();

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        WebElement kredi_hesaplama = driver.findElement(By.xpath("//a[@id='ctl00_ucFooterMenu_rptMain_ctl02_rptChild_ctl00_linkChild']"));
        kredi_hesaplama.click();
        Thread.sleep(1000);

        ((JavascriptExecutor)driver).executeScript("scroll(0,400)");

        WebElement tutar = driver.findElement(By.xpath("//input[@id='t-credit-price']"));
        tutar.clear();
        tutar.sendKeys("50.000");
        Thread.sleep(3000);

        WebElement sigortasizCheck = driver.findElement(By.xpath("//div[@id='ctl00_ctl43_g_26e16d7b_ef03_46b5_a437_2bc263507602']/div/div/div[2]/div/div/div/div/fieldset/div/div/a"));
        sigortasizCheck.click();
        Thread.sleep(1000);

        for (int i = 0; i <4; i++) {
            WebElement backButton = driver.findElement(By.xpath("//div[@id='ctl00_ctl43_g_26e16d7b_ef03_46b5_a437_2bc263507602']/div/div/div[2]/div/div/div/div/div[2]/div[3]/a[2]"));
            backButton.click();
            //Thread.sleep(1000);
        }
        Thread.sleep(2000);
        WebElement h_detaylari =driver.findElement(By.xpath("//a[@data-function='calculate']"));
          h_detaylari.click();
        Thread.sleep(2000);

        WebElement kontrolTable = driver.findElement(By.xpath("//section/div[2]/table/tbody/tr[2]/td"));
        kontrolTable.getText();

        if (kontrolTable.getText() == "Aylık Maliyet Oranı"){
            Thread.sleep(1000);
        }

        WebElement o_plani =driver.findElement(By.xpath("//a[@id='accordion2']"));
        o_plani.click();
        Thread.sleep(1000);

        WebElement element =driver.findElement(By.xpath("//div[@id='content-2']/div/div/table/tbody/tr[21]/td"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor)driver).executeScript("scroll(0,800)");
        Thread.sleep(2000);

        try {
            screenVideo.stopRecording();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void startRecording() throws Exception
    {

        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        this.screenRecorder = new ScreenRecorder(gc,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(16),
                        QualityKey, 1.0f,
                        KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                        FrameRateKey, Rational.valueOf(30)),
                null);
        this.screenRecorder.start();
    }
    public void stopRecording() throws Exception
    {
        this.screenRecorder.stop();
    }

}


