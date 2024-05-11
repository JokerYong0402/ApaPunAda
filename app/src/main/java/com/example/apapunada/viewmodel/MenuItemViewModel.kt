package com.example.apapunada.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.apapunada.data.dataclass.FoodDetails
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.data.dataclass.NutritionFacts
import com.example.apapunada.data.repository.FoodDetailsRepository
import com.example.apapunada.data.repository.MenuItemRepository
import com.example.apapunada.data.repository.NutritionFactsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

data class MenuItemState(
    val menuItem: MenuItem = MenuItem(),
    val isValid: Boolean = false
)

data class MenuListState(
    val isLoading: Boolean = false,
    val menuItemList: List<MenuItem> = listOf(MenuItem()),
    val errorMessage: String = ""
)

data class FoodDetailsState(
    val foodDetails: FoodDetails = FoodDetails(),
    val isValid: Boolean = false
)

data class NutritionFactsState(
    val nutritionFacts: NutritionFacts = NutritionFacts(),
    val isValid: Boolean = false
)

enum class Cuisine(name: String) {
    Western("Western"),
    Japanese("Japanese"),
    Korean("Korean"),
    Malaysian("Malaysian"),
    Thai("Thai"),
    Beverage("Beverage"),
}

enum class MenuItemStatus(name: String) {
    Active("Active"),
    Disabled("Disabled"),
    Deleted("Deleted"),
}

class MenuItemViewModel(
    private val menuItemRepository: MenuItemRepository,
    private val foodDetailsRepository: FoodDetailsRepository,
    private val nutritionFactsRepository: NutritionFactsRepository
): ViewModel() {

    var menuItemState by mutableStateOf(MenuItemState())
        private set

    var menuListState by mutableStateOf(MenuListState())
        private set

    var foodDetailsState by mutableStateOf(FoodDetailsState())
        private set

    var nutritionFactsState by mutableStateOf(NutritionFactsState())
        private set

    fun loadAllMenuItem() {
        menuItemRepository.getAllMenuItemsStream()
            .map { MenuListState(isLoading = false, menuItemList = it) }
            .onStart { emit(MenuListState(isLoading = true)) }
            .catch { emit(MenuListState(errorMessage = it.message.toString()))
                Log.i("MenuItem", "loadAllMenuItems: " + it.message.toString()) }
    }

    fun loadMenuItemByMenuItemId(id: Int) {
        menuItemRepository.getMenuItemByMenuItemIdStream(id)
            .map { MenuItemState(menuItem = it) }
            .catch { Log.i("MenuItem", "loadMenuItemByMenuItemId: "
                    + it.message.toString())
            }
    }

    fun loadFoodDetailsByMenuItemId(id: Int) {
        foodDetailsRepository.getFoodDetailsByMenuItemIdStream(id)
            .map { FoodDetailsState(foodDetails = it) }
            .catch { Log.i("FoodDetails", "loadFoodDetailsByMenuItemId: "
                    + it.message.toString())
            }
    }

    fun loadNutritionFactsByFoodDetailsId(id: Int) {
        nutritionFactsRepository.getNutritionFactsByFoodDetailsIdStream(id)
            .map { NutritionFactsState(nutritionFacts = it) }
            .catch { Log.i("NutritionFacts", "loadNutritionFactsByFoodDetailsId: "
                    + it.message.toString())
            }
    }

    private fun validateMenuItemInput(uiState: MenuItem = menuItemState.menuItem): Boolean {
        return with(uiState) {
            cuisine.isNotBlank() && price.isNaN() // TODO
        }
    }

    private fun validateFoodDetailsInput(
        uiState: FoodDetails = foodDetailsState.foodDetails
    ): Boolean {
        return with(uiState) {
            servingSize.isNaN() // TODO
        }
    }

    private fun validateNutritionFactsInput(
        uiState: NutritionFacts = nutritionFactsState.nutritionFacts
    ): Boolean {
        return with(uiState) {
            carbohydrates.isNaN() // TODO
        }
    }

    fun updateMenuItemState(menuItem: MenuItem) {
        menuItemState = MenuItemState(
            menuItem = menuItem, isValid = validateMenuItemInput(menuItem)
        )
    }

    fun updateFoodDetailsState(foodDetails: FoodDetails) {
        foodDetailsState = FoodDetailsState(
            foodDetails = foodDetails, isValid = validateFoodDetailsInput(foodDetails)
        )
    }

    fun updateNutritionFactsState(nutritionFacts: NutritionFacts) {
        nutritionFactsState = NutritionFactsState(
            nutritionFacts = nutritionFacts, isValid = validateNutritionFactsInput(NutritionFacts())
        )
    }

    suspend fun saveMenuItem() {
        if (validateMenuItemInput()) {
            try {
                menuItemRepository.insertMenuItem(menuItemState.menuItem)
            } catch (e: Exception) {
                Log.i("MenuItem", "saveMenuItem: " + e.message.toString())
            }
        }
    }

    suspend fun updateMenuItem() {
        if (validateMenuItemInput()) {
            try {
                menuItemRepository.updateMenuItem(menuItemState.menuItem)
            } catch (e: Exception) {
                Log.i("MenuItem", "updateMenuItem: " + e.message.toString())
            }
        }
    }

    suspend fun deleteMenuItem() {
        if (validateMenuItemInput()) {
            try {
                menuItemRepository.deleteMenuItem(menuItemState.menuItem)
            } catch (e: Exception) {
                Log.i("MenuItem", "deleteMenuItem: " + e.message.toString())
            }
        }
    }

    suspend fun saveFoodDetails() {
        if (validateFoodDetailsInput()) {
            try {
                foodDetailsRepository.insertFoodDetails(foodDetailsState.foodDetails)
            } catch (e: Exception) {
                Log.i("FoodDetails", "saveFoodDetails: " + e.message.toString())
            }
        }
    }

    suspend fun updateFoodDetails() {
        if (validateFoodDetailsInput()) {
            try {
                foodDetailsRepository.updateFoodDetails(foodDetailsState.foodDetails)
            } catch (e: Exception) {
                Log.i("FoodDetails", "updateFoodDetails: " + e.message.toString())
            }
        }
    }

    suspend fun deleteFoodDetails() {
        if (validateFoodDetailsInput()) {
            try {
                foodDetailsRepository.deleteFoodDetails(foodDetailsState.foodDetails)
            } catch (e: Exception) {
                Log.i("FoodDetails", "deleteFoodDetails: " + e.message.toString())
            }
        }
    }

    suspend fun saveNutritionFacts() {
        if (validateNutritionFactsInput()) {
            try {
                nutritionFactsRepository.insertNutritionFacts(nutritionFactsState.nutritionFacts)
            } catch (e: Exception) {
                Log.i("NutritionFacts", "saveNutritionFacts: " + e.message.toString())
            }
        }
    }

    suspend fun updateNutritionFacts() {
        if (validateNutritionFactsInput()) {
            try {
                nutritionFactsRepository.updateNutritionFacts(nutritionFactsState.nutritionFacts)
            } catch (e: Exception) {
                Log.i("NutritionFacts", "updateNutritionFacts: " + e.message.toString())
            }
        }
    }

    suspend fun deleteNutritionFacts() {
        if (validateNutritionFactsInput()) {
            try {
                nutritionFactsRepository.deleteNutritionFacts(nutritionFactsState.nutritionFacts)
            } catch (e: Exception) {
                Log.i("NutritionFacts", "deleteNutritionFacts: " + e.message.toString())
            }
        }
    }
}