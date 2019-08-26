package sola.martin.kotlinmessenger.models

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String, val profileImageUrl: String): Parcelable {
    constructor() : this("", "", "")   /// ovo nekužim to je neka kotlin sintaksa


}