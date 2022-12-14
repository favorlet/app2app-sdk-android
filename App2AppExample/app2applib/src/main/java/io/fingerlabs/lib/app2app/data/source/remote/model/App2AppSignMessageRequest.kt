package io.fingerlabs.lib.app2app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App2AppSignMessageRequest(
    @SerialName("action")
    val action: String,

    @SerialName("chainId")
    val chainId: Int,

    @SerialName("blockChainApp")
    val blockChainApp: App2AppBlockChainApp,

    @SerialName("signMessage")
    val signMessage: App2AppSignMessage,
)
