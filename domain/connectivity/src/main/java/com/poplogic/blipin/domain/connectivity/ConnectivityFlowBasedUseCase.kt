package com.poplogic.blipin.domain.connectivity

import com.poplogic.blipin.domain.common.FlowBasedUseCase
import com.poplogic.blipin.domain.connectivity.model.ConnectivityState

interface ConnectivityFlowBasedUseCase : FlowBasedUseCase<Unit, ConnectivityState>
