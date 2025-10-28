import fr.ekod.Product;
import fr.ekod.ShoppingCart;
import fr.ekod.Order;
import fr.ekod.exceptions.InvalidDiscountCodeException;
import fr.ekod.exceptions.OutOfStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderManagementTest {
    private Product testProduct;
    private ShoppingCart cart;
    private Order order;

    @BeforeEach
    void setUp() {
        testProduct = new Product("Test Product", 999.99, 5);
        cart = new ShoppingCart();
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

    //TODO: Implement tests for Cart
    @Test
    void testStockDecrementWhenProductAddedToCart() {
        Product testProduct = new Product("Test Product", 999.99, 5);
        assertEquals(5, testProduct.getStockQuantity(), "La quantité en stock doit être 5 avant d'ajouter le produit");
        ShoppingCart shoppingCart = new ShoppingCart();
        try {
            shoppingCart.addProduct(testProduct);
        } catch (OutOfStockException e) {
            fail("L'exception OutOfStockException ne devrait pas être lancée ici");
        }
        assertEquals(4, testProduct.getStockQuantity(), "La quantité en stock doit être 4 après avoir ajouté le produit");
    }

        // TODO: Implement tests for Invoice

        // TODO: Implement tests for Order
        @Test
         void testDiscount() {
            Product testProduct = new Product("Test Product", 15, 5);
            ShoppingCart shoppingCart = new ShoppingCart();
            try {
                shoppingCart.addProduct(testProduct);
            } catch (OutOfStockException e) {
                fail("L'exception OutOfStockException ne devrait pas être lancée ici");
            }
            Order testOrder = new Order(shoppingCart);
            try {
                testOrder.applyDiscount("PROMO10");
            } catch (InvalidDiscountCodeException e) {
                assertEquals("Code de réduction invalide: PROMO10", e.getMessage());
            }
            try {
                testOrder.applyDiscount("PROMO");
            } catch (InvalidDiscountCodeException e) {
                assertEquals("Code de réduction PROMO n'existe pas", e.getMessage());
            }
            assertEquals(0.15, testOrder.getDiscount(), 0.001);
            assertEquals(12.75, shoppingCart.getTotalPrice() * (1 - testOrder.getDiscount()), 0.001);
        }
}

