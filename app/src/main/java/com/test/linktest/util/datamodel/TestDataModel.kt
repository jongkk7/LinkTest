package com.test.linktest.util.datamodel

class TestDataModel (val dateTimes: String){

    constructor(dateTimes: String , title : String) : this(dateTimes)
    constructor(dateTimes: String , title : String, contents: String) : this(dateTimes, title)

    var title : String? = null
    var contents : String? = null

}