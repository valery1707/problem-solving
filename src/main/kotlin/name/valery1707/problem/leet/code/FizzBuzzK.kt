package name.valery1707.problem.leet.code

/**
 * # 412. Fizz Buzz
 *
 * Given an integer `n`, return a string array answer (**1-indexed**) where:
 * * `answer[i] == "FizzBuzz"` if `i` is divisible by `3` and `5`.
 * * `answer[i] == "Fizz"` if `i` is divisible by `3`.
 * * `answer[i] == "Buzz"` if `i` is divisible by `5`.
 * * `answer[i] == i` (as a string) if none of the above conditions are true.
 *
 * ### Constraints
 * * `1 <= n <= 10^4`
 *
 * <a href="https://leetcode.com/problems/fizz-buzz/">412. Fizz Buzz</a>
 */
interface FizzBuzzK {
    fun fizzBuzz(n: Int): List<String>

    @Suppress("EnumEntryName", "unused")
    enum class Implementation : FizzBuzzK {
        pure {
            override fun fizzBuzz(n: Int): List<String> {
                return (1..n).map {
                    val div3 = it % 3 == 0
                    val div5 = it % 5 == 0
                    if (div3 && div5) "FizzBuzz"
                    else if (div3) "Fizz"
                    else if (div5) "Buzz"
                    else it.toString()
                }
            }
        },
        mutable {
            override fun fizzBuzz(n: Int): List<String> {
                val res = ArrayList<String>(n)
                for (it in 1..n) {
                    val div3 = it % 3 == 0
                    val div5 = it % 5 == 0
                    res.add(
                        if (div3 && div5) "FizzBuzz"
                        else if (div3) "Fizz"
                        else if (div5) "Buzz"
                        else it.toString()
                    )
                }
                return res
            }
        },
    }
}
