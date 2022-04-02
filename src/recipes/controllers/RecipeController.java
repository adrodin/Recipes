package recipes.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.exceptions.NoRequiredRightsException;
import recipes.exceptions.RecipeNotFoundException;
import recipes.models.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

import static recipes.security.UserAuthentication.isCurrentUserOwner;

@RestController
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;

    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> addRecipe(@Valid @RequestBody Recipe recipe){
        var savedId = recipeService.save(recipe);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("id", savedId));
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable Integer id){
        var recipe = recipeService.getRecipeById(id).orElse(null);
        if(recipe == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        }
    }


    @DeleteMapping(value = "/api/recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable("id") int id) {
        Recipe recipe = recipeService.getRecipeById(id).orElseThrow(RecipeNotFoundException::new);
        if(isCurrentUserOwner(recipe.getOwner()))
        {
            recipeService.deleteRecipeById(id);
        }
        else {
            throw new NoRequiredRightsException();
        }
    }

    @PutMapping("/api/recipe/{id}")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void modifyRecipe(@PathVariable Integer id, @RequestBody @Valid Recipe recipe){
        Recipe oldRecipe = recipeService.getRecipeById(id).orElseThrow(RecipeNotFoundException::new);
        if(isCurrentUserOwner(oldRecipe.getOwner())){
            recipeService.modifyRecipe(id,recipe);
        }else{
            throw new NoRequiredRightsException();
        }
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<?> searchRecipe(@RequestParam Map<String,String> queryParameters){
        if(!recipeService.isSearchParamValid(queryParameters)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
        var recipes = recipeService.searchBy(queryParameters);
        return ResponseEntity.status(HttpStatus.OK).body(recipes);

    }


}
