package com.zagirlek.nytimes

import android.app.Application
import com.zagirlek.nytimes.di.CoreDi
import com.zagirlek.nytimes.di.DataDi
import com.zagirlek.nytimes.di.DomainDi
import com.zagirlek.nytimes.di.FeatureModule

class NyTimesApp: Application() {
    private val coreDi: CoreDi by lazy {
        CoreDi(this)
    }
    private val dataDi: DataDi by lazy {
        DataDi(coreDi)
    }
    private val domainDi: DomainDi by lazy {
        DomainDi(dataDi)
    }
    val featureModule: FeatureModule by lazy {
        FeatureModule(
            storeFactory = coreDi.storeFactory,
            authDomainModule = domainDi.authDomainModule,
            weatherDomainModule = domainDi.weatherDomainModule,
            newsDomainModule = domainDi.newsDomainModule
        )
    }
}