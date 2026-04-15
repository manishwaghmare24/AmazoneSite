package amazontestscripts;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SearchMob {
	@Test
	public void searchMobile() throws InterruptedException {

		WebDriver driver;
		WebDriverManager.chromedriver().setup();
		String url = System.getProperty("base.url");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.get(url);
		// Thread.sleep(3000);
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Samsung Mobile");
		Thread.sleep(3000);
		List<WebElement> search = driver.findElements(
				By.xpath("//*[@class='left-pane-results-container']//child::div[starts-with(@id,'sac-suggestion')]"));

		for (WebElement s : search) {

			System.out.println("Web Elements present inside search:" + " " + s.getText());

			if (s.getText().equalsIgnoreCase("samsung mobile 5g phone")) {
				Thread.sleep(3000);

				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", s);
				// s.click();
				break;

			}

		}

		// scroll to the Next button

		// driver.findElement(By.xpath("//*[text()='Next']"));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

		for (int i = 1; i <= 4; i++) {

			WebElement nxtButton = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Next']")));

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true)", nxtButton);
			nxtButton.click();
		}

		// name and details configuration of the mobile phone
		WebElement hoverSeller = driver
				.findElement(By.xpath("//div[@id='nav-xshop-container']//child::*[text()='Bestsellers']"));
		StringBuilder sb = new StringBuilder();
		Actions action = new Actions(driver);
		action.moveToElement(hoverSeller);
		action.build().perform();
		int count = 0;
		Thread.sleep(3000);
		List<WebElement> descMobs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//*[@class='puisg-col-inner']//child::h2[starts-with(@class,'a-size-medium')]")));
		// driver.findElements(By.xpath("//*[@class='puisg-col-inner']//child::h2[starts-with(@class,'a-size-medium')]//child::span"));
		// wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='puisg-col-inner']//child::h2[starts-with(@class,'a-size-medium')]")));
		// driver.findElements(By.xpath("//*[@class='puisg-col-inner']//child::h2[starts-with(@class,'a-size-medium')]"));

		System.out.println("Total Elements:" + " " + descMobs.size());
		List<String> detail = new LinkedList<String>();

		for (WebElement descMob : descMobs) {
			Thread.sleep(3000);
			count++;
			// System.out.println("Mobile Information:" + " "+ count+" "+
			// descMob.getText());
			// System.out.println("["+descMob.getText()+"]");
			String text = descMob.getText();
			detail.add(text);
			// sb.append().append("\n");
			// b.append(detail.add(descMob.getText())).append("\n");
			// sb.append(detail.add(descMob.getText())).append("\n");

		}
		System.out.println("Total count:" + " " + count);
		System.out.println(String.join("\n", detail));
		// driver.quit();

		List<WebElement> prices = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//*[@class='puisg-col-inner']//child::span[@class='a-price-whole']")));
		List<String> stringprice = new LinkedList<String>();

		for (WebElement price : prices) {
			String pricetext = price.getText();
			stringprice.add(pricetext.replaceAll("[^a-zA-Z0-9]", ""));
		}

		System.out.println(String.join("\n", stringprice));

		List<Integer> priceint = new LinkedList<Integer>();

		for (String pricestr : stringprice) {

			priceint.add(Integer.parseInt(pricestr));
		}

		System.out.println("Max price:" + Collections.max(priceint));

		LinkedHashMap<String, Integer> productNamePrice = new LinkedHashMap<String, Integer>();
		int size = Math.min(detail.size(), priceint.size());
		for (int i = 0; i < size; i++) {
			productNamePrice.put(detail.get(i), priceint.get(i));
		}

		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : productNamePrice.entrySet()) {
			list.add(entry.getKey() + " ->" + entry.getValue());
		}
		System.out.println(String.join("\n", list));

		WebElement nxtButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Next']")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", nxtButton);
	}

}
