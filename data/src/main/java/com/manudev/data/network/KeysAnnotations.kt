package com.manudev.data.network

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class PrivateKey

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class PublicKey
