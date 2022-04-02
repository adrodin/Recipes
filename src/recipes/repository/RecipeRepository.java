package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.models.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe,Integer> {

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
