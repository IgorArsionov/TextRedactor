package com.example.textredactor.engine.repository;

import com.example.textredactor.engine.model.Man;

import java.io.File;
import java.util.List;

public interface AddManRepository {

    String folder = "Men";

    void addMan(Man man);

    List<Man> getMans();

    default void createFolder() {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
