package com.mentormate.foodwars.utils

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

@ExperimentalSwipeableCardApi
fun Modifier.swappableCard(
    state: SwipeableCardState,
    onSwiped: (Direction) -> Unit,
    onDrag: (Direction) -> Unit,
    onDragEnd: () -> Unit,
    onSwipeCancel: () -> Unit = {},
    blockedDirections: List<Direction> = listOf(Direction.Up, Direction.Down),
) = pointerInput(Unit) {
    coroutineScope {
        detectDragGestures(
            onDragCancel = {
                launch {
                    state.reset()
                    onSwipeCancel()
                }
            },
            onDrag = { change, dragAmount ->
                launch {

                    if (abs(state.offset.targetValue.y) < abs(state.offset.targetValue.x)) {
                        if (state.offset.targetValue.x > 0) {
                            onDrag.invoke(Direction.Right)
                        } else {
                            onDrag.invoke(Direction.Left)
                        }
                    }

                    val original = state.offset.targetValue
                    val summed = original + dragAmount
                    val newValue = Offset(
                        x = summed.x.coerceIn(-state.maxWidth, state.maxWidth),
                        y = summed.y.coerceIn(-state.maxHeight, state.maxHeight)
                    )
                    if (change.positionChange() != Offset.Zero) change.consume()
                    state.drag(newValue.x, newValue.y)
                }
            },
            onDragEnd = {
                launch {
                    val coercedOffset = state.offset.targetValue
                        .coerceIn(
                            blockedDirections,
                            maxHeight = state.maxHeight,
                            maxWidth = state.maxWidth
                        )

                    if (hasNotTravelledEnough(state, coercedOffset)) {
                        state.reset()
                        onSwipeCancel()
                        onDragEnd.invoke()
                    } else {
                        val horizontalTravel = abs(state.offset.targetValue.x)
                        val verticalTravel = abs(state.offset.targetValue.y)

                        if (horizontalTravel > verticalTravel) {
                            if (state.offset.targetValue.x > 0) {
                                state.swipe(Direction.Right)
                                onSwiped(Direction.Right)
                                onDragEnd.invoke()
                            } else {
                                state.swipe(Direction.Left)
                                onSwiped(Direction.Left)
                                onDragEnd.invoke()
                            }
                        } else {
                            if (state.offset.targetValue.y < 0) {
                                state.swipe(Direction.Up)
                                onSwiped(Direction.Up)
                            } else {
                                state.swipe(Direction.Down)
                                onSwiped(Direction.Down)
                            }
                        }
                    }
                }
            }
        )
    }
}.graphicsLayer {
    translationX = state.offset.value.x
    translationY = state.offset.value.y
    rotationZ = (state.offset.value.x / 60).coerceIn(-40f, 40f)
}

private fun Offset.coerceIn(
    blockedDirections: List<Direction>,
    maxHeight: Float,
    maxWidth: Float,
): Offset =
    copy(
        x = x.coerceIn(
            if (blockedDirections.contains(Direction.Left)) {
                0f
            } else {
                -maxWidth
            },
            if (blockedDirections.contains(Direction.Right)) {
                0f
            } else {
                maxWidth
            }
        ),
        y = y.coerceIn(
            if (blockedDirections.contains(Direction.Up)) {
                0f
            } else {
                -maxHeight
            },
            if (blockedDirections.contains(Direction.Down)) {
                0f
            } else {
                maxHeight
            }
        )
    )

private fun hasNotTravelledEnough(
    state: SwipeableCardState,
    offset: Offset,
): Boolean =
    abs(offset.x) < state.maxWidth / 4 &&
            abs(offset.y) < state.maxHeight / 4