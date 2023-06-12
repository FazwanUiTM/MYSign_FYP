package com.example.mysign_fyp

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

class LumaAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {
    private val TAG = "LumaAnalyzer"

    override fun analyze(image: ImageProxy) {
        val buffer: ByteBuffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luma = pixels.average()

        listener(luma)

        image.close()
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }
}
