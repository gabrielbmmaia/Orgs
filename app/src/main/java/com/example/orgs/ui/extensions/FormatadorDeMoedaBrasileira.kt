package com.example.orgs.ui.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun BigDecimal.formataParaMoedaReal() : String? {
    val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
    return formatador.format(this)
}