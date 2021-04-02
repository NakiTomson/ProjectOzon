package com.app.marketPlace.data.utils



object Constants {

    const val ApiToken ="zXSRzd1MQNKNebKntBATTcQj"

    const val CoffeeMaker = "abcat0912005"
    const val CellPhone = "pcmcat209400050001"
    const val Laptop = "abcat0502000"
    const val TVS = "abcat0101000"
    const val Playstation = "pcmcat295700050012"
    const val Camera = "abcat0401000"
    const val Headphones = "abcat0204000"
    const val Monitor = "abcat0509000"
    const val ComputerKeyboard = "abcat0513004"
    const val Phone = "abcat0800000"
    
    const val bestPath = "(id=abcat*)"
    const val attrSearch = "search"
    const val attrCategoryPathId = "categoryPath.id"
    const val baseUrl = "https://api.bestbuy.com"


    fun getImage(index: Int): String {
        return if (index < listImagesByCategory.size){
            listImagesByCategory[index]
        }else {
            listImagesByCategory[index - listImagesByCategory.size]
        }
    }
    
    private val listImagesByCategory = listOf(
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