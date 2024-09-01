/*
 * Copyright 2024 Teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.nudge.coroutines

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Exception thrown when a coroutine scope is cancelled due to leaving the composition.
 */
private class ScopeCancellationException : CancellationException(
  "The coroutine scope left the composition"
)

/**
 * Type alias for a provider function that returns a [CoroutineContext].
 *
 * @see rememberCoroutineScope
 */
public typealias CoroutineContextProvider = @DisallowComposableCalls () -> CoroutineContext

/**
 * Type alias for a provider function that returns a [CoroutineContext] for a supervisor job.
 *
 * @see rememberCoroutineScope
 */
public typealias SupervisorContextProvider = @DisallowComposableCalls () -> CoroutineContext

/**
 * Handles the cancellation of a [CoroutineScope] when the scope is forgotten or abandoned.
 *
 * @param scope The [CoroutineScope] to be cancelled.
 */
@PublishedApi
internal class CompositionScopeCanceller(
  val scope: CoroutineScope
) : RememberObserver {
  override fun onRemembered() {
    // No action needed when remembered
  }

  override fun onForgotten() {
    scope.cancel(ScopeCancellationException())
  }

  override fun onAbandoned() {
    scope.cancel(ScopeCancellationException())
  }
}

/**
 * Creates a [CoroutineScope] with the given [CoroutineContext] and optional [SupervisorJob].
 *
 * @param context The [CoroutineContext] to be used for the new [CoroutineScope].
 * @param supervisorContext Optional [CoroutineContext] to be used for a [SupervisorJob].
 * @param composer The [Composer] instance used for applying the coroutine context.
 * @return A [CoroutineScope] with the combined context.
 */
@PublishedApi
@OptIn(InternalComposeApi::class)
internal fun createCompositionCoroutineScope(
  context: CoroutineContext,
  supervisorContext: CoroutineContext?,
  composer: Composer,
): CoroutineScope {
  val combinedContext = when {
    context[Job] != null -> {
      // If a parent Job is already present, create a new Job that fails
      // to prevent adding an additional Job to an existing context.
      Job().apply {
        completeExceptionally(
          IllegalArgumentException(
            "CoroutineContext supplied to rememberCoroutineScope may not include a parent job."
          )
        )
      }
    }

    else -> {
      // Otherwise, create a new Job and add it to the context
      val applyContext = composer.applyCoroutineContext
      applyContext + Job(applyContext[Job]) + context
    }
  }

  // Add SupervisorJob if supervisorContext is not null
  val finalContext = if (supervisorContext != null) {
    combinedContext + supervisorContext
  } else {
    combinedContext
  }

  return CoroutineScope(finalContext)
}

/**
 * Provides a [CoroutineScope] that is remembered across recompositions.
 *
 * @param contextProvider A function that provides the [CoroutineContext] to be used for the scope.
 * @param supervised Whether to add a [SupervisorJob] to the coroutine context.
 * @return A [CoroutineScope] that is remembered and will be cancelled when the composition leaves.
 */
@Composable
public inline fun rememberCoroutineScope(
  crossinline contextProvider: CoroutineContextProvider = { EmptyCoroutineContext },
  supervised: Boolean = false,
): CoroutineScope {
  val composer = currentComposer
  val scope = remember {
    createCompositionCoroutineScope(
      context = contextProvider(),
      supervisorContext = if (supervised) SupervisorJob() else null,
      composer = composer,
    )
  }
  val canceller = remember { CompositionScopeCanceller(scope) }
  return canceller.scope
}

/**
 * Provides a [CoroutineScope] that is remembered across recompositions with a supervisor context provider.
 *
 * @param contextProvider A function that provides the [CoroutineContext] to be used for the scope.
 * @param supervisorContextProvider A function that provides the [CoroutineContext] for a [SupervisorJob].
 * @return A [CoroutineScope] that is remembered and will be cancelled when the composition leaves.
 */
@Composable
public inline fun rememberCoroutineScope(
  crossinline contextProvider: CoroutineContextProvider = { EmptyCoroutineContext },
  crossinline supervisorContextProvider: SupervisorContextProvider = { SupervisorJob() }
): CoroutineScope {
  val composer = currentComposer
  val scope = remember {
    createCompositionCoroutineScope(
      context = contextProvider(),
      supervisorContext = supervisorContextProvider(),
      composer = composer,
    )
  }
  val canceller = remember { CompositionScopeCanceller(scope) }
  return canceller.scope
}

/**
 * Creates a [CoroutineScope] with the given [CoroutineContext] and optional [SupervisorJob].
 *
 * @param context The [CoroutineContext] to be used for the new [CoroutineScope].
 * @param supervisorContext Optional [CoroutineContext] to be used for a [SupervisorJob].
 * @return A [CoroutineScope] with the combined context.
 */
@PublishedApi
internal fun createCompositionCoroutineScope(
  context: CoroutineContext,
  supervisorContext: CoroutineContext?,
): CoroutineScope {
  val combinedContext = when {
    context[Job] != null -> {
      // If a parent Job is already present, create a new Job that fails
      // to prevent adding an additional Job to an existing context.
      Job().apply {
        completeExceptionally(
          IllegalArgumentException(
            "CoroutineContext supplied to rememberCoroutineScope may not include a parent job."
          )
        )
      }
    }

    else -> {
      // Otherwise, use the provided context
      context
    }
  }

  // Add SupervisorJob if supervisorContext is not null
  val finalContext = if (supervisorContext != null) {
    combinedContext + supervisorContext
  } else {
    combinedContext
  }

  return CoroutineScope(finalContext)
}

/**
 * Creates a [CoroutineScope] with the given [CoroutineContext] and optional [SupervisorJob].
 *
 * @param contextProvider A function that provides the [CoroutineContext] to be used for the scope.
 * @param supervised Whether to add a [SupervisorJob] to the coroutine context.
 * @return A [CoroutineScope] with the combined context.
 */
public inline fun createScopeWithContext(
  crossinline contextProvider: CoroutineContextProvider = { EmptyCoroutineContext },
  supervised: Boolean = false,
): CoroutineScope {
  val scope = createCompositionCoroutineScope(
    context = contextProvider(),
    supervisorContext = if (supervised) SupervisorJob() else null,
  )
  val canceller = CompositionScopeCanceller(scope)
  return canceller.scope
}
