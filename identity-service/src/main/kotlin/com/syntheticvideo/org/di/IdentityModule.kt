package com.syntheticvideo.org.di


import com.syntheticvideo.org.contract.UserContract
import com.syntheticvideo.org.repository.UserRepository
import com.syntheticvideo.org.service.IdentityService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val applyIdentityModule = module {
    singleOf(::UserRepository) { bind<UserContract>() }
    singleOf(::IdentityService)
}
