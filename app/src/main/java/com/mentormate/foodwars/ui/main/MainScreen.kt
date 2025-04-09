package com.mentormate.foodwars.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mentormate.foodwars.R
import com.mentormate.foodwars.domain.vo.main.FoodUIModel
import com.mentormate.foodwars.utils.Direction
import com.mentormate.foodwars.utils.SwipeableCardState
import com.mentormate.foodwars.utils.rememberSwipeableCardState
import com.mentormate.foodwars.utils.swappableCard

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { FoodWarsToolbar() },
        bottomBar = { FoodWarsBottomNavigation(viewModel) },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            val dataItems = uiState.foodUIModelList
                .map { it to rememberSwipeableCardState() }

            if (dataItems.isNotEmpty()) {
                DefaultState(
                    dataItems = dataItems,
                    onSwiped = { itemId, direction -> viewModel.updateItem(itemId, direction) },
                    onCardClick = { viewModel.cardImageOnClicked(it) },
                    onShareButtonClick = { viewModel.shareFood(it) }
                )
            } else {
                EmptyState()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodWarsToolbar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.primary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@Composable
fun FoodWarsBottomNavigation(viewModel: MainViewModel) {
    val bottomMenuItemsList = prepareBottomMenu()

    val state = stringResource(id = R.string.home)
    var selectedItem by remember {
        mutableStateOf(state)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BottomNavigation(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            bottomMenuItemsList.forEach { menuItem ->
                val text = stringResource(id = menuItem.label)
                BottomNavigationItem(
                    selected = (selectedItem == text),
                    onClick = {
                        selectedItem = text
                        viewModel.bottomMenuStateHandling(menuItem.label)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = menuItem.icon),
                            contentDescription = stringResource(id = menuItem.label),
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = menuItem.label),
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    enabled = true
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FoodCard(
    modifier: Modifier,
    foodUI: FoodUIModel,
    dynamicColor: Color,
    notificationPermission: ManagedActivityResultLauncher<String, Boolean>,
    onCardClick: (foodId: Long) -> Unit,
    onShareButtonClick: (foodUI: FoodUIModel) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(20.dp)
            .shadow(
                elevation = 20.dp,
                ambientColor = dynamicColor,
                spotColor = dynamicColor,
                shape = RoundedCornerShape(20.dp)
            )
            .size(300.dp, 300.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onCardClick(foodUI.id)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = {
                    onShareButtonClick(foodUI)
                },
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.size(150.dp, 50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.share),
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Log.d("testt","Image: " + foodUI.imageUrl)
            GlideImage(
                alignment = Alignment.Center,
                model = foodUI.imageUrl,
                contentDescription = foodUI.name
            ) {
                it.load(foodUI.imageUrl)
                    .error(R.drawable.error_icon)
                    .fitCenter()
                    .placeholder(R.drawable.refresh_icon)
                    .override(800, 900)
            }

            Text(
                text = foodUI.name,
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun DefaultState(
    dataItems: List<Pair<FoodUIModel, SwipeableCardState>>,
    onSwiped: (foodId: Long, direction: Direction) -> Unit,
    onCardClick: (foodId: Long) -> Unit,
    onShareButtonClick: (foodUI: FoodUIModel) -> Unit,
) {
    val notificationPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    var color by remember { mutableStateOf(Color.Transparent) }

    val defaultColor = colorResource(id = R.color.black)
    val redCardColor = colorResource(id = R.color.red_card_color)
    val greenCardColor = colorResource(id = R.color.green_card_color)

    Log.d("testt","data items:" + dataItems)

    dataItems.forEach { (item, direction) ->
        if (direction.swipedDirection == null) {
            FoodCard(
                modifier = Modifier
                    .swappableCard(
                        state = direction,
                        onSwiped = {
                            onSwiped.invoke(item.id, it)
                        },
                        onDrag = {
                            color = when (it) {
                                Direction.Right -> redCardColor
                                Direction.Left -> greenCardColor
                                else -> defaultColor
                            }
                        },
                        onDragEnd = {
                            color = defaultColor
                        },
                        blockedDirections = listOf(Direction.Down, Direction.Up)
                    )
                    .fillMaxSize()
                    .background(color),
                foodUI = item,
                dynamicColor = color,
                notificationPermission = notificationPermission,
                onCardClick = { onCardClick.invoke(it) },
                onShareButtonClick = { onShareButtonClick.invoke(it) }
            )
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.chicken),
            contentDescription = stringResource(R.string.nobody_but_chickens),
            alignment = Alignment.Center,
            modifier = Modifier
                .size(800.dp, 900.dp)
        )

        Text(
            text = stringResource(R.string.nobody_but_chickens),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.surface,
        )
    }
}

data class BottomMenuItem(
    val label: Int,
    val icon: Int
)

private fun prepareBottomMenu() = listOf(
    BottomMenuItem(label = R.string.home, icon = R.drawable.home_icon),
    BottomMenuItem(label = R.string.topten, icon = R.drawable.topten_icon),
    BottomMenuItem(label = R.string.history, icon = R.drawable.history_icon),
    BottomMenuItem(label = R.string.profile, icon = R.drawable.profile_icon)
)