package logic.pages;

import framework.wdm.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class CartPage extends BasePage {

    private CartPage() {
    }

    public static CartPage getInstance() {
        return new CartPage();
    }


    public void selectQuantityOfProduct(String amount, int index) {
        List<WebElement> quantitySelect = getDriver().findElements(By.xpath("//select[@name='quantity']"));
        selectByVisibleText(quantitySelect.get(index), amount);
    }

    public String getSubTotal() {
        waitUntilSpecificTime(3);
        return getDriver().findElement(By.xpath("//span[contains(normalize-space(text()),'Subtotal')][1]")).getText();
    }

    public BigDecimal getToTalPay() {
        try {
            return parse(getDriver().findElement(By.xpath("//span[contains(normalize-space(text()),'Subtotal')]/following-sibling::span/span")).getText(), Locale.US);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getPriceByIndex(int index) {
        List<WebElement> pricesList = getDriver().findElements(By.xpath("//div[@class='sc-list-item-content']//span[contains(@class,'sc-price')]"));
        try {
            return parse(pricesList.get(index).getText(), Locale.US);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigDecimal roundDouble(double d, int places) {

        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal;
    }

}
