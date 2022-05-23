package com.projetb3.api.service;

import com.projetb3.api.model.Item;
import com.projetb3.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Optional<Item> get(final int id_item) {
        return itemRepository.findById(id_item);
    }

    public Page<Item> getAll(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, Optional<String> color, Optional<Integer> priceMin, Optional<Integer> priceMax) {
        Page<Item> itemsList;
        switch (filterParam(color, priceMin, priceMax)) {
            case "without" : itemsList = withoutFilter(page, sortBy, orderBy); break;
            case "color" : itemsList = filteredByColor(page, sortBy, orderBy, color.get()); break;
            case "price" : itemsList = filteredByPrice(page, sortBy, orderBy, priceMin.get(), priceMax.get()); break;
            case "colorprice" : itemsList = filteredByBoth(page, sortBy, orderBy, color.get(), priceMin.get(), priceMax.get()); break;
            default : itemsList = null; break;
        }
        return itemsList;
    }

    private String filterParam(Optional<String> color, Optional<Integer> priceMin, Optional<Integer> priceMax) {
        if(color.isEmpty() && priceMin.isEmpty() && priceMax.isEmpty()){
            return "without";
        } else if (color.isPresent() && priceMin.isEmpty() && priceMax.isEmpty()){
            return "color";
        } else if (color.isEmpty() && priceMin.isPresent() && priceMax.isPresent()){
            return "price";
        } else if (color.isPresent() && priceMin.isPresent() && priceMax.isPresent()){
            return "colorprice";
        }
        return "";
    }

    private Page<Item> withoutFilter(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy) {
        return itemRepository.findAll(PageRequest.of(
                        page.orElse(0),
                        20,
                        getOrder(orderBy),
                        sortBy.orElse("id")
                )
        );
    }

    public Page<Item> filteredByColor(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, String color) {
        List<Item> itemsList = itemRepository.itemsByColor(color);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(itemsList, pageable, itemsList.size());
    }

    public Page<Item> filteredByPrice(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, int min, int max) {
        List<Item> itemsList = itemRepository.itemsByPrice(min, max);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(itemsList, pageable, itemsList.size());
    }

    private Page<Item> filteredByBoth(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, String color, Integer priceMin, Integer priceMax) {
        List<Item> itemsList = itemRepository.itemsByColorAndPrice(priceMin, priceMax);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(itemsList, pageable, itemsList.size());
    }

    public static Sort.Direction getOrder(Optional<String> orderBy) {
        if (orderBy.isPresent() && orderBy.get().equals("ASC")) {
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

    public Page<Item> getItemsOfCategory(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, int id_category) {
        List<Item> itemsList = itemRepository.itemsOfCategory(id_category);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(itemsList, pageable, itemsList.size());
    }

    public Page<Item> getItemsOfSubCategory(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, int id_subCategory) {
        List<Item> itemsList = itemRepository.itemsOfSubCategory(id_subCategory);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(itemsList, pageable, itemsList.size());
    }
    public Page<Item> getItemsOfRoom(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, int id_room) {
        List<Item> itemsList = itemRepository.itemsOfRoom(id_room);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(itemsList, pageable, itemsList.size());
    }

    public void delete(final int id_item) {
        itemRepository.deleteById(id_item);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }
}