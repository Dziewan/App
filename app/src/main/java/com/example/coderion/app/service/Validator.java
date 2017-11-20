package com.example.coderion.app.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderion on 20.11.17.
 */

public class Validator {

    String wymiar;
    List<String> odpowiedz;

    public Validator(String wymiar) {
        this.wymiar = wymiar;
        odpowiedz = new ArrayList<>();
    }

    public List<String> validate() {
        validateSize();
        return odpowiedz;
    }

    public void validateSize() {
        int x = 0;
        boolean nieLiczba = false;
        for (char znak : wymiar.toCharArray()) {
            if (znak == 'x') {
                x++;
                continue;
            }
            if (!Character.isDigit(znak)) {
                nieLiczba = true;
            }
        }
        if (x != 1 || nieLiczba) odpowiedz.add("Błędne dane w wymiarze");
    }
}
