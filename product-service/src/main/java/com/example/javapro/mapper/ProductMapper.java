package com.example.javapro.mapper;

import com.example.javapro.model.Product;
import com.example.javapro.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "user.id", target = "userId")
    ProductDto toDto(Product product);

    @Mapping(source = "userId", target = "user.id")
    Product toEntity(ProductDto productDto);

}
