package com.eugens.githubsearch.presentation.di.modules

import com.eugens.githubsearch.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { AuthUseCase(get(), get()) }
    factory { SearchUseCase(get(), get()) }
    factory { IsAuthorizedUseCase(get(), get()) }
    factory { GetHistoryUseCase(get(), get()) }
    factory { MarkAsViewedUseCase(get(), get()) }
    factory { SwapItemsUseCase(get(), get()) }
    factory { DeleteUseCase(get(), get()) }
}
