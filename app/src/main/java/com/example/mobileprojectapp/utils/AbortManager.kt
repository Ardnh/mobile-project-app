package com.example.mobileprojectapp.utils

import kotlinx.coroutines.Job

object HttpAbortManager {

    private val requestMap = mutableMapOf<String, Job>()

    /** abort all requests */
    fun abortAll() {
        requestMap.values.forEach { job ->
            if (job.isActive) job.cancel()
        }
        requestMap.clear()
    }

    /** abort request by id */
    fun abort(id: String) {
        requestMap[id]?.let { job ->
            if (job.isActive) job.cancel()
        }
        requestMap.remove(id)
    }

    /** abort multiple */
    fun abort(ids: List<String>) {
        ids.forEach { abort(it) }
    }

    /** remove request id */
    fun clear(id: String) {
        requestMap.remove(id)
    }

    /**
     * Register coroutine job with id
     * (jika sudah ada → cancel dulu seperti FE version)
     */
    fun register(id: String, job: Job): Job {
        // jika sudah ada dan belum dibatalkan, batalkan dulu
        requestMap[id]?.cancel()
        requestMap[id] = job
        return job
    }
}
