package com.example.afya.data.model

class User(var nomUtil: String?, var motPasse: String?) : java.io.Serializable {
    override fun toString(): String {
        return "User(nomUtil=$nomUtil, motPasse=$motPasse)"
    }
}

