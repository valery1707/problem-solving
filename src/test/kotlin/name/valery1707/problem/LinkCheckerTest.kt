package name.valery1707.problem

import name.valery1707.problem.LinkChecker.Companion.toURI
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.assertj.core.api.Assumptions.assumeThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.net.Authenticator
import java.net.CookieHandler
import java.net.InetSocketAddress
import java.net.ProxySelector
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpHeaders
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLParameters
import javax.net.ssl.SSLSession
import kotlin.io.path.toPath

typealias ResponseBuilder<T> = (HttpRequest) -> HttpResponse<T>
typealias ResponseMeta = Pair<Int, Map<String, String>>

internal class LinkCheckerTest {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "./path/to/real/project",
        ],
    )
    internal fun checkReal(path: Path) {
        assumeThat(path).isDirectory.isReadable
        val client = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.NEVER)
            .proxy(proxy)
            .build()
        val checker = LinkChecker(path)
        assertThat(checker.findInvalid(client)).isEmpty()
    }

    @Test
    @Suppress("HttpUrlsUsage")
    internal fun testDemo() {
        val path = javaClass.getResource("/linkChecker/Demo.md")?.toURI()?.toPath()?.parent
        assertThat(path).isNotNull.isDirectory.isReadable

        fun ok(): ResponseMeta = 200 to mapOf()
        fun notFound(): ResponseMeta = 404 to mapOf()
        fun redirect(code: Int, target: String): ResponseMeta = code to mapOf("Location" to target)
        fun rateLimitGH(awaitMillis: Long): ResponseMeta = 403 to mapOf("x-ratelimit-reset" to Instant.now().plusMillis(awaitMillis).epochSecond.toString())

        //Check links via: curl --silent -X GET --head 'URL'
        val client = MockedHttpClient.fromMeta(
            mapOf(
                "https://ya.ru" to listOf(
                    redirect(302, "https://ya.ru/"),
                ),
                "https://ya.ru/" to listOf(
                    ok(),
                ),
                "http://schema.org" to listOf(
                    redirect(301, "https://schema.org/"),
                ),
                "https://github.com/androidx/androidx/blob/androidx-main/build.gradle" to listOf(
                    //todo Calculate header value on building response
                    //Will wait some time
                    rateLimitGH(2111),
                    //Will wait zero time
                    rateLimitGH(10),
                    //Will wait default time
                    rateLimitGH(-1500),
                    ok(),
                ),
                "https://github.com/androidx/androidx/blob/androidx-main/buildSrc/public/src/main/kotlin/androidx/build/LibraryGroups.kt" to listOf(
                    notFound(),
                ),
            ),
        )

        val checker = LinkChecker(path!!)

        assertThat(checker.findInvalid(client)).containsExactlyInAnyOrderEntriesOf(
            mapOf(
                "Demo.md:1:25" to "https://ya.ru -> 302 -> https://ya.ru/",
                "Demo.md:3:14" to "http://schema.org -> 301 -> https://schema.org/",
                "Demo.md:5:14" to "https://github.com/androidx/androidx/blob/androidx-main/buildSrc/public/src/main/kotlin/androidx/build/LibraryGroups.kt -> 404",
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

    private class MockedHttpClient(
        private val worker: ResponseBuilder<Any?>,
    ) : HttpClient() {
        override fun cookieHandler(): Optional<CookieHandler> = Optional.empty()
        override fun connectTimeout(): Optional<Duration> = Optional.empty()
        override fun followRedirects(): Redirect = Redirect.NEVER
        override fun proxy(): Optional<ProxySelector> = Optional.empty()
        override fun sslContext(): SSLContext = SSLContext.getDefault()
        override fun sslParameters(): SSLParameters = sslContext().defaultSSLParameters
        override fun authenticator(): Optional<Authenticator> = Optional.empty()
        override fun version(): Version = Version.HTTP_1_1
        override fun executor(): Optional<Executor> = Optional.empty()

        override fun <T : Any?> sendAsync(
            request: HttpRequest,
            responseBodyHandler: HttpResponse.BodyHandler<T>,
            pushPromiseHandler: HttpResponse.PushPromiseHandler<T>?,
        ): CompletableFuture<HttpResponse<T>> = sendAsync(request, responseBodyHandler)

        override fun <T : Any?> sendAsync(
            request: HttpRequest,
            responseBodyHandler: HttpResponse.BodyHandler<T>,
        ): CompletableFuture<HttpResponse<T>> = CompletableFuture.supplyAsync { send(request, responseBodyHandler) }

        @Suppress("UNCHECKED_CAST")
        override fun <T : Any?> send(request: HttpRequest, responseBodyHandler: HttpResponse.BodyHandler<T>): HttpResponse<T> =
            worker(request) as HttpResponse<T>

        companion object {
            fun fromMeta(responses: Map<String, List<ResponseMeta>>): HttpClient = fromBuilders(
                responses.mapValues {
                    it.value
                        .map<ResponseMeta, ResponseBuilder<Any?>> { meta ->
                            { req ->
                                MockedHttpResponse.fromRequest(req, meta.first, meta.second.mapValues { h -> listOf(h.value) })
                            }
                        }
                        .toMutableList()
                },
            )

            fun fromBuilders(responses: Map<String, MutableList<ResponseBuilder<Any?>>>): HttpClient = MockedHttpClient { req ->
                responses[req.uri().toString()]?.removeFirst()?.invoke(req) ?: fail("Unknown response builders for ${req.uri()}")
            }
        }
    }

    private class MockedHttpResponse<T : Any?>(
        private val request: HttpRequest,
        private val statusCode: Int,
        private val headers: HttpHeaders,
    ) : HttpResponse<T> {
        override fun statusCode(): Int = statusCode
        override fun request(): HttpRequest = request
        override fun previousResponse(): Optional<HttpResponse<T>> = Optional.empty()
        override fun headers(): HttpHeaders = headers
        override fun body(): T? = null
        override fun sslSession(): Optional<SSLSession> = Optional.empty()
        override fun uri(): URI = request().uri()
        override fun version(): HttpClient.Version = request().version().orElse(HttpClient.Version.HTTP_1_1)

        companion object {
            fun <T : Any?> fromRequest(request: HttpRequest, statusCode: Int, headers: Map<String, List<String>>): HttpResponse<T> = MockedHttpResponse(
                request, statusCode, HttpHeaders.of(headers) { _, _ -> true },
            )
        }
    }

}
