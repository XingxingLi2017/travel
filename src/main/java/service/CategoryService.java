package service;

import domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * return all the categories in nav bar
     * */
    List<Category> findAll();
}
