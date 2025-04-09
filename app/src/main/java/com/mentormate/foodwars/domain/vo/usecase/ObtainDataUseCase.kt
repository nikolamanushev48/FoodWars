package com.mentormate.foodwars.domain.vo.usecase

import android.util.Log
import com.mentormate.foodwars.data.network.model.toFood
import com.mentormate.foodwars.data.repository.food.FoodRepository
import com.mentormate.foodwars.data.room.*
import javax.inject.Inject

class ObtainDataUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) : UseCase<Long>() {

    override suspend fun execute(parameters: Long) {
        val characteristicsCrossRefs = mutableListOf<FoodCharacteristicCrossRef>()
        val relatedItemsCrossRefs = mutableListOf<FoodRelatedItemCrossRef>()


        Log.d("testt","HERE!")
        val foodList =
            foodRepository.getAllFoodsByUserIdRemote(parameters).response.voteItemsInUserContext

        Log.d("testt","FOOOD LIAST: " + foodList)
        val characteristicSet: MutableSet<Characteristic> = mutableSetOf()
        val relatedItemsSet: MutableSet<RelatedItemTable> = mutableSetOf()

        val listFoodObjects: MutableList<Food> = mutableListOf()

        foodList.forEach {
            it.item.characteristicList.forEach { characteristic ->
                characteristicSet.add(Characteristic(characteristic))
            }
            it.item.relatedItems.forEach { relatedItem ->
                relatedItemsSet.add(RelatedItemTable(relatedItem.name, relatedItem.imageUrl))
            }

            listFoodObjects.add(it.item.toFood())
        }

        with(foodRepository) {
            clearAllFoods()
            insertCharacteristics(characteristicSet.toList())
            insertRelatedItems(relatedItemsSet.toList())
            insertAllFood(listFoodObjects).let { foodIndexedList ->
                foodList.forEachIndexed { index, foodItem ->
                    foodItem.item.characteristicList.forEach { characteristicId ->
                        characteristicsCrossRefs.add(
                            FoodCharacteristicCrossRef(
                                foodIndexedList[index],
                                characteristicId
                            )
                        )
                    }

                    foodItem.item.relatedItems.forEach { relatedItem ->
                        relatedItemsCrossRefs.add(
                            FoodRelatedItemCrossRef(
                                foodIndexedList[index],
                                relatedItem.name
                            )
                        )
                    }
                }
            }

            insertFoodWithCharacteristicCrossRefs(characteristicsCrossRefs)
            insertFoodWithRelatedItemCrossRefs(relatedItemsCrossRefs)
        }
    }
}