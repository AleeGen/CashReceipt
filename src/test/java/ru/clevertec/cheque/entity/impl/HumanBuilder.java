package ru.clevertec.cheque.entity.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.cheque.entity.EntityBuilder;
import ru.clevertec.cheque.entity.Human;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aHuman")
public class HumanBuilder implements EntityBuilder<Human> {

    private int id = 1;
    private String gender = "male";
    private int age = 11;
    private String name = "Ivan";
    private boolean higherEducation = false;

    @Override
    public Human build() {
        final Human human = new Human();
        human.setId(id);
        human.setGender(gender);
        human.setAge(age);
        human.setName(name);
        human.setHigherEducation(higherEducation);
        return human;
    }
}
