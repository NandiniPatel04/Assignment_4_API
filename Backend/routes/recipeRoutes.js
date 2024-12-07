//FileName: reciperoutes.js
//studentname: Nandini
//studentId: 200596236
//Date: DeC 04
const authenticateToken = require("../auth/jwtMiddleware");
const express = require("express");
const router = express.Router();
const {
  getAllRecipes,
  getRecipeById,
  addRecipe,
  updateRecipe,
  deleteRecipe,
} = require("../controllers/recipeController");

router.get("/", [authenticateToken], getAllRecipes); // GET /recipes
router.get("/:id", [authenticateToken], getRecipeById); // GET /recipes/:id
router.post("/", [authenticateToken], addRecipe); // POST /recipes
router.put("/:id", [authenticateToken], updateRecipe); // PUT /recipes/:id
router.delete("/:id", [authenticateToken], deleteRecipe); // DELETE /recipes/:id

module.exports = router;
