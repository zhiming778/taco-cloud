package com.example.tacocloud.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.tacocloud.api.TacoRepresentationModel;
import com.example.tacocloud.api.TacoRepresentationModelAssembler;
import com.example.tacocloud.dao.IngredientRepository;
import com.example.tacocloud.dao.TacoRepository;
import com.example.tacocloud.entity.Ingredient;
import com.example.tacocloud.entity.Ingredient.Type;
import com.example.tacocloud.entity.Order;
import com.example.tacocloud.entity.Taco;
import com.example.tacocloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/design")
public class DesignTacoController {


    private final IngredientRepository ingredientRepo;

    private final TacoRepository designRepo;


    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo,
            TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping("/taco/{id}")
    public Taco getOrderById(@PathVariable Long id){
        Taco taco  =designRepo.findById(id).orElse(null);
        if(taco == null)
            throw new IllegalArgumentException();
        return taco;
    }

    @GetMapping("/recent")
    public CollectionModel<TacoRepresentationModel> recentTacos() {
        List<Taco> tacos = designRepo.findAll();
        Collection<TacoRepresentationModel> tacoRepresentationModels = new TacoRepresentationModelAssembler().toCollectionModel(tacos).getContent();
        CollectionModel<TacoRepresentationModel> recentTacos = CollectionModel.of(tacoRepresentationModels);
        recentTacos.add(linkTo(methodOn(DesignTacoController.class).recentTacos()).slash("1").withRel("1111"));
        return recentTacos;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = ingredientRepo.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        return "design";
    }

    @PostMapping
    public String processDesign(
            @Valid @RequestBody Taco design, Errors errors,
            @ModelAttribute Order order, @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}

