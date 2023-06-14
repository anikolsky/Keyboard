package com.omtorney.keyboard.service

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.omtorney.keyboard.settings.SettingsStorage
import com.omtorney.keyboard.ui.MyKeyboardView

class KeyboardService :
    InputMethodService(),
    ViewModelStoreOwner,
    LifecycleOwner,
    SavedStateRegistryOwner,
    KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: MyKeyboardView
    private var caps = false

    private val store by lazy { ViewModelStore() }
    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }
    private val savedStateRegistryController by lazy { SavedStateRegistryController.create(this) }

    private val settingsStorage by lazy { SettingsStorage(applicationContext) }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection
        inputConnection?.let {
            when (primaryCode) {
                Keyboard.KEYCODE_DELETE -> onDeleteClick()
                Keyboard.KEYCODE_SHIFT -> onShiftClick()
                else -> {
                    var code = primaryCode.toChar()
                    if (Character.isLetter(code) && caps) {
                        code = code.uppercaseChar()
                    }
                    inputConnection.commitText(code.toString(), 1)
                }
            }
        }
    }

    private fun onShiftClick() {
        caps = !caps
    }

    private fun onDeleteClick() {
        val inputConnection = currentInputConnection
        inputConnection?.let {
            val selectedText = inputConnection.getSelectedText(0)
            if (selectedText.isNullOrEmpty()) {
                inputConnection.deleteSurroundingText(1, 0)
            } else {
                inputConnection.commitText("", 1)
            }
        }
    }

    override fun onPress(p0: Int) {}
    override fun onRelease(p0: Int) {}
    override fun onText(p0: CharSequence?) {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}

    override val viewModelStore: ViewModelStore
        get() = store

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override fun onCreateInputView(): View {
        keyboardView = MyKeyboardView(
            context = this,
            settingsStorage = settingsStorage,
            onKeyClick = { key -> onKey(key.first().toInt(), null) },
            onShiftClick = { onShiftClick() },
            onDeleteClick = { onDeleteClick() }
        )
        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this)
            decorView.setViewTreeViewModelStoreOwner(this)
            decorView.setViewTreeSavedStateRegistryOwner(this)
        }
        return keyboardView
    }

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}
