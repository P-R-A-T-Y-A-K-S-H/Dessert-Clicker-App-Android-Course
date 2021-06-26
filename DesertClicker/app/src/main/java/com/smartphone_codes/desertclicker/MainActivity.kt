package com.smartphone_codes.desertclicker

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.smartphone_codes.desertclicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val REVENUE_KEY = "KEY_revenue"
        const val DESERT_KEY = "KEY_desert_sold"
    }

    data class Dessert(val resId: Int, val price: Int, val capitalAmount: Int)

    private val allDesert = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 2),
        Dessert(R.drawable.eclair, 15, 3),
        Dessert(R.drawable.froyo, 30, 4),
        Dessert(R.drawable.gingerbread, 50, 5),
        Dessert(R.drawable.honeycomb, 100, 6),
        Dessert(R.drawable.icecreamsandwich, 500, 7),
        Dessert(R.drawable.jellybean, 1000, 8),
        Dessert(R.drawable.kitkat, 2000, 9),
        Dessert(R.drawable.lollipop, 3000, 10),
        Dessert(R.drawable.marshmallow, 4000, 20),
        Dessert(R.drawable.nougat, 5000, 30),
        Dessert(R.drawable.oreo, 6000, 50)
    )
    lateinit var binding: ActivityMainBinding
    private var currentdesert = allDesert[0]
    private var revenue: Int = 0
    private var desertSold: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Companion.TAG, "onCreate Called")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState != null) {
            revenue = savedInstanceState.getInt(REVENUE_KEY, 0)
            desertSold = savedInstanceState.getInt(DESERT_KEY, 0)
            setDesert()
        }

        binding.revenue = revenue
        binding.desertSold = desertSold
        binding.dessertButton.setOnClickListener { onDesertClick() }
        binding.dessertButton.setImageResource(currentdesert.resId)


    }

    override fun onStart() {
        Log.d(TAG, "onStrart() Called")
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }

    private fun onDesertClick() {
        revenue += currentdesert.price
        desertSold++

        binding.revenue = revenue
        binding.amountSoldText.text = desertSold.toString()
        setDesert()
    }

    private fun setDesert() {
        var newDesert = allDesert[0]
        for (deserts in allDesert) {
            if (desertSold >= deserts.capitalAmount) {
                newDesert = deserts

            } else break
        }
        if (newDesert != currentdesert) {
            currentdesert = newDesert
            binding.dessertButton.setImageResource(newDesert.resId)

        }
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text, desertSold, revenue))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSavedInstanceState called")

        outState.putInt(REVENUE_KEY, revenue)
        outState.putInt(DESERT_KEY, desertSold)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState called")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbarmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sharescoreopt -> {
                onShare()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}


