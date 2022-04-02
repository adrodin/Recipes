package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import recipes.models.Recipe;
import recipes.repository.RecipeRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

import static recipes.security.UserAuthentication.getUsernameOfCurrentUser;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Integer save(Recipe recipe){
        recipe.setDate(LocalDateTime.now());
        recipe.setOwner(getUsernameOfCurrentUser());
        var saved = recipeRepository.save(recipe);
        return saved.getId();
    }

    public Optional<Recipe> getRecipeById(Integer id){
        return recipeRepository.findById(id);
    }

    public void deleteRecipeById(Integer id){
       recipeRepository.deleteById(id);
    }

    public void modifyRecipe(Integer id, Recipe recipe){
        recipe.setId(id);
        recipe.setOwner(getUsernameOfCurrentUser());
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
    }

    public List<Recipe> searchBy(Map<String, String> queryParameters){
        if(queryParameters.containsKey("name")){
            return byName(queryParameters.get("name"));
        }else if(queryParameters.containsKey("category")){
            return byCategory(queryParameters.get("category"));
        }else{
            return Collections.emptyList();
        }
    }

    private List<Recipe> byName(String name){
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
    private List<Recipe> byCategory(String category){
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public boolean isSearchParamValid(Map<String, String> queryParameters) {
        return queryParameters.size() == 1 &&
                (queryParameters.containsKey("name") || queryParameters.containsKey("category"));

    }

}
