package com.example.reccyclergrid.models

data class Person(val id: Int, val name: String, val lastName: String, val gender: Boolean)

var personsList = mutableListOf(
    (Person(1, "nika", "namoradze", true)),
    (Person(2, "lika", "khokhiashvili", false)),
    (Person(4, "baia", "asanidze", false)),
    (Person(6, "mariam", "eristavi", false)),
    (Person(3, "bachi", "mosulishvili", true)),
    (Person(5, "luka", "janjgava", true)),
    (Person(7, "giorgi", "dolidze", true)),
    (Person(8, "tengizi", "gachechiladze", true)),
    (Person(9, "mariam", "namgaladze", false)),
)


