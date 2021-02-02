package logic.pages;

import framework.wdm.DriverFactory;
import framework.wdm.WdManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class BasePage {

    public BasePage() {
        PageFactory.initElements(DriverFactory.getInstance().getDriver(), this);
    }

    //region Useful actions
    public boolean switchWindow(String title, boolean isParent) {
        if (!isParent) {
            try {
                Set<String> availableWindows = getDriver().getWindowHandles();
                if (!availableWindows.isEmpty()) {
                    for (String windowId : availableWindows) {
                        if (getDriver().switchTo().window(windowId).getTitle().equals(title)) {
                            getDriver().manage().window().maximize();
                            Thread.sleep(2000);
                            return true;
                        }
                    }
                }
            } catch (Throwable ex) {
                getDriver().switchTo().window(title);

            }
        } else {
            getDriver().switchTo().window(title);
            getDriver().manage().window().maximize();
        }
        return false;
    }

    /**
     * @param tbl table which contains labels and values
     * @param lbl label to fill value in
     * @param val value to be filled
     */
    protected void enterValueByLabel(WebElement tbl, String lbl, String val) {
        WebElement ele =
                tbl.findElement(By.xpath("//tr/td[@class='label' and contains(text(),'" + lbl + "')]/following-sibling::td/input"));
        //ele.click();
        // ele.clear();
        ele.sendKeys(val);
    }

    protected void enterValueByLabel(String lbl, String val) {
        WebElement ele =
                getDriver().findElement(By.xpath("//tr/td[@class='label' and contains(text(),'" + lbl + "')]/following-sibling::td/input"));
        ele.click();
        ele.clear();
        ele.sendKeys(val);
    }

    protected void enterValueByLabel(WebElement element, String val) {
        element.clear();
        element.sendKeys(val);
    }

    protected void enterValueByJs(WebElement element, String val) {
        setClearInputValue(element);
        ((JavascriptExecutor) getDriver()).executeAsyncScript("arguments[0].value='" + val + "'", element);
    }

    protected void setClearInputValue(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeAsyncScript("arguments[0].value=''", element);
    }

    protected WebElement getCell(WebElement tbl, int row, int col) {
        return tbl.findElement(By.xpath("//tr[" + row + "]/td[" + col + "]"));
    }

    public void waitUntilElementVisible(WebElement ele) {
        WdManager.getWait().until(ExpectedConditions.visibilityOf(ele));
    }

    public void waitUntilElementNotVisible(WebElement ele) {
        WdManager.getWait().until(ExpectedConditions.invisibilityOf(ele));
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickLinkByText(String text) {
        click(getDriver().findElement(By.linkText(text)));
        waitForPageLoadComplete(90);
    }

    public void click(WebElement element) {
        element.click();
        waitForPageLoadComplete(100);
    }

    public void doubleClick(WebElement element) {
        element.click();
        element.click();
        waitForPageLoadComplete(100);
    }

    public void clickWithOutWait(WebElement element) {
        element.click();
    }

    protected void clickByJs(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    protected void clickByJs(String js, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript(js, element);
    }

    protected void selectByVisibleText(WebElement element, String value) {
        Select drpCountry = new Select(element);
        drpCountry.selectByVisibleText(value);
        waitForPageLoadComplete(10);
    }

    protected void selectByIndex(WebElement element, int index) {
        Select drpCountry = new Select(element);
        drpCountry.selectByIndex(index);
    }

    protected void selectByValue(WebElement element, String value) {
        Select drpCountry = new Select(element);
        drpCountry.selectByValue(value);
    }

    protected WebElement findControlByLabel(String label) {
        WebElement ele = null;
        try {
            ele = getDriver().findElement(By.xpath("//tr/td[contains(@class, 'label') and contains(text(),'" + label + "')]/following-sibling::td//input"));
        } catch (Exception ex) {
            ele = getDriver().findElement(By.xpath("//tr/td[contains(@class, 'label') and contains(text(),'" + label + "')]/following-sibling::td"));
        }
        return ele;
    }

//    protected void waitForPageLoadComplete(int specifiedTimeout) {
//        Wait<WebDriver> wait = new WebDriverWait(getDriver(), specifiedTimeout);
//        wait.until(driver -> String
//                .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
//                .equals("complete"));
//    }

    public void waitForPageLoadComplete(int specifiedTimeout) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), specifiedTimeout);
        wait.until(pageLoadCondition);
    }


    protected WebDriver getDriver() {
        return DriverFactory.getInstance().getDriver();
    }

    public WebElement findTdByLabelAndIndex(String lbl, int index) {
        return getDriver().findElement(By.xpath("//tr/td[contains(@class, 'label') and contains(text(),'" + lbl + "')]/following-sibling::td[" + index + "]"));
    }

    public String getTextOfElement(WebElement element) {
        return element.getText();
    }

    public String getValueOfElement(WebElement element) {
        return element.getAttribute("value");
    }


    public boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Throwable e) {
            return false;
        }
    }

    public static void waitUntilVisible(WebElement ele) {
        DriverFactory.getInstance().getWaitDriver().until(ExpectedConditions.visibilityOf(ele));
    }

    public String findValueByLabel(WebElement element, String label) {
        String xpath = ".//td[normalize-space(text())='%s']";
        WebElement td = element.findElement(By.xpath(String.format(xpath, label)));
        WebElement tr = td.findElement(By.xpath(".//ancestor::tr[1]"));
        return tr.findElement(By.xpath(".//td[4]")).getText();
    }

    public WebElement findLabelCell(WebElement element, String text) {
        List<WebElement> allLabels = element.findElements(By.xpath(".//td[@class='fieldKey']"));
        for (WebElement label : allLabels) {
            if (label.getText().trim().equalsIgnoreCase(text)) {
                return label;
            }
        }
        return null;
    }

    public WebElement findCheckBox(WebElement container, String name) {
        List<WebElement> labels = container.findElements(By.tagName("label"));
        for (WebElement label : labels) {
            if (label.getText().replace(" ", "").equalsIgnoreCase(name.replace(" ", ""))) {
                return label.findElement(By.tagName("input"));
            }
        }
        return null;
    }

    public WebElement findCheckBox(String name) {
        List<WebElement> labels = getDriver().findElements(By.tagName("label"));
        for (WebElement label : labels) {
            if (label.getText().replace(" ", "").equalsIgnoreCase(name.replace(" ", ""))) {
                return label.findElement(By.tagName("input"));
            }
        }
        return null;
    }

    public void clickEditBtnByIndex(int index) {
        getDriver().findElements(By.xpath("//a[contains(text(),'Edit')]")).get(index).click();

    }

    public void clickEditBtnBySection(String sectionName) {
        String xpath = String.format("//td[contains(text(),'%s') and @class='informationBoxHeader' ]/ancestor::table[1]//a[@class='informationBoxHeader' and contains(text(),'Edit')]", sectionName);
        getDriver().findElement(By.xpath(xpath)).click();
    }

    @FindBy(xpath = "//input[@value='Apply']")
    WebElement applyBtn;
    @FindBy(xpath = "//button[normalize-space(text())='Submit']")
    WebElement submitBtn;

    public void clickApplyBtn() {
        click((applyBtn));
    }

    public void selectDropBoxByVisibelText(WebElement element, String text) {
        Select el = new Select(element.findElement(By.tagName("Select")));
        el.selectByVisibleText(text);
    }

    public void acceptComfirmDialog() {
        getDriver().switchTo().alert().accept();
    }

    public void dismissComfirmDialog() {
        getDriver().switchTo().alert().dismiss();
    }

    public String getTextComfirmDialog() {
        return getDriver().switchTo().alert().getText();
    }

    public void clickSubmitBtn() {
        click(submitBtn);
    }

    public boolean isSubmitBtnDisplayed() {
        return getDriver().findElements(By.xpath("//button[normalize-space(text())='Submit']")).size() != 0;
    }

    public void backToPreviousPage() {
        DriverFactory.getInstance().getDriver().navigate().back();
    }

    public WebDriver switchFrameByName(String name) {
        return getDriver().switchTo().frame(name);
    }

    public WebDriver switchFrameByName(WebElement element) {
        return getDriver().switchTo().frame(element);
    }

    public String getTextOfSelectedOption(WebElement element) {
        Select select = new Select(element.findElement(By.tagName("select")));
        return select.getFirstSelectedOption().getText();
    }

    public void clickHelpBtnByIndex(int index) {
        List<WebElement> helpList = getDriver().findElements(By.xpath("//img[@src='images/icons/qmark.jpg?v=2.60.0-SNAPSHOT']"));
        waitUntilElementClickable(helpList.get(index));
        click(helpList.get(index));
    }

    public void waitUntilElementClickable(WebElement ele) {
        DriverFactory.getInstance().getWaitDriver().until(ExpectedConditions.elementToBeClickable(ele));
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public void clickBackBtn() {
        click(getDriver().findElement(By.xpath("//a[@id='BackBtn']")));
    }


    public void clickSaveBtn() {
        click(getDriver().findElement(By.xpath("//a[@id='SaveBtn']")));
    }

    public WebElement getMssgBoxByIndex(int index) {
        String xpath = String.format("//div[@class='msg-box'][%s]", index);
        return getDriver().findElement(By.xpath(xpath));
    }

    public void selectRadioButtonByText(WebElement element, String text) {
        String xpath = String.format("//input[ @value='%s' and @type='radio']", text);
        click(element.findElement(By.xpath(xpath)));
    }

    public void hover(WebElement element) {
        Actions action = new Actions(getDriver());
        scrollToElement(element);
        action.moveToElement(element).build().perform();
    }

    public void hoverOffset(WebElement element, int offsetX, int offsetY) {
        Actions action = new Actions(getDriver());
        action.moveToElement(element, offsetX, offsetY).build().perform();
    }

    public void executeJs(String mouseOverScript, WebElement hoverElement) {
        ((JavascriptExecutor) getDriver()).executeScript(mouseOverScript, hoverElement);
    }

    public void closeCurrentBrowser() {
        getDriver().close();
    }

    public String getTitle() {
        return getDriver().getTitle();
    }

    protected void enterValueByLabel(List<WebElement> element, String... val) {
        for (int i = 0; i < element.size(); i++) {
            element.get(i).clear();
            element.get(i).sendKeys(val[i]);
        }
    }

    protected void selectByVisibleText(List<WebElement> element, String... value) {
        for (int i = 0; i < element.size(); i++) {
            Select drpCountry = new Select(element.get(i));
            drpCountry.selectByVisibleText(value[i]);
        }

    }

    protected WebElement findLable(String label) {
        String xpath = String.format("//label[normalize-space(text())='%s']", label);
        return DriverFactory.getInstance().getDriver().findElement(By.xpath(xpath));
    }

    protected List<WebElement> findLables(String label) {
        String xpath = String.format("//label[normalize-space(text())='%s']", label);
        return DriverFactory.getInstance().getDriver().findElements(By.xpath(xpath));
    }

    protected WebElement findInputByLabel(String label) {
        return findLable(label).findElement(By.xpath("./following-sibling::input"));
    }

    protected WebElement findInputByDiv(String label) {
        return findDivByLabel(label).findElement(By.xpath("./following-sibling::input"));
    }

    protected WebElement findDivByLabel(String label) {
        return findLable(label).findElement(By.xpath("./following-sibling::div"));
    }

    protected List<WebElement> findDivsByLabel(String label) {
        List<WebElement> labels = findLables(label);
        List<WebElement> divs = new ArrayList<>();
        for (WebElement element : labels) {
            divs.add(element.findElement(By.xpath("./following-sibling::div")));
        }
        return divs;
    }

    protected WebElement findButtonBy(String Text) {
        String xpath = String.format("//button[normalize-space(text())='%s']", Text);
        return DriverFactory.getInstance().getDriver().findElement(By.xpath(xpath));
    }

    protected WebElement findSelectByLabel(String label) {
        return findDivByLabel(label).findElement(By.xpath("./select"));
    }

    public void selectDropDownListByVisibleTextAndLabel(String label, String text) {
        Select select = new Select(findSelectByLabel(label));
        select.selectByVisibleText(text);
    }


    @FindBy(xpath = "//input[@type='search']")
    WebElement searchInput;

    protected void searchAutoCompleteInputByLabel(String label, String value) {
        WebElement element = findSpanByText(label);
        click(element);
        enterValueByLabel(searchInput, value);
        String xpath = String.format("//li[text()='%s']", value);
        click(getDriver().findElement(By.xpath(xpath)));
    }

    protected WebElement findSpanByText(String text) {
        String xpath = String.format("//span[text()='%s']", text);
        return DriverFactory.getInstance().getDriver().findElement(By.xpath(xpath));
    }

    protected List<WebElement> findSpansByLabel(String label) {
        List<WebElement> elementList = findDivsByLabel(label);
        List<WebElement> spanLists = new ArrayList<>();
        for (WebElement element : elementList) {
            spanLists.add(element.findElement(By.xpath(".//span")));
        }
        return spanLists;
    }

    protected List<WebElement> findInputsByLabel(String label) {
        List<WebElement> elementList = findDivsByLabel(label);
        List<WebElement> inputList = new ArrayList<>();
        for (WebElement element : elementList) {
            inputList.add(element.findElement(By.xpath(".//input")));
        }
        return inputList;
    }

    protected void enterKeyPerform(WebElement element) {
        element.sendKeys(Keys.ENTER);
    }

    public WebElement findCheckBoxByLabel(String label) {
        return findDivByLabel(label).findElement(By.xpath(".//input[@type='checkbox']"));
    }

    public boolean verifyCheckBoxIsCheckedByLabel(String label) {
        return findCheckBoxByLabel(label).isSelected();
    }


    public void waitUntilSpecificTime(int second) {
        try {
            Thread.sleep(1000 * second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FindBy(xpath = "//a[normalize-space(text())='Create new']")
    WebElement createNewBtn;

    public void clickCreateNewBtn() {
        waitUntilElementClickable(createNewBtn);
        click(createNewBtn);
    }

    public static WebElement getAElementByText(String text) {
        List<WebElement> webElementList = DriverFactory.getInstance().getDriver().findElements(By.xpath("//a"));
        for (WebElement el : webElementList) {
            if (el.getText().equalsIgnoreCase(text))
                return el;
        }
        return webElementList.get(0);
    }

    public static List<WebElement> getAllAElementsById(String id) {
        return DriverFactory.getInstance().getDriver().findElements(By.id(id));
    }

    protected WebElement getInputById(String id) {
        String xpath = String.format("//input[@id='%s']", id);
        return getDriver().findElement(By.xpath(xpath));
    }

    public static List<WebElement> getAllDivElementsByClass(String className) {
        String xpath = String.format("//span[contains(@class,'%s')]", className);
        return DriverFactory.getInstance().getDriver().findElements(By.xpath(xpath));
    }

    public static List<WebElement> getAllButtonsByText(String text) {
        String xpath = String.format("//button[normalize-space('%s')]", text);
        return DriverFactory.getInstance().getDriver().findElements(By.xpath(xpath));
    }

    public BigDecimal parse(final String amount, final Locale locale) throws ParseException {
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]", ""));
    }

    //endregion
}

