package com.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    // POST /recipes -> レシピを作成
    @PostMapping
    public ResponseEntity<Map<String, Object>> createRecipe(@RequestBody Map<String, String> payload) {
        Map<String, Object> response = new LinkedHashMap<>();

        // 必須パラメーターのバリデーションチェック
        if (!payload.containsKey("title") || !payload.containsKey("making_time") ||
                !payload.containsKey("serves") || !payload.containsKey("ingredients") ||
                !payload.containsKey("cost")) {

            response.put("message", "Recipe creation failed!");
            response.put("required", "title, making_time, serves, ingredients, cost");
            return ResponseEntity.ok(response);
        }

        // 新規レシピの保存
        Recipe recipe = new Recipe();
        recipe.setTitle(payload.get("title"));
        recipe.setMaking_time(payload.get("making_time"));
        recipe.setServes(payload.get("serves"));
        recipe.setIngredients(payload.get("ingredients"));
        recipe.setCost(payload.get("cost"));

        Recipe savedRecipe = recipeRepository.save(recipe);

        response.put("message", "Recipe successfully created!");
        response.put("recipe", Collections.singletonList(savedRecipe));
        return ResponseEntity.ok(response);
    }

    // GET /recipes -> 全レシピ一覧を返す
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("recipes", recipes);
        return ResponseEntity.ok(response);
    }

    // GET /recipes/{id} -> 指定レシピ一つを返す
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRecipeById(@PathVariable Integer id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        Map<String, Object> response = new LinkedHashMap<>();

        if (optionalRecipe.isPresent()) {
            response.put("message", "Recipe details by id");
            response.put("recipe", Collections.singletonList(optionalRecipe.get()));
        } else {
            response.put("message", "No Recipe found");
        }
        return ResponseEntity.ok(response);
    }

    // PATCH /recipes/{id} -> 指定レシピを更新
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRecipe(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        Map<String, Object> response = new LinkedHashMap<>();

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();

            if (payload.containsKey("title")) recipe.setTitle(payload.get("title"));
            if (payload.containsKey("making_time")) recipe.setMaking_time(payload.get("making_time"));
            if (payload.containsKey("serves")) recipe.setServes(payload.get("serves"));
            if (payload.containsKey("ingredients")) recipe.setIngredients(payload.get("ingredients"));
            if (payload.containsKey("cost")) recipe.setCost(payload.get("cost"));

            Recipe updatedRecipe = recipeRepository.save(recipe);

            response.put("message", "Recipe successfully updated!");
            response.put("recipe", Collections.singletonList(updatedRecipe));
        } else {
            response.put("message", "No Recipe found");
        }
        return ResponseEntity.ok(response);
    }

    // DELETE /recipes/{id} -> 指定レシピの削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRecipe(@PathVariable Integer id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (optionalRecipe.isPresent()) {
            recipeRepository.delete(optionalRecipe.get());
            response.put("message", "Recipe successfully removed!");
        } else {
            response.put("message", "No Recipe found");
        }
        return ResponseEntity.ok(response);
    }
}