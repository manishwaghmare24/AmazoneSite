package amazontestscripts;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class verifyLinksTest {
       @Test
	  public void verifyLinks() {
		  
		  
		  WebDriver driver;
			WebDriverManager.chromedriver().setup();
			
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			driver.get("https://www.hindustantimes.com/");
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			List<WebElement> links = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));
		//	System.out.println(links.size());
			int linkcount = 0;
			int statuscodefail = 0;
			int statuscodepass = 0;
			for(WebElement link: links) {
				String linktext = link.getText();
				if(!linktext.isEmpty() && !linktext.isBlank())
				{	linkcount++;
				System.out.println("Links:" + linktext.trim());
				}
				}
				
			System.out.println(linkcount);
			
			
			for(WebElement linkele:links) {
				
				String linkurl = linkele.getAttribute("href");
				
				System.out.println(linkurl);
				
				if(linkurl == null || linkurl.isEmpty()) {
					System.out.println("Empty or null url");
					continue;
				}
				
				
				try {
					
					URL linkUrl2 = new URL(linkurl);
					URLConnection urlconn = linkUrl2.openConnection();
					HttpURLConnection conn = (HttpURLConnection) urlconn;
					conn.setRequestMethod("HEAD");
					conn.connect();
					int responsecode = conn.getResponseCode();
					
					if(responsecode >= 400) {
						System.out.println("URL link is broke:" +" "+ linkurl +" "+ responsecode);
						statuscodefail++;
					}else {
						System.out.println("URL link is working:" +" "+ linkurl +" "+ responsecode);
						statuscodepass++;
					}
					
				}catch(Exception e){
					System.out.println("Exception for link:" + linkurl + "->" + e.getMessage());
				}
			}
			System.out.println("Status code failed:"+ " "+ statuscodefail);
			System.out.println("status code passws:" +" " + statuscodepass);
			driver.quit();
	  }

}
