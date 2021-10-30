/* Lunar Age Calendar - Lunar Age
* Kim Hyun
* Model (Xml)
* Model of lunar age
* */
package com.example.lunaragecalendar.api

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class XmlResponse(
    @Element
    val header: XmlHeader,
    @Element
    val body: XmlBody
)

@Xml(name = "header")
data class XmlHeader(
    @PropertyElement val resultCode: String,
    @PropertyElement val resultMsg: String,
)

@Xml(name = "body")
data class XmlBody(
    @Element val items: Items,
    @PropertyElement val numOfRows: Int,
    @PropertyElement val pageNo: Int,
    @PropertyElement val totalCount: Int
)

@Xml
data class Items(
    @Element val item: LunarAge
)

@Xml(name = "item")
data class LunarAge(
    @PropertyElement var lunAge: Double?,
    @PropertyElement var solDay: Int?,
    @PropertyElement var solMonth: Int?,
    @PropertyElement var solWeek: String?,
    @PropertyElement var solYear: Int?
){
    constructor():this(0.0,30,10,"토",2021)
}
