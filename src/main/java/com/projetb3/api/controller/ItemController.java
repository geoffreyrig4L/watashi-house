package com.projetb3.api.controller;

import com.projetb3.api.model.Item;
import com.projetb3.api.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/articles")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Page<Item>> getAll(@RequestParam("page") final Optional<Integer> page,
                                             @RequestParam("sortBy") final Optional<String> sortBy,
                                             @RequestParam("orderBy") final Optional<String> orderBy,
                                             @RequestParam("couleur") final Optional<String> couleur,
                                             @RequestParam("min") final Optional<Integer> min,
                                             @RequestParam("max") final Optional<Integer> max
    ) {
        Page<Item> itemsList = itemService.getAll(page, sortBy, orderBy, couleur, min, max);
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable("id") final int id) {
        Optional<Item> item = itemService.get(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categorie={id_category}")
    public ResponseEntity<Page<Item>> getItemsOfCategory(@PathVariable("id_category") final int id_category,
                                                         @RequestParam("page") final Optional<Integer> page,
                                                         @RequestParam("sortBy") final Optional<String> sortBy,
                                                         @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<Item> itemsList = itemService.getItemsOfCategory(page, sortBy, orderBy, id_category);
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/souscategorie={id_subCategory}")
    public ResponseEntity<Page<Item>> getItemsOfSubCategory(@PathVariable("id_subCategory") final int id_subCategory,
                                                            @RequestParam("page") final Optional<Integer> page,
                                                            @RequestParam("sortBy") final Optional<String> sortBy,
                                                            @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<Item> itemsList = itemService.getItemsOfSubCategory(page, sortBy, orderBy, id_subCategory);
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/piece={id_room}")
    public ResponseEntity<Page<Item>> getItemsOfRoom(@PathVariable("id_room") final int id_room,
                                                     @RequestParam("page") final Optional<Integer> page,
                                                     @RequestParam("sortBy") final Optional<String> sortBy,
                                                     @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<Item> itemsList = itemService.getItemsOfRoom(page, sortBy, orderBy, id_room);
        return ResponseEntity.ok(itemsList);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Item item) {
        if (item.getCategories().isEmpty()) {
            return ResponseEntity.ok().body("L'article doit être associé à une catégorie. Catégorie : " + item.getCategories());
        }
        itemService.save(item);
        return ResponseEntity.ok().body("L'article a été crée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") final int id) {
        Optional<Item> optItem = itemService.get(id);
        if (optItem.isPresent()) {
            itemService.delete(id);
            return ResponseEntity.ok().body("L'article a été supprimé.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable("id") final int id, @RequestBody Item modified) {
        System.out.println(modified.toString());
        Optional<Item> optItem = itemService.get(id);
        if (optItem.isPresent()) {
            Item current = optItem.get();
            if (modified.getName() != null) {
                current.setName(modified.getName());
            }
            if (modified.getDescription() != null) {
                current.setDescription(modified.getDescription());
            }
            if (modified.getImage1() != null) {
                current.setImage1(modified.getImage1());
            }
            if (modified.getImage2() != null) {
                current.setImage2(modified.getImage2());
            }
            if (modified.getImage3() != null) {
                current.setImage3(modified.getImage3());
            }
            if (modified.getImage4() != null) {
                current.setImage1(modified.getImage4());
            }
            if (modified.getStock() != 0) {
                current.setStock(modified.getStock());
            }
            if (modified.getColor() != null) {
                current.setColor(modified.getColor());
            }
            if (modified.getPrice() != 0) {
                current.setPrice(modified.getPrice());
            }
            if (modified.getCollection() != null) {
                current.setCollection(modified.getCollection());
            }
//            if(!modified.getSubCategories().isEmpty()){
//                current.setSubCategories(modified.getSubCategories());
//            }
//            if(!modified.getCategories().isEmpty()){
//                current.getCategories().clear();
//                current.getCategories().addAll(modified.getCategories());
//            }
//            if(!modified.getRooms().isEmpty()){
//                current.getRooms().clear();
//                current.getRooms().addAll(modified.getRooms());
//            }
            itemService.save(current);
            return ResponseEntity.ok().body("L'article " + current.getId() + " a été modifié.");
        }
        return ResponseEntity.notFound().build();
    }
}
