package com.amog.starwarscatalogue.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("height")
    var height: String? = null,
    @SerializedName("birth_year")
    var birthYear: String? = null,
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("homeworld")
    var homeworld: String? = null,
    @SerializedName("films")
    var movies: ArrayList<String>? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        arrayListOf<String>().apply {
            parcel.readList(this, String::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(height)
        parcel.writeString(birthYear)
        parcel.writeString(gender)
        parcel.writeString(homeworld)
        parcel.writeList(movies)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }
}
