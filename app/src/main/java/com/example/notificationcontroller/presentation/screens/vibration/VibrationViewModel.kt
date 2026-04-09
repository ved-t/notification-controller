//package com.example.notificationcontroller.presentation.screens.home
//
//import android.content.Context
//import android.os.Build
//import android.os.VibrationEffect
//import android.os.Vibrator
//import android.os.VibratorManager
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import com.example.notificationcontroller.domain.model.VibrationLevel
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class VibrationViewModel @Inject constructor() : ViewModel() {
//
//    private val _state = mutableStateOf(HomeState())
//    val state: State<HomeState> = _state
//
//    fun onVibrationLevelSelected(level: VibrationLevel) {
//        _state.value = _state.value.copy(selectedVibrationLevel = level)
//    }
//
//    fun triggerVibration(context: Context) {
//        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
//            vibratorManager.defaultVibrator
//        } else {
//            @Suppress("DEPRECATION")
//            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        }
//
//        val duration = _state.value.selectedVibrationLevel.duration
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
//        } else {
//            @Suppress("DEPRECATION")
//            vibrator.vibrate(duration)
//        }
//    }
//}