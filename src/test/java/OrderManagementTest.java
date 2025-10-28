import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ekod.exceptions.InvalidDiscountCodeException;
import fr.ekod.exceptions.OutOfStockException;
import fr.ekod.Invoice;
import fr.ekod.Order;
import fr.ekod.Product;
import fr.ekod.ShoppingCart;

import static org.junit.jupiter.api.Assertions.*;
public class OrderManagementTest {

    private Product testProduct;
    private ShoppingCart testCart;

    @BeforeEach
    void setUp() {
        testProduct = new Product("", 999.99, 5);
        testCart = new ShoppingCart();
    }

    @Test
    void testProductCreationNoName() {
        assertEquals("", testProduct.getName());
        assertEquals(999.99, testProduct.getPrice());
        assertEquals(5, testProduct.getStockQuantity());
    }

    @Test
    void testProductCreationNoPrice() {
        Product zeroPriceProduct = new Product("Test Product", 0.0, 5);
        assertEquals("Test Product", zeroPriceProduct.getName());
        assertEquals(0.0, zeroPriceProduct.getPrice());
        assertEquals(5, zeroPriceProduct.getStockQuantity());
    }

    @Test
    void testProductCreationNoStock() {
        Product noStockProduct = new Product("Test Product", 999.99, 0);
        assertEquals("Test Product", noStockProduct.getName());
        assertEquals(999.99, noStockProduct.getPrice());
        assertEquals(0, noStockProduct.getStockQuantity());
    }

    @Test
    void testAddProductToCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(testProduct);
        assertEquals(1, cart.getItemCount());
        assertEquals(testProduct, pr());
    }

    // TODO: Implement tests for Invoice

    // TODO: Implement tests for Order
}
