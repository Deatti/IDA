import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class WebCapture{
	
	
	public static void main(String args[]) throws Exception{
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2();
		Thread3 t3 = new Thread3();
		t1.start();
		t2.start();
		t3.start();
		/*System.setProperty("webdriver.gecko.driver","/root/WebCapture/driver/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://sovrin.org/people/");
		driver.get("https://www.naver.com");
		driver.get("https://www.daum.net/");
		//Thread.sleep(2000);
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		ImageIO.write(fpScreenshot.getImage(), "JPG", new File("/root/WebCapture/result/"+System.currentTimeMillis()+"FullPageScreenshot.jpg"));
		
	}*/
	}
	
	

}
class Thread1 extends Thread{
	public void run() {
		System.setProperty("webdriver.gecko.driver","/root/WebCapture/driver/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://sovrin.org/people/");
		//driver.get("https://www.naver.com");
		//driver.get("https://www.daum.net/");
		//Thread.sleep(2000);
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		try {
			ImageIO.write(fpScreenshot.getImage(), "JPG", new File("/root/WebCapture/result/"+System.currentTimeMillis()+"FullPageScreenshot.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class Thread2 extends Thread{
	public void run() {
		System.setProperty("webdriver.gecko.driver","/root/WebCapture/driver/geckodriver");
		WebDriver driver = new FirefoxDriver();
		//driver.get("https://sovrin.org/people/");
		driver.get("https://www.facebook.com/groups/codingeverybody/?fref=nf");
		//driver.get("https://www.daum.net/");
		//Thread.sleep(2000);
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		try {
			ImageIO.write(fpScreenshot.getImage(), "JPG", new File("/root/WebCapture/result/"+System.currentTimeMillis()+"FullPageScreenshot.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class Thread3 extends Thread{
	public void run() {
		System.setProperty("webdriver.gecko.driver","/root/WebCapture/driver/geckodriver");
		WebDriver driver = new FirefoxDriver();
		//driver.get("https://sovrin.org/people/");
		driver.get("https://www.naver.com");
		//driver.get("https://www.daum.net/");
		//Thread.sleep(2000);
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		try {
			ImageIO.write(fpScreenshot.getImage(), "JPG", new File("/root/WebCapture/result/"+System.currentTimeMillis()+"FullPageScreenshot.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
