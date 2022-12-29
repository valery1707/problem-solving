package name.valery1707.problem

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoField.NANO_OF_SECOND
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.PathWalkOption
import kotlin.io.path.readText
import kotlin.io.path.walk

/**
 * todo Add description
 * todo Make async (probably with coroutines)
 */
class LinkChecker(private val root: Path) {
    /**
     * Сканируем все файлы из директории, ищем в тексте ссылки, проверяем их на доступность
     */
    @OptIn(ExperimentalPathApi::class)
    fun findInvalid(client: HttpClient): Map<String, String> {
        val filePos2uriCheck = root
            .walk(PathWalkOption.FOLLOW_LINKS)
            .map { root.relativize(it) }
            .map {
                it to loadFile(root.resolve(it))
            }
            .flatMap { pathWithText ->
                pathWithText.second.findUri()
                    .map { (pathWithText.first to it.first) to it.second }
            }
            .take(20)// todo Remove limit
            .map {
                it.first to (it.second to it.second.check(client))
            }
            .filter { it.second.second.first != 200 }
            .toList()
        // todo remove
        println("filePos2uriCheck = $filePos2uriCheck")
        return filePos2uriCheck
            .associateBy(
                { "${it.first.first}:${it.first.second}" },
                {
                    when (it.second.second.first) {
                        in HTTP_REDIRECT -> "${it.second.first} -> ${it.second.second.first} -> ${it.second.second.second}"
                        -1 -> "${it.second.first} -> ${it.second.second.first} -> ${it.second.second.second.query}"
                        else -> "${it.second.first} -> ${it.second.second.first}"
                    }
                },
            )
    }

    private fun loadFile(path: Path): String {
        return path.readText()
    }

    companion object {
        /**
         * https://stackoverflow.com/a/45690571
         */
        private val URI_PATTERN_FULL = ("" +
            "(?<scheme>[a-z][a-z0-9+.-]+):" +
            "(?<authority>\\/\\/(?<user>[^@]+@)?(?<host>[a-z0-9.\\-_~]+)(?<port>:\\d+)?)?" +
            "(?<path>(?:[a-z0-9-._~]|%[a-f0-9]|[!\$&'()*+,;=:@])+(?:\\/(?:[a-z0-9-._~]|%[a-f0-9]|[!\$&'()*+,;=:@])*)*|(?:\\/(?:[a-z0-9-._~]|%[a-f0-9]|[!\$&'()*+,;=:@])+)*)?" +
            "(?<query>\\?(?:[a-z0-9-._~]|%[a-f0-9]|[!\$&'()*+,;=:@]|[/?])+)?" +
            "(?<fragment>\\#(?:[a-z0-9-._~]|%[a-f0-9]|[!\$&'()*+,;=:@]|[/?])+)?" +
            "").toRegex(RegexOption.IGNORE_CASE)

        private val URI_PATTERN_SIMPLE = URI_PATTERN_FULL.pattern
            .replace("()", "")
            .replace("?:", "")
            .replace("+)*)?(?<query>", "*)*)?(?<query>")
            .replace("(?<user>[^@]+@)", "(?<user>[\\w]+@)")
            .toRegex(RegexOption.IGNORE_CASE)

        private fun MatchResult.position(text: String): String {
            val prefix = text.subSequence(0, range.last)
            val col = range.first - prefix.indexOfLast { it == '\n' }
            val line = 1 + prefix.count { it == '\n' }
            return "$line:$col"
        }

        private fun String.findUri() = URI_PATTERN_SIMPLE
            .findAll(this)
            .filter { it.value.startsWith("http") }
            .map { uri ->
                (uri.position(this)) to (uri.value.trimEnd('.').toURI())
            }
            .filter { it.second != null }
            .map { it.first to it.second!! }
            .filter { it.second.scheme in setOf("http", "https") }

        internal fun String.toURI(): URI? = try {
            URI.create(this)
        } catch (e: IllegalArgumentException) {
            null
        }

        private fun URI.check(client: HttpClient): Pair<Int, URI> {
            val request = HttpRequest.newBuilder(this).GET().build()
            // todo Cache
            return try {
                // todo Logging
                println("Check: $this")
                val response = client.send(request, HttpResponse.BodyHandlers.discarding())
                when (response.statusCode()) {
                    //Redirects: extract new location
                    in HTTP_REDIRECT -> response.statusCode() to response.headers().firstValue("Location")!!.get().toURI()!!

                    //Rate limiting: wait and retry
                    in HTTP_RATE_LIMIT -> {
                        val now = Instant.now()
                        val await = response.headers()

                            // todo Extract to method
                            // https://docs.github.com/en/rest/overview/resources-in-the-rest-api?apiVersion=2022-11-28#checking-your-rate-limit-status
                            .map()["x-ratelimit-reset"]
                            ?.asSequence()
                            ?.map(String::toLong)?.map(Instant::ofEpochSecond)
                            ?.map { Duration.between(now.with(NANO_OF_SECOND, 0), it) }
                            ?.map(Duration::toMillis)
                            ?.filter { it >= 0 }
                            ?.firstOrNull()

                            ?: 500

                        // todo Logging
                        println("Await: $await ms")
                        Thread.sleep(await)
                        check(client)
                    }

                    else -> response.statusCode() to response.uri()
                }
            } catch (e: Exception) {
                // todo Logging
                -1 to URI.create("http://host?message=${e.message?.replace(" ", "%20")}")
            }
        }

        private val HTTP_REDIRECT = setOf(301, 302, 307, 308)
        private val HTTP_RATE_LIMIT = setOf(403)
    }
}
