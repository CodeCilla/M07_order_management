import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import fr.ekod.exceptions.InvalidDiscountCodeException;
// import fr.ekod.exceptions.OutOfStockException;
// import fr.ekod.Invoice;
// import fr.ekod.Order;
// import fr.ekod.Product;
// import fr.ekod.ShoppingCart;

// import static org.junit.jupiter.api.Assertions.*;

public class OrderManagementTest {
    // TODO: Declare products and cart

    @BeforeEach
    void setUp() {
        // TODO: Initialize products and cart
    }

        @Test
    void testProductCreationNoName() {
        Product testProduct = new Product("", 999.99, 5);
        assertEquals("", testProduct.getName());
        assertEquals(999.99, testProduct.getPrice());
        assertEquals(5, testProduct.getStockQuantity());
    }
    @Test
    void testProductCreationNoPrice() {
        Product testProduct = new Product("Test Product", 0.0, 5);
        assertEquals("Test Product", testProduct.getName());
        assertEquals(0.0, testProduct.getPrice());
        assertEquals(5, testProduct.getStockQuantity());
    }
    @Test
    void testProductCreationNoStock() {
        Product testProduct = new Product("Test Product", 999.99, 0);
        assertEquals("Test Product", testProduct.getName());
        assertEquals(999.99, testProduct.getPrice());
        assertEquals(0, testProduct.getStockQuantity());
    }

    // TODO: Implement tests for ShoppingCart

    // TODO: Implement tests for Invoice

    // TODO: Implement tests for Order
}
