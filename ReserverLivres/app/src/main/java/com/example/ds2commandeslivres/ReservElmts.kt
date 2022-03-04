package com.example.ds2commandeslivres

class ReservElmts {

    companion object Factory {
        fun create(): ReservElmts = ReservElmts()
    }
    var livreId: String? = null
    var nomLivreResrv: String? = null

}