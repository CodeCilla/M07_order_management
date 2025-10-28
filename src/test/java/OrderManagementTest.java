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
        assertEquals("", testProduct.getName());
        assertEquals(999.99, testProduct.getPrice());
        assertEquals(5, testProduct.getStockQuantity());
    }

    //Product Creation with Zero Price
    @Test
    void testProductCreationNoPrice() {
        Product zeroPriceProduct = new Product("Test Product", 0.0, 5);
        assertEquals("Test Product", zeroPriceProduct.getName());
        assertEquals(0.0, zeroPriceProduct.getPrice());
        assertEquals(5, zeroPriceProduct.getStockQuantity());
    }

    //Product Creation with Zero Stock
    @Test
    void testProductCreationNoStock() {
        Product noStockProduct = new Product("Test Product", 999.99, 0);
        assertEquals("Test Product", noStockProduct.getName());
        assertEquals(999.99, noStockProduct.getPrice());
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
    void testTotalPrice() throws OutOfStockException {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(testProduct5);
        cart.addProduct(testProduct5);
        Order order = new Order(cart);
        assertEquals(20, order.getTotalPrice());
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
                "Total: 20.00 €", invoiceString);

    }

}
