package sola.martin.kotlinmessenger.models

class User(val uid: String, val username: String, val profileImageUrl: String) {
    constructor() : this("", "", "")   /// ovo nekužim to je neka kotlin sintaksa

}