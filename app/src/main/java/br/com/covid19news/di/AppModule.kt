package br.com.covid19news.di

import br.com.covid19news.data.AppDatabase
import br.com.covid19news.remote.IService
import br.com.covid19news.remote.Service
import br.com.covid19news.repository.IRepository
import br.com.covid19news.repository.Repository
import br.com.covid19news.viewmodel.GenericViewModel
import br.com.covid19news.viewmodel.StartViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { StartViewModel() }
    viewModel { GenericViewModel(repository = get(), application = androidApplication()) }
    single<IRepository> {
        Repository(
            service = get(),
            database = AppDatabase.getInstance(androidApplication().applicationContext)
        )
    }
    single<IService> { Service() }
}
