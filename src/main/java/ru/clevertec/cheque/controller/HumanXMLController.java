package ru.clevertec.cheque.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.cheque.entity.Human;
import ru.clevertec.cheque.service.impl.HumanService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/humansXml", produces = "application/xml")
public class HumanXMLController {

    @Autowired
    private HumanService humanService;

    @GetMapping
    public ResponseEntity<List<Human>> get() {
        return new ResponseEntity<>(humanService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Human> get(@PathVariable Integer id) {
        return new ResponseEntity<>(humanService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Human> save(@Valid @RequestBody Human human) {
        humanService.save(human);
        return new ResponseEntity<>(human, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Human> update(@Valid @RequestBody Human human) {
        humanService.update(human);
        return new ResponseEntity<>(human, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        humanService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
