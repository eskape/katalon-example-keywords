package sample

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import groovy.transform.CompileStatic

public class ShadowDOM {


	@Keyword
	def static void shadowInput(TestObject testObject, String value) {
		WebElement webElement = WebUI.findWebElement(testObject, (int) GlobalVariable.waitPresentTimeout)
		WebElement shadow = expandRootElement(webElement);
		WebElement input = shadow.findElement(By.cssSelector("input"))
		input.clear();
		input.sendKeys(value)
	}

	@Keyword
	def static void shadowTextArea(TestObject testObject, String value) {
		WebElement webElement = WebUI.findWebElement(testObject, (int) GlobalVariable.waitPresentTimeout)
		WebElement shadow = expandRootElement(webElement);
		WebElement textarea = shadow.findElement(By.cssSelector("textarea"))
		textarea.clear()
		textarea.sendKeys(value)
	}

	@Keyword
	def static void shadowButtonClick(TestObject testObject) {
		WebElement webElement = WebUI.findWebElement(testObject, (int) GlobalVariable.waitPresentTimeout)
		WebElement shadow = expandRootElement(webElement);
		WebElement button = shadow.findElement(By.xpath("//button"))
		button.click()
	}

	@CompileStatic
	static WebElement expandRootElement(WebElement element) {
		WebElement shadowDom = (WebElement) WebUI.executeJavaScript("return arguments[0].shadowRoot", Arrays.asList(element))
		return shadowDom;
	}
}
