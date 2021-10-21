package com.example.myrecyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecyclerviewapp.model.CatBreed
import com.example.myrecyclerviewapp.model.CatUiModel
import com.example.myrecyclerviewapp.model.Gender
import com.example.myrecyclerviewapp.model.ListItemUiModel

class MainActivity : AppCompatActivity() {
    private val recyclerView : RecyclerView by lazy{
        findViewById(R.id.recycler_view)
    }
    private val addItemButton : View by lazy{
        findViewById(R.id.main_add_item_button)
    }
    private val listItemsAdapter by lazy {
        ListItemsAdapter(layoutInflater , GlideImageLoader(this) , object : ListItemsAdapter.OnClickListener{
            override fun onItemClicked(catData: CatUiModel) {
                showSelectionDialog(catData)
            }
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Main Avtivity" , "messi")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView.adapter = listItemsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false
        )


        val itemTouchHelper = ItemTouchHelper(listItemsAdapter.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        addItemButton.setOnClickListener {
            listItemsAdapter.addItem(1 ,
            ListItemUiModel.Cat(
                CatUiModel(Gender.Female,
                    CatBreed.BalineseJavanese,
                    "Anonymous",
                    "Unknown",
                    "https://cdn2.thecatapi.com/images/zJkeHza2K.jpg")
            )
            )
        }


        listItemsAdapter.setData(
             listOf(
                     ListItemUiModel.Title("Sleeper Agents"),
             ListItemUiModel.Cat(
                     CatUiModel(
                             Gender.Male,
             CatBreed.ExoticShorthair,
             "Garvey",
             "Garvey is as a lazy, fat, and cynical orange cat.",
             "https://cdn2.thecatapi.com/images/FZpeiLi4n.jpg"
             )
         ),
         ListItemUiModel.Cat(
                 CatUiModel(
                         Gender.Unknown,
         CatBreed.AmericanCurl,
         "Curious George",
         "Award winning investigator",
         "https://cdn2.thecatapi.com/images/vJB8rwfdX.jpg"
         )),
         ListItemUiModel.Title("Active Agents")
             )
        )

    }
    private fun showSelectionDialog(catData: CatUiModel) {
        AlertDialog.Builder(this)
            .setTitle("Agent Selected")
            .setMessage("You have selected agent ${catData.name}")
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }
}