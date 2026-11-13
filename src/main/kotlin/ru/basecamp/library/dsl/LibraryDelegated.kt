package ru.basecamp.library.dsl

import ru.basecamp.model.LoanPolicy
import kotlin.properties.Delegates

class LibraryDelegated(val name: String) {
    var policy: LoanPolicy by Delegates.observable(
        LoanPolicy( 3)
    ) { _, old, new ->
        println("[Смена политики библитеки $name]: $old -> $new")
    }
}