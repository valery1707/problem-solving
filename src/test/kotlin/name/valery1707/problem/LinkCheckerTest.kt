package name.valery1707.problem

import name.valery1707.problem.LinkChecker.Companion.HTTP_DATE_FORMAT
import name.valery1707.problem.LinkChecker.Companion.toURI
import okhttp3.Headers.Companion.toHeaders
import okhttp3.OkHttpClient
import okhttp3.mock.MockInterceptor
import okhttp3.mock.body
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.assertj.core.api.Assumptions.assumeThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.net.InetSocketAddress
import java.net.ProxySelector
import java.nio.file.Path
import java.time.Instant
import java.time.ZoneId
import java.util.*
import kotlin.io.path.toPath

typealias ResponseMeta = () -> Pair<Int, Map<String, String>>

internal class LinkCheckerTest {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "./path/to/real/project",
        ],
    )
    internal fun checkReal(path: Path) {
        assumeThat(path).isDirectory.isReadable
        val client = OkHttpClient.Builder()
            .followRedirects(false).followSslRedirects(false)
            .proxySelector(proxy)
            .build()
        val checker = LinkChecker(path)
        assertThat(checker.findInvalid(client)).isEmpty()
    }

    @Test
    @Suppress("HttpUrlsUsage")
    internal fun testDemo() {
        val path = javaClass.getResource("/linkChecker/Demo.md")?.toURI()?.toPath()?.parent
        assertThat(path).isNotNull.isDirectory.isReadable

        fun ok(): ResponseMeta = { 200 to mapOf() }
        fun notFound(): ResponseMeta = { 404 to mapOf() }
        fun redirect(code: Int, target: String): ResponseMeta = { code to mapOf("Location" to target) }
        fun rateLimitGH(awaitMillis: Long): ResponseMeta = { 403 to mapOf("x-ratelimit-reset" to Instant.now().plusMillis(awaitMillis).epochSecond.toString()) }
        fun rateLimitSpecSec(awaitSec: Int): ResponseMeta = { 429 to mapOf("Retry-After" to awaitSec.toString()) }
        fun rateLimitSpecDate(awaitMillis: Long): ResponseMeta = {
            429 to mapOf("Retry-After" to HTTP_DATE_FORMAT.format(Instant.now().plusMillis(awaitMillis).atZone(ZoneId.systemDefault())))
        }

        //Check links via: curl --silent -X GET --head 'URL'
        val client = mockHttpClient(
            mapOf(
                "https://habr.com/ru/company/otus/blog/707724/comments" to mutableListOf(
                    redirect(302, "https://habr.com/ru/company/otus/blog/707724/comments/"),
                ),
                "https://habr.com/ru/company/otus/blog/707724/comments/" to mutableListOf(
                    ok(),
                ),
                "http://schema.org/" to mutableListOf(
                    redirect(301, "https://schema.org/"),
                ),
                "https://github.com/androidx/androidx/blob/androidx-main/build.gradle" to mutableListOf(
                    //Will wait some time
                    rateLimitGH(2111),
                    //Will wait zero time
                    rateLimitGH(10),
                    //Will wait default time
                    rateLimitGH(-1500),
                    ok(),
                ),
                "https://www.bearer.com/" to mutableListOf(
                    // Use variant with "delay-seconds"
                    rateLimitSpecSec(1),
                    // Use variant with "http-date"
                    rateLimitSpecDate(100),
                    ok(),
                ),
                "https://github.com/androidx/androidx/blob/androidx-main/buildSrc/public/src/main/kotlin/androidx/build/LibraryGroups.kt" to mutableListOf(
                    notFound(),
                ),
            ),
        )

        val checker = LinkChecker(path!!)

        assertThat(checker.findInvalid(client)).containsExactlyInAnyOrderEntriesOf(
            mapOf(
                "Demo.md:1:25" to "https://habr.com/ru/company/otus/blog/707724/comments -> 302 -> https://habr.com/ru/company/otus/blog/707724/comments/",
                "Demo.md:3:14" to "http://schema.org -> 301 -> https://schema.org/",
                "Demo.md:7:14" to "https://github.com/androidx/androidx/blob/androidx-main/buildSrc/public/src/main/kotlin/androidx/build/LibraryGroups.kt -> 404",
            ),
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "some invalid uri",
        ],
    )
    internal fun testInvalidUriString(uriString: String) {
        assertThat(uriString.toURI()).isNull()
    }

    private val proxy: ProxySelector by lazy {
        sequenceOf(
            "genproxy" to 8080,
        )
            .map { InetSocketAddress(it.first, it.second) }
            .filterNot { it.isUnresolved }
            .map { ProxySelector.of(it) }
            .firstOrNull()
            ?: ProxySelector.getDefault()
    }

    private companion object {
        fun mockHttpClient(responses: Map<String, MutableList<ResponseMeta>>): OkHttpClient {
            val interceptor = MockInterceptor()

            interceptor.addRule()
                .anyTimes()
                .answer { req ->
                    val uri = req.url.toUri()
                    val meta = ((responses[uri.toString()] ?: fail("Unknown URI: $uri")).removeFirstOrNull() ?: fail("Too many requests for URI: $uri"))()
                    okhttp3.Response.Builder()
                        .code(meta.first)
                        .headers(meta.second.toHeaders())
                        .body("")
                }

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        }
    }

}
