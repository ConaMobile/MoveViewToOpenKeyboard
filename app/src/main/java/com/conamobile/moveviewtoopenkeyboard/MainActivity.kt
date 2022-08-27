package com.conamobile.moveviewtoopenkeyboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.conamobile.moveviewtoopenkeyboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val insetsWithKeyboardCallback = InsetsWithKeyboardCallback(window)
        ViewCompat.setOnApplyWindowInsetsListener(binding.contentId, insetsWithKeyboardCallback)
        ViewCompat.setWindowInsetsAnimationCallback(binding.contentId, insetsWithKeyboardCallback)

        val insetsWithKeyboardAnimationCallback =
            InsetsWithKeyboardAnimationCallback(binding.loginButton)
        ViewCompat.setWindowInsetsAnimationCallback(binding.loginButton,
            insetsWithKeyboardAnimationCallback)

    }
}