package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.*;
import it.gabrieletondi.telldontaskkata.doubles.InMemoryProductCatalog;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OrderCreationUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private Category food = new Category("food", new BigDecimal("10"));
    private final ProductCatalog productCatalog = new InMemoryProductCatalog(
            Arrays.asList(
                    new Product("salad", new BigDecimal("3.56"), food),
                    new Product("tomato", new BigDecimal("4.65"), food)
            )
    );

    private final OrderCreationUseCase useCase = new OrderCreationUseCase(orderRepository, productCatalog);

    @Test
    public void sellMultipleItems() throws Exception {

        final SellItemsRequest request = new SellItemsRequest(Arrays.asList(
                new SellItemRequest("salad", 2),
                new SellItemRequest("tomato", 3)
        )
        );

        useCase.run(request);

        final Order insertedOrder = orderRepository.getSavedOrder();

        assertTrue(insertedOrder.equals(getOriginalOrder()));
    }

    private Order getOriginalOrder(){

        Order originalOrder = new Order(Arrays.asList(
                new OrderItem(productCatalog.getByName("salad"), 2),
                new OrderItem(productCatalog.getByName("tomato"), 3)
        ));
        return originalOrder;
    }

    @Test(expected = UnknownProductException.class)
    public void unknownProduct() throws Exception {

        SellItemRequest unknownProductRequest = new SellItemRequest("unknown product");
        SellItemsRequest request = new SellItemsRequest(Arrays.asList(unknownProductRequest));

        useCase.run(request);
    }
}
