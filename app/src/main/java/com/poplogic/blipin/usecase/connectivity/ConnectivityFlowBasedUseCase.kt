package com.poplogic.blipin.usecase.connectivity

import com.poplogic.blipin.usecase.FlowBasedUseCase
import com.poplogic.blipin.usecase.connectivity.domain.ConnectivityState

interface ConnectivityFlowBasedUseCase : FlowBasedUseCase<Unit, ConnectivityState>
