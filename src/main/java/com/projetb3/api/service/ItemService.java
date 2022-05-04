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
        System.out.println(itemRepository);
        return itemRepository.findById(id_item);
    }

    public Page<Item> getAll(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy) {
        return itemRepository.findAll(PageRequest.of(
                        page.orElse(0),
                        20,
                        getOrder(orderBy),
                        sortBy.orElse("id")
                )
        );
    }

    public void delete(final int id_item) {
        itemRepository.deleteById(id_item);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Page<Item> getItemsFilteredByColor(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, String color) {
        List<Item> itemsList = itemRepository.itemsByColor(color);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(itemsList, pageable, itemsList.size());
    }

    public Page<Item> getItemsFilteredByPrice(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, int min, int max) {
        List<Item> itemsList = itemRepository.itemsByPrice(min, max);
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
}