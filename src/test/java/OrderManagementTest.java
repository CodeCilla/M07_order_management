import fr.ekod.Product;
import fr.ekod.ShoppingCart;
import fr.ekod.Order;
import fr.ekod.Invoice;
import fr.ekod.exceptions.InvalidDiscountCodeException;
import fr.ekod.exceptions.OutOfStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
public class OrderManagementTest {

    private Product testProduct;
    private Product testProduct5;
    private Product testEmptyProduct;
    private ShoppingCart testCart;

    @BeforeEach
    void setUp() {
        testProduct = new Product("test", 999.99, 5);
        testProduct5 = new Product("test", 5, 10);
        testCart = new ShoppingCart();
        testEmptyProduct = new Product("",20, 0);
    }

    //Product Creation with Empty Name
    @Test
    void testProductCreationNoName() {
        assertEquals("", testEmptyProduct.getName());
    }

    //Product Creation with Zero Price
    @Test
    void testProductCreationNoPrice() {
        Product zeroPriceProduct = new Product("Test Product", 0.0, 5);
        assertEquals(0.0, zeroPriceProduct.getPrice());
    }

    //Product Creation with Zero Stock
    @Test
    void testProductCreationNoStock() {
        Product noStockProduct = new Product("Test Product", 999.99, 0);
        assertEquals(0, noStockProduct.getStockQuantity());
    }

    //Add Available Product to ShoppingCart
    @Test
    void testAddProductToCart() throws OutOfStockException {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(testProduct);
        assertEquals(1, cart.getItemCount());
    }

    //Add Unavailable Product to ShoppingCart
    @Test
    void testAddUnavailableProductInCart() throws OutOfStockException {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(testEmptyProduct);
        assertEquals(0, cart.getItemCount());
    }

    //ShoppingCart Item Count after Add/Remove
    @Test
    void testAddProductInCartCount() throws OutOfStockException {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(testProduct);
        assertEquals(1, cart.getItemCount());
        cart.addProduct(testProduct);
        assertEquals(2, cart.getItemCount());
        cart.removeProduct(testProduct);
        assertEquals(1, cart.getItemCount());
    }

    //Calculate Order Delivery Fee
    @Test
    void testDeliveryFee() throws OutOfStockException {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(testProduct);
        cart.addProduct(testProduct);
        Order order = new Order(cart);
        assertEquals(10, order.getDeliveryFee());
    }

    //Calculate Order Total Price
    @Test
    void testTotalPrice() throws OutOfStockException, InvalidDiscountCodeException {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(testProduct5);
        cart.addProduct(testProduct5);
        Order order = new Order(cart);
        order.applyDiscount("PROMO10");
        assertEquals(18, order.getTotalPrice());
    }

    //Generate Invoice Text
    @Test
    void testInvoice() throws OutOfStockException {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(testProduct5);
        cart.addProduct(testProduct5);
        Order order = new Order(cart);
        Invoice invoice = new Invoice(order);
        String invoiceString = invoice.generateInvoice();
        assertEquals("=== FACTURE ===\n" +
                "\n" +
                "Articles:\n" +
                "- test: 5.00 €\n" +
                "- test: 5.00 €\n" +
                "\n" +
                "Sous-total: 10.00 €\n" +
                "Frais de livraison: 10.00 €\n" +
                "\n" +
                "Total: 20.00 €\n", invoiceString);

    }

    //TODO: Implement tests for Cart
    @Test
    void testStockDecrementWhenProductAddedToCart() {
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
            ShoppingCart shoppingCart = new ShoppingCart();
            try {
                shoppingCart.addProduct(testProduct5);
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
                assertEquals("Code de réduction invalide: PROMO", e.getMessage());
            }
            assertEquals(0.1, testOrder.getDiscount(), 0.001);
            assertEquals(4.5, shoppingCart.getTotalPrice() * (1 - testOrder.getDiscount()), 0.001);
        }
}

