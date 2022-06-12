package com.slyvii.eyeam.data

import com.google.gson.annotations.SerializedName

data class LogsResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("eventid")
	val eventid: String? = null,

	@field:SerializedName("animal")
	val animal: String? = null,

	@field:SerializedName("logtime")
	val logtime: String? = null,

	@field:SerializedName("userid")
	val userid: String? = null
)
