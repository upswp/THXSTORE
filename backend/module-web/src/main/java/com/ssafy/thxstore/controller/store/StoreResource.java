package com.ssafy.thxstore.controller.store;

import com.ssafy.thxstore.store.domain.Store;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class StoreResource extends EntityModel<Store> {
    public StoreResource(Store store, Link... links){
        super(store, links);
        add(linkTo(StoreController.class).slash(store.getId()).withSelfRel());
    }
}
