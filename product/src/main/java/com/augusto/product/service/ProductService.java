package com.augusto.product.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.augusto.product.payload.BatchDto;
import com.augusto.product.payload.BatchInputDto;
import com.augusto.product.payload.ItemsInputDto;
import com.augusto.product.payload.OrderDto;
import com.augusto.product.payload.ProductDto;
import com.augusto.product.payload.ProductInputDto;
import com.augusto.product.proxy.ProductProxy;
import com.augusto.product.security.JwtTokenProvider;

@Service
public class ProductService {
    @Autowired
    private Environment environment;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ProductProxy productProxy;
    @Autowired
    private ModelMapper mapper;


    public ProductDto createProductDto(ProductInputDto productInputDto, String token) {
        productInputDto.setUser(provideIdFromToken(token));
        productInputDto.setSku(generateSku());
        var productDto = toProductDto(productInputDto);
        productDto.setEnvironment(environment.getProperty("local.server.port"));
        productDto.setBatch(toBatchDto(productInputDto.getBatch()));
        Message<ProductInputDto> message = MessageBuilder.withPayload(productInputDto)
                .setHeader(KafkaHeaders.TOPIC, "product").build();
        kafkaTemplate.send(message);
        return productDto;
    }

    public List<ProductDto> getProductDtoByUserId(Long id, String token) {
        var productDto = productProxy.getProductByUserId(id, token);
        productDto.forEach(p -> p.setEnvironment(environment.getProperty("local.server.port")));;
        return productDto;
    }

    public ProductDto getProductDtoById(Long id, String token) {
        var productDto = getProductDto(id, token);
        productDto.setEnvironment(environment.getProperty("local.server.port"));
        return productDto;
    }

    public void insertBatch(BatchInputDto batchInputDto) {
        Message<BatchInputDto> message =
                MessageBuilder.withPayload(batchInputDto).setHeader(KafkaHeaders.TOPIC, "batch")
                        .setHeader(KafkaHeaders.GROUP_ID, "product").build();
        kafkaTemplate.send(message);
    }

    public OrderDto buyProduct(List<ItemsInputDto> itemsInputDtos, String token) {
        var userID = provideIdFromToken(token);
        itemsInputDtos.forEach(iDto -> iDto.setUser(userID));
        var orderDto =  productProxy.buyProduct(itemsInputDtos, token);
        orderDto.getItems().forEach(i -> i.getProduct().setEnvironment(environment.getProperty("local.server.port")));
        return orderDto;
    }

    public OrderDto confirmPayment(Long orderID, String token) {
        return productProxy.confirmPayment(orderID, token);
    }

    public void CancelOrder(Long orderID, String token) {
        productProxy.cancelOrder(orderID, token);
    }


    private String generateSku() {
        var sku = "";
        for (int i = 0; i <= 4; i++) {
            SecureRandom rand = new SecureRandom();
            var num = rand.nextInt(10);
            sku += "" + num;
        }
        return sku;
    }

    private ProductDto getProductDto(Long id, String token) {
        return productProxy.getProductById(id, token);
    }

    private ProductDto toProductDto(ProductInputDto productInputDto) {
        var productDto = mapper.map(productInputDto, ProductDto.class);
        return productDto;
    }

    private List<BatchDto> toBatchDto(BatchInputDto batchInputDto) {
        var batchDto = mapper.map(batchInputDto, BatchDto.class);
        var batchDtoList = new ArrayList<BatchDto>();
        batchDtoList.add(batchDto);
        return batchDtoList;
    }

    private Long provideIdFromToken(String token) {
        return jwtTokenProvider.getUserIdFromJwt(token);
    }
}
