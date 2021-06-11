package ink.lch.config.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class JavaScript {

    public boolean webSnap(String url, String fileName) {
        //获取网址
        //String fileName = "cnblogs_8027778";
        //String url = "https://www.cnblogs.com/my-blogs-for-everone/p/8027778.html";

        //调用chrome driver
        System.setProperty("webdriver.chrome.driver", "/home/software/chromedriver");

        //setting chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless").addArguments("--disable-gpu").addArguments("--no-sandbox");

        //调用chrome
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // get web from chrome driver
        driver.get(url);

        // setting width and height
        Dimension dimension = new Dimension(1920, 1080);
        driver.manage().window().setSize(dimension);

        File screenShot = driver.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShot, new File("/home/snap/" + fileName + ".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
