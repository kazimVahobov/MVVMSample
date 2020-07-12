package com.example.mvvmsample

import android.app.Application
import com.example.mvvmsample.data.db.AppDB
import com.example.mvvmsample.data.network.API
import com.example.mvvmsample.data.network.NetworkConnectionInterceptor
import com.example.mvvmsample.data.repositories.UserRepository
import com.example.mvvmsample.ui.auth.AuthViewModelFactory
import com.example.mvvmsample.ui.home.profile.ProfileViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApp))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { API(instance()) }
        bind() from singleton { AppDB(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
    }
}