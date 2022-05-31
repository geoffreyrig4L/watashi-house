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
                                             @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<Item> itemsList = itemService.getAll(page, sortBy, orderBy);
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/couleur={couleur}")
    public ResponseEntity<Page<Item>> getItemsFilteredByColor(@RequestParam("page") final Optional<Integer> page,
                                                                    @RequestParam("sortBy") final Optional<String> sortBy,
                                                                    @RequestParam("orderBy") final Optional<String> orderBy,
                                                                    @PathVariable("couleur") final String couleur) {
        return ResponseEntity.ok(itemService.getItemsFilteredByColor(page, sortBy, orderBy, couleur));
    }

    @GetMapping("/prixMin={min}/prixMax={max}")
    public ResponseEntity<Page<Item>> getItemsFilteredByPrice(@RequestParam("page") final Optional<Integer> page,
                                                                    @RequestParam("sortBy") final Optional<String> sortBy,
                                                                    @RequestParam("orderBy") final Optional<String> orderBy,
                                                                    @PathVariable("min") final int min,
                                                                    @PathVariable("max") final int max) {
        return ResponseEntity.ok(itemService.getItemsFilteredByPrice(page, sortBy, orderBy, min, max));
    }

    @GetMapping("/categorie={id_category}")
    public ResponseEntity<Page<Item>> getItemsOfCategory(@PathVariable("id_category") final int id_category,
                                                               @RequestParam("page") final Optional<Integer> page,
                                                               @RequestParam("sortBy") final Optional<String> sortBy,
                                                               @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<Item> itemsList = itemService.getItemsOfCategory(page, sortBy, orderBy, id_category);
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/souscategorie={id_souscategorie}")
    public ResponseEntity<Page<Item>> getItemsOfSubCategory(@PathVariable("id_souscategorie") final int id_souscategorie,
                                                                   @RequestParam("page") final Optional<Integer> page,
                                                                   @RequestParam("sortBy") final Optional<String> sortBy,
                                                                   @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<Item> itemsList = itemService.getItemsOfSubCategory(page, sortBy, orderBy, id_souscategorie);
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/piece={id_piece}")
    public ResponseEntity<Page<Item>> getItemsOfRoom(@PathVariable("id_piece") final int id_piece,
                                                           @RequestParam("page") final Optional<Integer> page,
                                                           @RequestParam("sortBy") final Optional<String> sortBy,
                                                           @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<Item> itemsList = itemService.getItemsOfRoom(page, sortBy, orderBy, id_piece);
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> get(@PathVariable("id") final int id) {
        Optional<Item> item = itemService.get(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        }
        return ResponseEntity.notFound().build();
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
    public ResponseEntity<String> delete(@PathVariable("id") final int id) {
        Optional<Item> optItem = itemService.get(id);
        if (optItem.isPresent()) {
            itemService.delete(id);
            return ResponseEntity.ok().body("L'article a été supprimé.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody Item modified) {
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
            itemService.save(current);
            return ResponseEntity.ok().body("L'article " + current.getId() + " a été modifié.");
        }
        return ResponseEntity.notFound().build();
    }
}
