package com.valllent.devtools.di

import android.content.ContentResolver
import android.content.Context
import android.net.wifi.WifiManager
import com.valllent.devtools.managers.NetworkManager
import com.valllent.devtools.managers.PermissionRequester
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    fun provideWifiManager(@ApplicationContext context: Context): WifiManager {
        return context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    @Provides
    fun provideNetworkManager(
        contentResolver: ContentResolver,
        wifiManager: WifiManager,
    ): NetworkManager {
        return NetworkManager(contentResolver, wifiManager)
    }

}