package com.example.apapunada.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apapunada.data.dataclass.FoodDetails
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.data.dataclass.NutritionFacts
import com.example.apapunada.data.repository.FoodDetailsRepository
import com.example.apapunada.data.repository.MenuItemRepository
import com.example.apapunada.data.repository.NutritionFactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class MenuItemState(
    val isLoading: Boolean = false,
    val menuItem: MenuItem = MenuItem(),
    val isValid: Boolean = false,
    val errorMessage: String = ""
)

data class MenuListState(
    val isLoading: Boolean = false,
    val menuItemList: List<MenuItem> = listOf(MenuItem()),
    val errorMessage: String = ""
)

data class FoodDetailsState(
    val foodDetails: FoodDetails = FoodDetails(),
    val isValid: Boolean = false,
    val errorMessage: String = ""
)

data class NutritionFactsState(
    val nutritionFacts: NutritionFacts = NutritionFacts(),
    val isValid: Boolean = false,
    val errorMessage: String = ""
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

    private val _menuItemState = MutableStateFlow(MenuItemState())
    val menuItemState: StateFlow<MenuItemState> = _menuItemState.asStateFlow()

    private val _menuListState = MutableStateFlow(MenuListState())
    val menuListState: StateFlow<MenuListState> = _menuListState.asStateFlow()

    private val _foodDetailsState = MutableStateFlow(FoodDetailsState())
    val foodDetailsState: StateFlow<FoodDetailsState> = _foodDetailsState.asStateFlow()

    private val _nutritionFactsState = MutableStateFlow(NutritionFactsState())
    val nutritionFactsState: StateFlow<NutritionFactsState> = _nutritionFactsState.asStateFlow()

    fun loadAllMenuItem() {
        viewModelScope.launch(Dispatchers.IO) {
            menuItemRepository.getAllMenuItemsStream()
                .map { MenuListState(isLoading = false, menuItemList = it) }
                .onStart { emit(MenuListState(isLoading = true)) }
                .catch {
                    emit(MenuListState(errorMessage = it.message.toString()))
                    Log.i("MenuItem", "loadAllMenuItems: " + it.message.toString())
                }
                .collect { _menuListState.value = it }
        }
    }

    fun loadMenuItemByMenuItemId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            menuItemRepository.getMenuItemByMenuItemIdStream(id)
                .map { MenuItemState(isLoading = false, menuItem = it) }
                .onStart { emit(MenuItemState(isLoading = true)) }
                .catch {
                    emit(MenuItemState(errorMessage = it.message.toString()))
                    Log.i(
                        "MenuItem", "loadMenuItemByMenuItemId: " + it.message.toString()
                    )
                }
                .collect { _menuItemState.value = it }
        }
    }

    fun loadFoodDetailsByMenuItemId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            foodDetailsRepository.getFoodDetailsByMenuItemIdStream(id)
                .map { FoodDetailsState(foodDetails = it) }
                .catch {
                    emit(FoodDetailsState(errorMessage = it.message.toString()))
                    Log.i("FoodDetails", "loadFoodDetailsByMenuItemId: "
                            + it.message.toString())
                }
                .collect { _foodDetailsState.value = it }
        }
    }

    fun loadNutritionFactsByFoodDetailsId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            nutritionFactsRepository.getNutritionFactsByFoodDetailsIdStream(id)
                .map { NutritionFactsState(nutritionFacts = it) }
                .catch {
                    emit(NutritionFactsState(errorMessage = it.message.toString()))
                    Log.i("NutritionFacts", "loadNutritionFactsByFoodDetailsId: "
                                + it.message.toString()
                    )
                }
                .collect { _nutritionFactsState.value = it }
        }
    }

    private fun validateMenuItemInput(): Boolean {
        return with(_menuItemState.value.menuItem) {
            cuisine.isNotBlank() && price.isNaN() // TODO
        }
    }

    private fun validateFoodDetailsInput(): Boolean {
        return with(_foodDetailsState.value.foodDetails) {
            servingSize.isNaN() // TODO
        }
    }

    private fun validateNutritionFactsInput(): Boolean {
        return with(_nutritionFactsState.value.nutritionFacts) {
            carbohydrates.isNaN() // TODO
        }
    }

    fun updateMenuItemState(menuItem: MenuItem) {
        _menuItemState.value = _menuItemState.value.copy(
            menuItem = menuItem, isValid = validateMenuItemInput()
        )
    }

    fun updateFoodDetailsState(foodDetails: FoodDetails) {
        _foodDetailsState.value = FoodDetailsState(
            foodDetails = foodDetails, isValid = validateFoodDetailsInput()
        )
    }

    fun updateNutritionFactsState(nutritionFacts: NutritionFacts) {
        _nutritionFactsState.value = NutritionFactsState(
            nutritionFacts = nutritionFacts, isValid = validateNutritionFactsInput()
        )
    }

    fun saveMenuItem() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateMenuItemInput()) {
                try {
                    menuItemRepository.insertMenuItem(menuItemState.value.menuItem)
                } catch (e: Exception) {
                    Log.i("MenuItem", "saveMenuItem: " + e.message.toString())
                }
            }
        }
    }

    fun updateMenuItem() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateMenuItemInput()) {
                try {
                    menuItemRepository.updateMenuItem(menuItemState.value.menuItem)
                } catch (e: Exception) {
                    Log.i("MenuItem", "updateMenuItem: " + e.message.toString())
                }
            }
        }
    }

    private suspend fun deleteMenuItem() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateMenuItemInput()) {
                try {
                    menuItemRepository.deleteMenuItem(menuItemState.value.menuItem)
                } catch (e: Exception) {
                    Log.i("MenuItem", "deleteMenuItem: " + e.message.toString())
                }
            }
        }
    }

    fun saveFoodDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateFoodDetailsInput()) {
                try {
                    foodDetailsRepository.insertFoodDetails(foodDetailsState.value.foodDetails)
                } catch (e: Exception) {
                    Log.i("FoodDetails", "saveFoodDetails: " + e.message.toString())
                }
            }
        }
    }

    fun updateFoodDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateFoodDetailsInput()) {
                try {
                    foodDetailsRepository.updateFoodDetails(foodDetailsState.value.foodDetails)
                } catch (e: Exception) {
                    Log.i("FoodDetails", "updateFoodDetails: " + e.message.toString())
                }
            }
        }
    }

    private suspend fun deleteFoodDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateFoodDetailsInput()) {
                try {
                    foodDetailsRepository.deleteFoodDetails(foodDetailsState.value.foodDetails)
                } catch (e: Exception) {
                    Log.i("FoodDetails", "deleteFoodDetails: " + e.message.toString())
                }
            }
        }
    }

    fun saveNutritionFacts() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateNutritionFactsInput()) {
                try {
                    nutritionFactsRepository
                        .insertNutritionFacts(nutritionFactsState.value.nutritionFacts)
                } catch (e: Exception) {
                    Log.i("NutritionFacts", "saveNutritionFacts: " + e.message.toString())
                }
            }
        }
    }

    fun updateNutritionFacts() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateNutritionFactsInput()) {
                try {
                    nutritionFactsRepository
                        .updateNutritionFacts(nutritionFactsState.value.nutritionFacts)
                } catch (e: Exception) {
                    Log.i("NutritionFacts", "updateNutritionFacts: "
                            + e.message.toString())
                }
            }
        }
    }

    private suspend fun deleteNutritionFacts() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateNutritionFactsInput()) {
                try {
                    nutritionFactsRepository
                        .deleteNutritionFacts(nutritionFactsState.value.nutritionFacts)
                } catch (e: Exception) {
                    Log.i("NutritionFacts", "deleteNutritionFacts: "
                            + e.message.toString())
                }
            }
        }
    }
}