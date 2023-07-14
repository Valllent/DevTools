package com.valllent.devtools.data

enum class ProxyType(
    val description: String,
    val proxyPort: String,
) {

    NO_PROXY(
        "No proxy",
        ":0"
    ),
    MITMPROXY(
        "Mitmproxy",
        ":8080"
    ),
    CHARLES(
        "Charles",
        ":8888"
    );

}