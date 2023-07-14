package com.valllent.devtools.data

enum class ProxyType(
    val description: String,
    val proxyUrl: String,
) {

    NO_PROXY(
        "No proxy",
        ":0"
    ),
    MITMPROXY(
        "Mitmproxy",
        "192.168.8.2:8080"
    ),
    CHARLES(
        "Charles",
        "192.168.8.2:8888"
    );

}