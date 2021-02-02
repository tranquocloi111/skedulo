package logic.pages;

import framework.wdm.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

public class ProductDetailPage extends BasePage{

    private ProductDetailPage() {
    }
    public static ProductDetailPage getInstance(){
        return new ProductDetailPage();
    }

    public void getQuantitySelect(int index){
        Select quanitySelect=new Select (DriverFactory.getInstance().getDriver().findElement(By.xpath("//select[@name='quantity']")));
        quanitySelect.selectByVisibleText(String.valueOf(index));
    }

    public  void backToPreviousPage() {
        super.backToPreviousPage();
    }
    public BigDecimal priceInsideBuyBox(){
        try {
            return parse(getDriver().findElement(By.xpath("//span[@id='price_inside_buybox']")).getText(), Locale.US);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public void clickAddToCartBtn(){
       click(getInputById("add-to-cart-button"));
    }

}
