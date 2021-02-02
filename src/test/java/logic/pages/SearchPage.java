package logic.pages;

import framework.wdm.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class SearchPage extends BasePage{

    private SearchPage() {
    }
    public static SearchPage getInstance(){
        return new SearchPage();
    }

    public WebElement getHighToLowOption(){
        return getDriver().findElement(By.xpath("//a[normalize-space(text())='Discount - High to Low']"));
    }


    public void selectHighToLowOption(){
        click(getHighToLowOption());
        waitUntilSpecificTime(5);
    }
    public void selectNewestArrivals(){
        click(getDriver().findElement(By.xpath("//span[text()='Featured']")));
        click(getDriver().findElement(By.xpath("//a[text()='Newest Arrivals']")));
        waitUntilSpecificTime(5);
    }

    public void triggerSortByText(String text){
        String xpath = String.format("//span[normalize-space(text())='%s']/ancestor::span[@role='button']",text);
        click(getDriver().findElement(By.xpath(xpath)));
    }
    public  void clickItemByIndex(int index) {
        click(getAllAElementsById("dealImage").get(index));
    }

    public  void clickAddToCartByIndex(int index) {
        waitUntilSpecificTime(3);
        click(getAllButtonsByText("Add To Cart").get(index));
    }

    public void goToCart(){
       click(DriverFactory.getInstance().getDriver().findElement(By.xpath("//span[normalize-space(text())='Cart']//ancestor::a[1]")));
    }

    public BigDecimal getPriceFollowIndex(int index){
        waitForPageLoadComplete(10);
        try {
            return parse(getAllDivElementsByClass("dealPriceText").get(index).getText(), Locale.US);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public  void backToPreviousPage() {
        super.backToPreviousPage();
    }

    public void keyInKeywordToSearchBar(String text){
        enterValueByLabel(getInputById("twotabsearchtextbox"),text);
    }

    public void clickSearch(){
        click(getInputById("nav-search-submit-button"));
    }

    public void clickItemByName(String name){
        String xpath =  String.format("//span[normalize-space(text())='%s']/ancestor::a",name);
        click(getDriver().findElement(By.xpath(xpath)));
    }

    public void selectQuantityOfProduct(String amount){
        WebElement quantitySelect= getDriver().findElement(By.xpath("//select[@name='quantity']"));
        selectByVisibleText(quantitySelect,amount);
    }



}
