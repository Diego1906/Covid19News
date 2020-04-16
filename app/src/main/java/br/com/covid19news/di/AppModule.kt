package br.com.covid19news.di

import br.com.covid19news.remote.IService
import br.com.covid19news.remote.Service
import br.com.covid19news.repository.IRepository
import br.com.covid19news.repository.Repository
import br.com.covid19news.viewmodel.ViewModelAllCountries
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    viewModel { ViewModelAllCountries(repository = get(), application = androidApplication()) }
    single<IRepository> { Repository(service = get()) }
    single<IService> { Service() }
}
