package name.valery1707.problem

/**
 * @see StringConcatenationJ
 */
interface StringConcatenationK {
    fun accept(lhs: String, rhs: String): String

    @Suppress("EnumEntryName", "unused")
    enum class Implementation : StringConcatenationK {
        plus {
            override fun accept(lhs: String, rhs: String) = lhs + "_" + rhs
        },
        join {
            override fun accept(lhs: String, rhs: String): String = java.lang.String.join("_", lhs, rhs)
        },
        interpolate {
            override fun accept(lhs: String, rhs: String) = "${lhs}_${rhs}"
        },
    }
}
