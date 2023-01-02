package org.example.webserver.utils

fun parseQueryString(path: String): Map<String, List<String>> = buildMap<String, MutableList<String>> {
    path.substring(path.indexOf('?') + 1)
        .split('&')
        .map {
            val (key, value) = it.split('=')
            key to value
        }
        .forEach { (key, value) ->
            val values = get(key) ?: mutableListOf()
            values.add(value)
            put(key, values)
        }
}
