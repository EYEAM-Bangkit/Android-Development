package com.slyvii.eyeam.data

import com.slyvii.eyeam.R

object AnimalData {
    private val data : List<Animal> = listOf(
        Animal(id = 0, name = "Binturong", image = R.drawable.binturong),
        Animal(id = 1, name = "Koala", image = R.drawable.koala),
        Animal(2,"Lemur",R.drawable.lemur),
        Animal(3, "Tarsier", R.drawable.tarsier),
        Animal(4,"Walrus", R.drawable.walrus)
    )
    val list : ArrayList<Animal>
    get(){
        val listdata = ArrayList<Animal>()
        for (i in data){
            val data = Animal()
            data.id = i.id
            data.name = i.name
            data.image = i.image
            listdata.add(data)
        }
        return listdata
    }
}