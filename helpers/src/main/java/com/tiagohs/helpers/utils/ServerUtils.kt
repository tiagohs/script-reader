package com.tiagohs.helpers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class ServerUtils {

    companion object {

        @Suppress("DEPRECATION")
        fun isNetworkConnected(context: Context?): Boolean {
            try {
                var result = false
                val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val networkCapabilities = connectivityManager.activeNetwork ?: return false
                    val actNw =
                        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                    result = when {
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                } else {
                    connectivityManager.run {
                        connectivityManager.activeNetworkInfo?.run {
                            result = when (type) {
                                ConnectivityManager.TYPE_WIFI -> true
                                ConnectivityManager.TYPE_MOBILE -> true
                                ConnectivityManager.TYPE_ETHERNET -> true
                                else -> false
                            }

                        }
                    }
                }

                return result
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }

        }

        @Suppress("DEPRECATION")
        fun isWifiConnected(context: Context?): Boolean {
            val connManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val network = connManager.allNetworks
                if (!network.isNullOrEmpty()) {
                    for (i in network.indices) {
                        val networkInfo = connManager.getNetworkInfo(network[i])
                        val networkType = networkInfo?.type

                        if (ConnectivityManager.TYPE_WIFI == networkType)
                            return true
                    }
                }
            } else {
                return connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnected ?: false
            }

            return false
        }

    }
}