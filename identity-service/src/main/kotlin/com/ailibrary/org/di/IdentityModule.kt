package com.ailibrary.org.di

import com.ailibrary.org.contract.UserContract
import com.ailibrary.org.repository.UserRepository
import com.ailibrary.org.service.IdentityService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val applyIdentityModule = module {
    singleOf(::UserRepository) { bind<UserContract>() }
    singleOf(::IdentityService)
}