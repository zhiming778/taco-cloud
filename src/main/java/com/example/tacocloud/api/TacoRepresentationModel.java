package com.example.tacocloud.api;

import com.example.tacocloud.controller.DesignTacoController;
import com.example.tacocloud.entity.Ingredient;
import com.example.tacocloud.entity.Taco;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Date;
import java.util.List;
@KafkaListener
@Getter
@Relation(value = "value", collectionRelation = "collectionRelation")
public class TacoRepresentationModel extends RepresentationModel<TacoRepresentationModel> {
    private final String name;
    private final Date createdAt;
    private final List<Ingredient> ingredients;

    public TacoRepresentationModel(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients = taco.getIngredients();
        this.add(linkTo(methodOn(DesignTacoController.class).getOrderById(taco.getId())).withRel("Taco" + taco.getId()));
    }
}
