package com.mentormate.foodwars.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.InterestText

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FoodDetailsScreen(viewModel: FoodDetailsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    var ratingVisibility by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.light_brown),
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                GlideImage(
                    alignment = Alignment.Center,
                    model = uiState.imageResource,
                    contentDescription = uiState.foodName
                ) {
                    it.load(uiState.imageResource)
                        .error(R.drawable.error_icon)
                        .fitCenter()
                        .placeholder(R.drawable.refresh_icon)
                        .override(500, 500)
                }

                Image(
                    painterResource(R.drawable.award),
                    contentDescription = stringResource(R.string.award),
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .fillMaxHeight(0.2f)
                        .clickable {
                            ratingVisibility = true
                        }
                )
            }

            Text(
                text = uiState.foodName,
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            InterestText.values().find {
                it.number == uiState.type
            }?.let { interest ->
                Text(
                    text = stringResource(id = interest.stringResource),
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier.padding(5.dp)
            ) {
                uiState.characteristics.forEach {
                    TextChip(
                        it.characteristic,
                        Color.Yellow
                    )
                }
            }
            val scrollState = rememberScrollState()
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .background(Color.Black)
                    .verticalScroll(scrollState, reverseScrolling = true),
                contentAlignment = Alignment.Center
            ) {
                uiState.listRelatedItems.forEach { relatedItem ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        GlideImage(
                            alignment = Alignment.TopStart,
                            model = relatedItem.imageUrl,
                            contentDescription = relatedItem.name
                        ) {
                            it.load(relatedItem.imageUrl)
                                .error(R.drawable.error_icon)
                                .placeholder(R.drawable.refresh_icon)
                                .override(600, 500)
                        }

                        Text(
                            text = relatedItem.name,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            RatingText(
                uiState.rating.number,
                ratingVisibility
            )

        }
    }
}

@Composable
fun TextChip(
    text: String,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 2.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(30.dp)
            )
            .background(
                color = color,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(4.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RatingText(rating: Int, isVisible: Boolean) {
    if (isVisible) {
        Text(
            text = stringResource(id = R.string.rating_text, rating),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}