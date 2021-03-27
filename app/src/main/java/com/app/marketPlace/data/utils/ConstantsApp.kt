package com.app.marketPlace.data.utils



object ConstantsApp {



    const val APIKEY ="zXSRzd1MQNKNebKntBATTcQj"

    const val COFFEE_MAKER = "abcat0912005"
    const val CELL_PHONES = "pcmcat209400050001"
    const val LAPTOPS = "abcat0502000"
    const val TVS = "abcat0101000"
    const val PLAYSTATION = "pcmcat295700050012"
    const val CAMERA = "abcat0401000"
    const val HEADPHONES = "abcat0204000"
    const val MONITORS = "abcat0509000"
    const val COMPUTER_KEYBOARDS = "abcat0513004"
    const val PHONES = "abcat0800000"
    const val BEST_PATH = "(id=abcat*)"


    fun getImage(index: Int): String {
        return if (index < listImagesByCategory.size){
            listImagesByCategory[index]
        }else {
            listImagesByCategory[index - listImagesByCategory.size]
        }
    }
    val listImagesByCategory = listOf(
        "https://ic.wampi.ru/2021/03/27/one.png",
        "https://ic.wampi.ru/2021/03/27/two.png",
        "https://ic.wampi.ru/2021/03/27/three.png",
        "https://ic.wampi.ru/2021/03/27/foure.png",
        "https://ic.wampi.ru/2021/03/27/five.png",
        "https://ic.wampi.ru/2021/03/27/six.png",
        "https://ic.wampi.ru/2021/03/27/seven.png",
        "https://ic.wampi.ru/2021/03/27/eight.png",
        "https://ic.wampi.ru/2021/03/27/nine.png",
        "https://ic.wampi.ru/2021/03/27/ten.png"
    )
}