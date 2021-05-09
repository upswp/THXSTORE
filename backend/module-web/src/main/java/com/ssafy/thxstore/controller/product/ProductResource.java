package com.ssafy.thxstore.controller.product;

import com.ssafy.thxstore.product.domain.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ProductResource extends EntityModel<Product> {
    public ProductResource(Product product, Link... links){
        super(product, links);
        add(linkTo(ProductController.class).slash(product.getId()).withSelfRel());
    }
}