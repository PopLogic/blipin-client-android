import android.os.SystemClock
import com.poplogic.blipin.common.base.ratelimit.RateLimitStatus
import com.poplogic.blipin.common.base.ratelimit.RateLimiter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.max
import kotlin.time.Duration.Companion.milliseconds

class RateLimiterImpl : RateLimiter {
    companion object {
        private const val COOLDOWN_MS = 3 * 60 * 1000L // 3 minutes
    }

    private val lastTriggerTime = AtomicLong(-COOLDOWN_MS)

    // Dedicated scope for running the internal countdown loop
    private val scope = CoroutineScope(Dispatchers.Default)
    private var tickerJob: Job? = null

    private val _statusFlow = MutableStateFlow<RateLimitStatus>(RateLimitStatus.Available)
    override val statusFlow: Flow<RateLimitStatus> = _statusFlow.asStateFlow()

    override fun currentStatus(): RateLimitStatus {
        val remaining = getRemainingCooldownMs()
        return if (remaining > 0) {
            RateLimitStatus.Locked(remaining.milliseconds)
        } else {
            RateLimitStatus.Available
        }
    }

    override fun tryTrigger(): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        while (true) {
            val lastTime = lastTriggerTime.get()
            if (currentTime < lastTime + COOLDOWN_MS) {
                return false
            }
            if (lastTriggerTime.compareAndSet(lastTime, currentTime)) {
                // 1. Instantly update the state to avoid any UI delay
                _statusFlow.value = RateLimitStatus.Locked(COOLDOWN_MS.milliseconds)
                // 2. Start the internal ticker loop
                startTicker()
                return true
            }
        }
    }

    private fun startTicker() {
        synchronized(this) {
            // Cancel any existing ticker to prevent overlapping loops
            tickerJob?.cancel()

            tickerJob =
                scope.launch {
                    while (true) {
                        val remaining = getRemainingCooldownMs()
                        if (remaining > 0) {
                            _statusFlow.value = RateLimitStatus.Locked(remaining.milliseconds)
                            delay(1000.milliseconds) // Tick down every second
                        } else {
                            _statusFlow.value = RateLimitStatus.Available
                            break // Stop the coroutine completely when cooldown finishes
                        }
                    }
                }
        }
    }

    private fun getRemainingCooldownMs(): Long {
        val elapsed = SystemClock.elapsedRealtime() - lastTriggerTime.get()
        return max(0L, COOLDOWN_MS - elapsed)
    }
}
