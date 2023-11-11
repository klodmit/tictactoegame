package com.example.tictactoegame

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.example.tictactoegame.databinding.ActivitySettingsBinding
import java.lang.reflect.Array.getInt

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private var currentSound = 0
    private var currentLevel = 0
    private var currentRules = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        val data = getSettingsInfo()

        currentSound = data.soundValue
        currentLevel = data.lvl
        currentRules = data.rules

        when(currentRules) {
            1 -> binding.verticalRules.isChecked = true
            2 -> binding.horizontalRules.isChecked = true
            3 -> {
                binding.verticalRules.isChecked = true
                binding.horizontalRules.isChecked = true
            }
            4 -> binding.diagonalRules.isChecked = true
            5 -> {
                binding.diagonalRules.isChecked = true
                binding.verticalRules.isChecked = true
            }
            6-> {
                binding.diagonalRules.isChecked = true
                binding.horizontalRules.isChecked = true
            }
            7 -> {
                binding.diagonalRules.isChecked = true
                binding.horizontalRules.isChecked = true
                binding.verticalRules.isChecked = true
            }
        }

        if(currentLevel == 0){
            binding.prevlvl.visibility = View.INVISIBLE
        } else if(currentLevel==2){
            binding.nextlvl.visibility = View.INVISIBLE
        }

        binding.infoLevel.text = resources.getStringArray(R.array.game_level)[currentLevel]
        binding.soundbar.progress = currentSound

        binding.prevlvl.setOnClickListener {
            currentLevel--

            if(currentLevel == 0){
                binding.prevlvl.visibility = View.INVISIBLE
            } else if(currentLevel==1){
                binding.nextlvl.visibility = View.VISIBLE
            }
            binding.infoLevel.text = resources.getStringArray(R.array.game_level)[currentLevel]
            updatelvl(currentLevel)
        }

        binding.nextlvl.setOnClickListener {
            currentLevel++

            if(currentLevel == 1){
                binding.prevlvl.visibility = View.VISIBLE
            } else if(currentLevel==2){
                binding.nextlvl.visibility = View.INVISIBLE
            }
            binding.infoLevel.text = resources.getStringArray(R.array.game_level)[currentLevel]
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