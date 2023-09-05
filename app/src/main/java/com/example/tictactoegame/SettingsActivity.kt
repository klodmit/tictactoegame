package com.example.tictactoegame

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoegame.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private var currentSound = 0
    private var currentLevel = 0
    private var currentRules = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        binding.prevlvl.setOnClickListener{
            currentLevel--
        }

        binding.nextlvl.setOnClickListener{
            currentLevel++
        }

        binding.soundbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, value: Int, p2: Boolean) {
                currentSound = value
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        binding.verticalRules.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                currentRules++
            }
            else{
                currentRules--
            }
        }
        binding.horizontalRules.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                currentRules+=2
            }
            else{
                currentRules-=2
            }
        }
        binding.diagonalRules.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                currentRules+=4
            }
            else{
                currentRules-=4
            }
        }

        setContentView(binding.root)
    }
}