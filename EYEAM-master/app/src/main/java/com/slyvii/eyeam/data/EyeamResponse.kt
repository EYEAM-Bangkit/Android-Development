package com.slyvii.eyeam.data

import com.google.gson.annotations.SerializedName

data class EyeamResponse(

	@field:SerializedName("data")
	val data: Data? = null
)

data class Data(

	@field:SerializedName("habitat")
	val habitat: String? = null,

	@field:SerializedName("tersedia")
	val tersedia: Int? = null,

	@field:SerializedName("iucn")
	val iucn: String? = null,

	@field:SerializedName("namailmiah")
	val namailmiah: String? = null,

	@field:SerializedName("taxonomy")
	val taxonomy: String? = null,

	@field:SerializedName("kingdom")
	val kingdom: String? = null,

	@field:SerializedName("ratapanjang")
	val ratapanjang: Int? = null,

	@field:SerializedName("persebaran")
	val persebaran: String? = null,

	@field:SerializedName("rataumur")
	val rataumur: Int? = null,

	@field:SerializedName("foto")
	val foto: Any? = null,

	@field:SerializedName("genus")
	val genus: String? = null,

	@field:SerializedName("species")
	val species: String? = null,

	@field:SerializedName("ordo")
	val ordo: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("ratalebar")
	val ratalebar: Int? = null,

	@field:SerializedName("family")
	val family: String? = null,

	@field:SerializedName("namapopuler")
	val namapopuler: String? = null,

	@field:SerializedName("class")
	val jsonMemberClass: String? = null,

	@field:SerializedName("rataberat")
	val rataberat: Int? = null
)
