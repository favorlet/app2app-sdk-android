package io.fingerlabs.ex.app2app

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.fingerlabs.ex.app2app.common.eventwrapper.EventObserver
import io.fingerlabs.ex.app2app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel = MainViewModel()
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(mainViewModel) {
            app2AppRequestId.observe(this@MainActivity) {
                goToFavorletApp2App(it.peekContent())
            }
            connectedAddress.observe(this@MainActivity) {
                binding.textConnectedAddress.text = it
                binding.editParams.setText("[\"$it\",\"0x1707Cc19778A773c45C1EA03f62482481d3c0fBD\",\"10\"]")
            }
            signatureHash.observe(this@MainActivity) {
                binding.textSignature.text = it
            }
            resultSendCoin.observe(this@MainActivity, EventObserver{
                binding.textSendCoinResult.text = it
            })
            resultExecuteContract.observe(this@MainActivity, EventObserver{
                binding.textExecuteContractResult.text = it
            })

            progress.observe(this@MainActivity, EventObserver {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            })
        }

        with(binding) {
            btnConnectWallet.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                mainViewModel.requestConnectWallet(chainId)
            }
            btnSignMessage.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val message = editMessage.text.toString()
                mainViewModel.requestSignMessage(chainId, message)
            }
            btnSendCoin.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val toAddress = editTo.text.toString()
                val amount = editCoinAmount.text.toString()
                mainViewModel.requestSendCoin(chainId, toAddress, amount)
            }
            editABI.setText("[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"safeTransferFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{}]")
            btnExecuteContract.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val contractAddress = editContractAddress.text.toString()
                val abi = editABI.text.toString()
                val params = editParams.text.toString()
                val value = editAmount.text.toString()
                val functionName = editFunctionName.text.toString()
                mainViewModel.requestExecuteContract(chainId, contractAddress, abi, params, value, functionName)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        mainViewModel.receipt()
    }


    fun goToFavorletApp2App(requestId: String) {
        Log.i(">>>", ">>> requestID: $requestId")
        mainViewModel.execute(this, requestId)
    }



}