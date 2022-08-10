package com.h.haoyangmaov2

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

import java.lang.reflect.ParameterizedType

/**
 * Created by lhn on 2017/6/23.
 */
abstract class BaseAc<T : ViewBinding> : AppCompatActivity() {
    var canSwipeBack = true
    lateinit var binding: T


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        binding = getViewBindingForActivity(layoutInflater)
        setContentView(binding.root)
        initViews()
    }





    @Suppress("UNCHECKED_CAST")
    private fun getViewBindingForActivity(layoutInflater: LayoutInflater): T {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as T
    }

    private fun canSwipeBack(): Boolean {
        return canSwipeBack
    }

 abstract   fun initViews()






}