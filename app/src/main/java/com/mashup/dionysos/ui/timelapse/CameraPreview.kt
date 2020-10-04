package com.mashup.dionysos.ui.timelapse

import android.content.Context
import android.graphics.PixelFormat
import android.hardware.Camera
import android.hardware.Camera.PictureCallback
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.coroutines.CoroutineContext


@Suppress("DEPRECATION")
class CameraPreview @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet?
) : SurfaceView(context, attributeSet), SurfaceHolder.Callback, CoroutineScope {

    private val mHolder: SurfaceHolder = holder
    var mCamera: Camera? = null
    var mCameraFacing = 0

    private lateinit var job: Job
    private val increaseTime = 1000L
    var playStatus = false
    var increase = true

    private var safeToTakePicture = false

    lateinit var timeLapsViewModel: TimeLapseViewModel

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    init {
        mHolder.addCallback(this)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        // 미리보기 화면에 픽셀로 뿌리기 시작! 렌즈로부터 들어온 영상을 뿌려줌.
        Log.e("surfaceChanged", " ::  ")
        mCamera?.startPreview()
        safeToTakePicture = true
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.e("surfaceDestroyed", " ::  ")
        stop()
    }


    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.e("surfaceCreated", " ::  ")
        facing()

        job = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                if (playStatus) {
                    increasePlayTime()
                }
                Thread.sleep(increaseTime)
            }
        }
    }

    private fun increasePlayTime() {
        timeLapsViewModel.let { it_ ->
            var base = it_.controlTime.value!!
            val timeDataModel = it_.timeDataModel.value!!

            if (increase) {
                base += increaseTime
                timeDataModel.totalTime += increaseTime
            } else {
                base -= increaseTime
                timeDataModel.timer -= increaseTime
                timeDataModel.totalTime += increaseTime
            }
            it_.controlTime.postValue(base)
            it_.timeDataModel.postValue(timeDataModel)
        }
        capture(jpegCallback)
    }

    // 서피스뷰에서 사진을 찍도록 하는 메서드
    private fun capture(callback: Camera.PictureCallback): Boolean {
        mCamera?.let {
            try {
                if (safeToTakePicture) {
                    it.takePicture(null, null, callback)
                    Log.e("takePicture", "try  takePicture")
                }
                Log.e("takePicture", "try")
            } catch (error: IOException) {
                Log.e("IOException", error.toString())
            } catch (error: RuntimeException) {
                Log.e("RuntimeException", error.toString())
            }
        }
        return (mCamera != null)
    }

    private var fileNum = 0
    private var jpegCallback =
        PictureCallback { data: ByteArray?, camera: Camera? ->
            mCamera?.startPreview()
            val basePath = "/data/data/com.mashup.dionysos/files/"
            val folderName = basePath + timeLapsViewModel.fileName
            val fileName = String.format("%05d", fileNum) + ".jpg"
            try {
                val dir = File(folderName);
                if (!dir.exists()) {
                    dir.mkdir(); }
                Log.e("fileName ", fileName)
                val fos = FileOutputStream("$folderName/$fileName", true)
                fos.write(data)
                fos.close();
            } catch (e: IOException) {
                e.printStackTrace();
            }
            fileNum++
            safeToTakePicture = true;
        }


    private fun facing() {
        Log.e("mCameraFacing", " ::   $mCameraFacing")
        mCamera = Camera.open(mCameraFacing)
        mCamera?.let {
            it.setDisplayOrientation(90) // 이게 없으면 미리보기 화면이 회전되어 나온다.
            try {
                val parameters = it.parameters
                parameters.set("jpeg-quality", 70)
                parameters.pictureFormat = PixelFormat.JPEG
                parameters.setPictureSize(640, 480)
                it.parameters = parameters
                it.setPreviewDisplay(mHolder)// Camera 객체에 이 서피스뷰를 미리보기로 하도록 설정
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun stop() {
        Log.e("surface    ", " ::  stop()")
        job.cancel()
        mCamera?.let {
            mCamera?.stopPreview()
            mCamera?.release() // 리소스 해제
            mCamera = null
        }
    }
}
