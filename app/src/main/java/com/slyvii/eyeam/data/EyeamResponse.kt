package com.slyvii.eyeam.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class EyeamResponse(

	@field:SerializedName("data")
	val data: List<Data>
)

@Parcelize
data class Data(

	@field:SerializedName("habitat")
	val habitat: String? = null,

	@field:SerializedName("iucn")
	val iucn: String? = null,

	@field:SerializedName("namailmiah")
	val namailmiah: String? = null,

	@field:SerializedName("kingdom")
	val kingdom: String? = null,

	@field:SerializedName("conserv")
	val conserv: String? = null,

	@field:SerializedName("persebaran")
	val persebaran: String? = null,

	@field:SerializedName("phylum")
	val phylum: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("genus")
	val genus: String? = null,

	@field:SerializedName("populasi")
	val populasi: String? = null,

	@field:SerializedName("threats")
	val threats: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("family")
	val family: String? = null,

	@field:SerializedName("namapopuler")
	val namapopuler: String? = null,

	@field:SerializedName("class")
	val jsonMemberClass: String? = null,

	@field:SerializedName("order")
	val order: String? = null
) : Parcelable
