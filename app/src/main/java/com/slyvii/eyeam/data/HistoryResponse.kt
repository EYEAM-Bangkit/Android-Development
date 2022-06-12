package com.slyvii.eyeam.data

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: ArrayList<DataItems>? = null
)

data class DataItems(

	@field:SerializedName("eventid")
	val eventid: String? = null,

	@field:SerializedName("animal")
	val animal: String? = null,

	@field:SerializedName("logtime")
	val logtime: String? = null,

	@field:SerializedName("userid")
	val userid: String? = null
)
