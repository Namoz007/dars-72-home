package com.example.dars_72

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.content.Context
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraAccessException

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.yourapp/flashlight"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if (call.method == "toggleFlashlight") {
                val isFlashlightOn = call.argument<Boolean>("status") ?: false
                toggleFlashlight(isFlashlightOn)
                result.success(null)
            } else {
                result.notImplemented()
            }
        }
    }

    private fun toggleFlashlight(status: Boolean) {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList[0] // Assume the first camera is the back camera
            cameraManager.setTorchMode(cameraId, status)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}
