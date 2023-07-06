package com.mentormate.foodwars.domain.vo.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<in P> {

    operator fun invoke(
        scope: CoroutineScope,
        params: P,
        callback: (Unit) -> Unit
    ) {
        val backgroundJob = scope.async { execute(params) }
        scope.launch { callback.invoke(backgroundJob.await()) }
    }

    suspend fun invoke(params: P) = execute(params)

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P)
}
