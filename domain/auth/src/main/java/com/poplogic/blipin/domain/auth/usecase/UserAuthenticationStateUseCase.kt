package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.domain.auth.model.UserAuthenticationState
import com.poplogic.blipin.domain.common.FlowBasedUseCase

interface UserAuthenticationStateUseCase : FlowBasedUseCase<Unit, UserAuthenticationState>
