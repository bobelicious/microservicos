package com.augusto.product.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.augusto.product.enums.PaymentStatus;
import com.augusto.product.exceptions.ProductException;
import com.augusto.product.exceptions.ResourceNotFoundException;
import com.augusto.product.model.Batch;
import com.augusto.product.model.Items;
import com.augusto.product.model.Order;
import com.augusto.product.model.Product;
import com.augusto.product.payload.BatchDto;
import com.augusto.product.payload.BatchInputDto;
import com.augusto.product.payload.ItemsInputDto;
import com.augusto.product.payload.OrderDto;
import com.augusto.product.payload.ProductDto;
import com.augusto.product.payload.ProductInputDto;
import com.augusto.product.repository.BatchRepository;
import com.augusto.product.repository.ItemsRepository;
import com.augusto.product.repository.OrderRepository;
import com.augusto.product.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ItemsRepository itemsRepository;

    @KafkaListener(topics = "product", groupId = "product",
            containerFactory = "kafkaListenerContainerFactory")
    public void createProduct(ProductInputDto productInputDto) {
        var product = toProduct(productInputDto);
        productRepository.save(product);
        batchRepository.save(toBatch(productInputDto.getBatch()));
    }

    public List<ProductDto> getProductDtoByUserId(Long id) {
        List<Product> products = productRepository.findProductByUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "userId", id));
        var productDto = products.stream().map(p -> toProductDto(p)).toList();
        return productDto;
    }

    public ProductDto getProductDtoById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        var productDto = toProductDto(product);
        productDto.setBatch(toBatchDto(product.getBatch()));
        return productDto;
    }

    @KafkaListener(topics = "batch", groupId = "product",
            containerFactory = "kafkaListenerContainerFactory")
    public void insertBatch(BatchInputDto batchInputDto) {
        var product = productRepository.findById(batchInputDto.getProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Batch", "ProductId",
                        batchInputDto.getProduct()));
        var batch = toBatch(batchInputDto);
        batch.setProduct(product);
        batchRepository.save(batch);
    }

    public OrderDto buyProduct(List<ItemsInputDto> itemsInputDtos) {
        var products = verifyProductsInStock(itemsInputDtos);
        var userID = itemsInputDtos.get(0).getUser();
        var items = toItems(itemsInputDtos, userID, products);
        var order = generateOrder(userID, items);
        var orderDto = toOrderDto(order);
        updateProductQuantity(items);
        return orderDto;
    }

    public OrderDto confirmPayment(Long orderID) {
        var order = orderRepository.findById(orderID)
                .orElseThrow(() -> new ProductException(HttpStatus.BAD_REQUEST,
                        "O pedido informado não foi encontrado"));
        order.setPayment(PaymentStatus.CONFIRMED);
        orderRepository.save(order);
        return toOrderDto(order);
    }

    public void CancelOrder(Long orderID) {
        var order = orderRepository.findById(orderID)
                .orElseThrow(() -> new ProductException(HttpStatus.NOT_FOUND,
                        "pedido solicitado não encontrado"));
        order.setPayment(PaymentStatus.CANCELED);
        var canceledProducts = new ArrayList<Product>();
        order.getItems().forEach((i) -> {
            var product = i.getProduct();
            product.setQuantity(product.getQuantity() + i.getQuantity());
            canceledProducts.add(product);
        });
        productRepository.saveAll(canceledProducts);
    }


    private void updateProductQuantity(List<Items> items) {
        var processedProducts = new ArrayList<Product>();
        items.forEach(i -> {
            var product = i.getProduct();
            if (product.getQuantity() < i.getQuantity()) {
                throw new ProductException(HttpStatus.BAD_REQUEST,
                        "Quantidade insuficiente, produto: " + product.getName() + "disponível: "
                                + product.getQuantity());
            }
            product.setQuantity(product.getQuantity() - i.getQuantity());
            processedProducts.add(product);
        });
        productRepository.saveAll(processedProducts);
    }

    private List<Items> toItems(List<ItemsInputDto> itemsInputDtos, Long userID,
            List<Product> products) {
        ArrayList<Items> items = new ArrayList<Items>();
        itemsInputDtos.forEach((iDto) -> {
            var newItem = new Items();
            var product = products.stream().filter(p -> p.getSku().equals(iDto.getProductSku()))
                    .findAny();
            newItem.setProduct(product.get());
            newItem.setUser(userID);
            newItem.setQuantity(iDto.getQuantity());
            newItem.setDiscount(iDto.getDiscount());
            items.add(newItem);
        });
        return items;
    }

    private Order generateOrder(Long userID, List<Items> items) {
        var order = new Order();
        items.forEach(i -> {
            var discount = ((float) i.getDiscount() / 100F) * i.getProduct().getUnityPrice();
            var price = (i.getProduct().getUnityPrice() - discount) * (float)i.getQuantity();
            order.setFinalPrice(price + order.getFinalPrice());
            order.setTotalPrice(
                    order.getTotalPrice() + (i.getQuantity() * i.getProduct().getUnityPrice()));
        });

        order.setItems(items);
        order.setUser(userID);
        order.setDiscount(order.getTotalPrice() - order.getFinalPrice());
        order.setPayment(PaymentStatus.PROCESSING);
        var newOrder = orderRepository.save(order);
        items.forEach(i -> {
            i.setOrder(newOrder);
        });
        itemsRepository.saveAll(items);
        return newOrder;
    }

    private List<Product> verifyProductsInStock(List<ItemsInputDto> itemsInputDtos) {
        var skus = itemsInputDtos.stream().map(i -> i.getProductSku()).toList();
        var products = productRepository.findAllBySkuIn(skus)
                .orElseThrow(() -> new ProductException(HttpStatus.NOT_FOUND,
                        "Não existe produto com os códigos SKU informados"));
        if (skus.size() != products.size()) {
            throw new ProductException(HttpStatus.NOT_FOUND, "Verifique os produtos solicitados");
        }
        return products;
    }

    private Product toProduct(ProductInputDto productInputDto) {
        var product = mapper.map(productInputDto, Product.class);
        return product;
    }

    private ProductDto toProductDto(Product product) {
        var productDto = mapper.map(product, ProductDto.class);
        return productDto;
    }

    private List<BatchDto> toBatchDto(List<Batch> batches) {
        var batchesDto = batches.stream().map(b -> mapper.map(b, BatchDto.class)).toList();
        return batchesDto;
    }

    private Batch toBatch(BatchInputDto batchInputDto) {
        var batch = mapper.map(batchInputDto, Batch.class);
        return batch;
    }

    private OrderDto toOrderDto(Order order) {
        var orderDto = mapper.map(order, OrderDto.class);
        return orderDto;
    }
}
