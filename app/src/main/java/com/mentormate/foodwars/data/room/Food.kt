package com.mentormate.foodwars.data.room

import androidx.room.*
import com.mentormate.foodwars.data.ScaleRating
import com.mentormate.foodwars.data.Taste
import com.mentormate.foodwars.domain.vo.details.FoodDetailsUIModel
import com.mentormate.foodwars.domain.vo.history.HistoryListItemUIModel
import com.mentormate.foodwars.domain.vo.main.FoodUIModel
import com.mentormate.foodwars.domain.vo.topten.TopTenEntityUIModel
import com.mentormate.foodwars.ui.constants.INVALID_ID
import com.mentormate.foodwars.ui.constants.INVALID_RES
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "food_table")
data class Food(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_id")
    val foodId: Long = INVALID_ID,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String = "",
    val type: Int = INVALID_RES,
    var taste: Taste = Taste.NOT_VOTED,
    val rating: ScaleRating = ScaleRating.NORMAL,
    @ColumnInfo(name = "spice_level")
    val spiceLevel: Int = 0
)

data class UpdateTaste(
    @ColumnInfo(name = "food_id")
    val foodId: Long = INVALID_ID,
    val taste: Taste = Taste.NOT_VOTED
)

@Entity(tableName = "characteristics_table")
data class Characteristic(
    @PrimaryKey
    @ColumnInfo(name = "characteristic")
    val characteristic: String = ""
)

@Entity(tableName = "related_items_table")
data class RelatedItemTable(
    @PrimaryKey
    @ColumnInfo(name = "related_item_name")
    val name: String,
    val imageUrl: String
)

@Entity(primaryKeys = ["food_id", "related_item_name"], tableName = "crossref_related_items_table")
data class FoodRelatedItemCrossRef(
    @ColumnInfo(name = "food_id")
    val foodId: Long,
    @ColumnInfo(name = "related_item_name")
    val relatedItemName: String
)

data class FoodWithRelatedItems(
    @Embedded val food: Food,
    @Relation(
        parentColumn = "food_id",
        entityColumn = "related_item_name",
        associateBy = Junction(FoodRelatedItemCrossRef::class)
    )
    val relatedItems: List<RelatedItemTable>
)

@Entity(primaryKeys = ["food_id", "characteristic"], tableName = "crossref_table")
data class FoodCharacteristicCrossRef(
    @ColumnInfo(name = "food_id")
    val foodId: Long,
    @ColumnInfo(name = "characteristic")
    val characteristic: String
)

data class FoodWithCharacteristics(
    @Embedded val food: Food,
    @Relation(
        parentColumn = "food_id",
        entityColumn = "characteristic",
        associateBy = Junction(FoodCharacteristicCrossRef::class)
    )
    val characteristics: List<Characteristic>
)

fun Food.toFoodUIModel() = FoodUIModel(
    id = foodId,
    name = name,
    imageUrl = imageUrl,
    type = type,
    rating = rating
)

fun Food.toHistoryListItemUIModel() = HistoryListItemUIModel(
    nameRes = name,
    resource = imageUrl,
    type = type,
    food = this
)

fun FoodWithRelatedItems.toFoodDetailsUIModel() = FoodDetailsUIModel(
    foodName = food.name,
    imageResource = food.imageUrl,
    type = food.type,
    rating = food.rating,
    listRelatedItems = relatedItems
)

fun FoodWithCharacteristics.toTopTenEntityUIModel() = TopTenEntityUIModel(
    resource = food.imageUrl,
    nameRes = food.name,
    type = food.type
)