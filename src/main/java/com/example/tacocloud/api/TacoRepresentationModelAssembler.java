package com.example.tacocloud.api;

import com.example.tacocloud.controller.DesignTacoController;
import com.example.tacocloud.entity.Taco;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class TacoRepresentationModelAssembler implements RepresentationModelAssembler<Taco, TacoRepresentationModel> {


    @Override
    public TacoRepresentationModel toModel(Taco entity) {
        TacoRepresentationModel tacoRepresentationModel = new TacoRepresentationModel(entity);
        return tacoRepresentationModel;
    }

    @Override
    public CollectionModel<TacoRepresentationModel> toCollectionModel(Iterable<? extends Taco> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
