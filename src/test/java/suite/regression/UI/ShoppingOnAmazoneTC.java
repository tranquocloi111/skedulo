package suite.regression.UI;


import logic.pages.CartPage;
import logic.pages.HomePage;
import logic.pages.ProductDetailPage;
import logic.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashMap;

public class ShoppingOnAmazoneTC extends BaseTest {

    @Test(enabled = true, description = "Test Amazon shopping")
    public void amazoneShoppingTCs() {

        test.get().info("Step 1: click today detail label");
        HomePage.getInstance().clickTodayDeal();

        test.get().info("Step 2: select High to low option");
        SearchPage.getInstance().triggerSortByText("Featured");
        SearchPage.getInstance().selectHighToLowOption();

        test.get().info("Step 3: click second item");
        BigDecimal firstItemPrice = SearchPage.getInstance().getPriceFollowIndex(1);
        SearchPage.getInstance().clickItemByIndex(1);


        test.get().info("Step 4: add 2 items in cart");
        SearchPage.getInstance().backToPreviousPage();
        SearchPage.getInstance().clickAddToCartByIndex(0);
        SearchPage.getInstance().clickAddToCartByIndex(0);

        test.get().info("Step 5: Go to to product cart ");
        SearchPage.getInstance().keyInKeywordToSearchBar("AAA Batteries");
        SearchPage.getInstance().clickSearch();

        test.get().info("Step 6: select High to low option");
        SearchPage.getInstance().selectNewestArrivals();

        test.get().info("Step 7: click  item");
        SearchPage.getInstance().clickItemByName("Energizer AA Batteries, MAX Alkaline, 30 Pack");


        test.get().info("Step 8: select 5 items and add to cart");
        SearchPage.getInstance().selectQuantityOfProduct("5");
        BigDecimal secondItemPrice = ProductDetailPage.getInstance().priceInsideBuyBox();
        ProductDetailPage.getInstance().clickAddToCartBtn();

        test.get().info("Step 9: Go to to product cart ");
        SearchPage.getInstance().goToCart();

        test.get().info("Step 10: Update quantity of products ");
        CartPage.getInstance().selectQuantityOfProduct("1", 1);
        CartPage.getInstance().selectQuantityOfProduct("3", 0);


        test.get().info("Step 11: Assert quantity of products ");
        Assert.assertEquals(CartPage.getInstance().getSubTotal(), "Subtotal (4 items):");

        test.get().info("Step 12: Assert prices of products ");
        BigDecimal totalPrice = CartPage.getInstance().getToTalPay();
        double expectedTotal = firstItemPrice.doubleValue() + secondItemPrice.doubleValue() * 3;
        Assert.assertEquals(CartPage.roundDouble(expectedTotal, 2), totalPrice);

        BigDecimal actualFirstItemPrice = CartPage.getInstance().getPriceByIndex(1);
        Assert.assertEquals(actualFirstItemPrice, firstItemPrice);

        BigDecimal actualSecondItemPrice = CartPage.getInstance().getPriceByIndex(0);
        Assert.assertEquals(actualSecondItemPrice, secondItemPrice);


    }

}
