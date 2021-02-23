package com.appmarketplace.ozon.domain.mappers

import com.appmarketplace.ozon.presentation.rowType.Resource
import com.appmarketplace.ozon.domain.modelsUI.GeneralCategory
import com.appmarketplace.ozon.domain.modelsUI.OnBoardingItem

class MapListCategoryToListData<T,M>() : MapperUI<T,M> {

    val listImagesByCategory = listOf<String>(
        "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=1",
        "https://www.dropbox.com/s/3oh2dtpcais3pmm/two.png?dl=1",
        "https://www.dropbox.com/s/s21jitjxbehsu6m/three.png?dl=1",
        "https://www.dropbox.com/s/rp58pn5csppxveg/foure.png?dl=1",
        "https://www.dropbox.com/s/dwsgfil13yaqfl6/five.png?dl=1",
        "https://www.dropbox.com/s/tp5dn122l4fxisz/six.png?dl=1",
        "https://www.dropbox.com/s/ral28ayfirfeq08/seven.png?dl=1",
        "https://www.dropbox.com/s/iweu5xow84uzw0q/eight.png?dl=1",
        "https://www.dropbox.com/s/da22ge77utrs7gk/nine.png?dl=1",
        "https://www.dropbox.com/s/al1j3eqjqwgieda/ten.png?dl=1",
        "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=1",
        "https://www.dropbox.com/s/3oh2dtpcais3pmm/two.png?dl=1",
        "https://www.dropbox.com/s/s21jitjxbehsu6m/three.png?dl=1",
        "https://www.dropbox.com/s/rp58pn5csppxveg/foure.png?dl=1",
        "https://www.dropbox.com/s/dwsgfil13yaqfl6/five.png?dl=1",
        "https://www.dropbox.com/s/tp5dn122l4fxisz/six.png?dl=1",
        "https://www.dropbox.com/s/ral28ayfirfeq08/seven.png?dl=1",
        "https://www.dropbox.com/s/iweu5xow84uzw0q/eight.png?dl=1",
        "https://www.dropbox.com/s/da22ge77utrs7gk/nine.png?dl=1",
        "https://www.dropbox.com/s/al1j3eqjqwgieda/ten.png?dl=1"
    )



    override fun map(list: M): Resource<T> {

        list as GeneralCategory
        for ((idex, i) in (0 until list?.categories!!.size).withIndex()) {
            list.categories[i].image = listImagesByCategory[idex]
        }

        val mapperedData = mutableListOf(

            list.categories
                .take(10)
                .map {
                    OnBoardingItem(title = it.name, onBoardingImageUrl = it.image, category = it.id)
                }.toMutableList(),

            list.categories
                .takeLast(10)
                .map {
                    OnBoardingItem(title = it.name, onBoardingImageUrl = it.image, category = it.id)
                }.toMutableList()
        )

        val result =
            Resource(status = Resource.Status.COMPLETED, data = mapperedData as T, exception = null)
        return result
    }


}