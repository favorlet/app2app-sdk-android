package io.fingerlabs.lib.app2app.domain.usecase

import io.fingerlabs.lib.app2app.data.repository.App2AppRepository
import io.fingerlabs.lib.app2app.data.source.remote.model.App2AppConnectWalletRequest
import io.fingerlabs.lib.app2app.data.source.remote.model.App2AppRequestResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class RequestConnectWalletUseCase constructor(
    private val app2AppRepository: App2AppRepository = App2AppRepository()
) {

    suspend operator fun invoke(
        request: App2AppConnectWalletRequest
    ): App2AppRequestResponse = withContext(Dispatchers.IO) {
        app2AppRepository.requestConnectWallet(request)
    }

}