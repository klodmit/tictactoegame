package com.example.tictactoegame

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoegame.databinding.ActivitySettingsBinding
import java.lang.reflect.Array.getInt

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private var currentSound = 0
    private var currentLevel = 0
    private var currentRules = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = getSettingsInfo()

        currentSound = data.soundValue
        currentLevel = data.lvl
        currentRules = data.rules

        binding = ActivitySettingsBinding.inflate(layoutInflater)

        binding.prevlvl.setOnClickListener {
            currentLevel--
            updatelvl(currentLevel)
        }

        binding.nextlvl.setOnClickListener {
            currentLevel++
            updatelvl(currentLevel)
        }

        binding.soundbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, value: Int, p2: Boolean) {
                currentSound = value
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                updateSoundValue(currentSound)
            }
        })

        binding.verticalRules.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentRules++
            } else {
                currentRules--
            }
            updateRules(currentRules)
        }
        binding.horizontalRules.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentRules += 2
            } else {
                currentRules -= 2
            }
            updateRules(currentRules)
        }
        binding.diagonalRules.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentRules += 4
            } else {
                currentRules -= 4
            }
            updateRules(currentRules)
        }

        setContentView(binding.root)
    }

    private fun updateSoundValue(value: Int) {
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()) {
            putInt("sound_value", value)
            apply()
        }
    }

    private fun updatelvl(lvl: Int) {
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()) {
            putInt("lvl", lvl)
            apply()
        }
    }

    private fun updateRules(rules: Int) {
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()) {
            putInt("rules", rules)
            apply()
        }
    }

    private fun getSettingsInfo(): SettingsInfo {
        with(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit()) {
            val soundValue = getInt("sound_value", 0)
            val lvl = getInt("lvl", 0)
            val rules = getInt("rules", 0)

            return SettingsInfo(soundValue, lvl, rules)
        }
    }

    data class SettingsInfo(val soundValue: Int, val lvl: Int, val rules: Int)

}