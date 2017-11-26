package com.example.coderion.app.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by coderion on 20.11.17.
 */

public interface Values {
    String MAIN_LINK = "http://dziewanowski.coderion.eu/md/api/main/";
    String ADD_NEW_BOARD = "addNew";
    String FIND_BY_ID = "getById";
    String FIND_ALL = "getAll";
    String DELETE_BY_ID = "deleteById";
    List<String> MATERIALS = Arrays.asList("Tekstolit", "Zielone szkło", "Brązowe szkło", "Czerwone szkło", "Delmat Epoxy", "Czarne szkło", "Żółte szkło");
}
