package ru.clevertec.cheque.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.service.impl.CardService;

import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/cards", produces = "application/json")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<DiscountCard>> get(@RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer size) {
        return new ResponseEntity<>(cardService.findAll(page, size), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DiscountCard> get(@PathVariable Integer id) {
        return new ResponseEntity<>(cardService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DiscountCard> save(@Valid @RequestBody DiscountCard card) {
        return new ResponseEntity<>(cardService.save(card), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<DiscountCard> update(@Valid @RequestBody DiscountCard card) {
        return new ResponseEntity<>(cardService.update(card), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cardService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}