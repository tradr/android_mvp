package com.tradr.http.amb

import com.tradr.models.AMBAsset
import com.tradr.models.AMBEvent
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/*
Copyright: Ambrosus Technologies GmbH
Email: tech@ambrosus.com
This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/.
This Source Code Form is "Incompatible With Secondary Licenses", as defined by the Mozilla Public License, v. 2.0.

Edited by svenji: Use Observables rather than Call, use Any instead of Object, use Gson to deserialize response, formatting
Original: https://github.com/ambrosus/sdk-android/blob/master/ambrosussdk/src/main/java/com/ambrosus/ambrosussdk/network/AMBService.kt
*/

interface AMBService {
    @GET("assets/{assetId}")
    fun getAsset(@Path("assetId") assetId: String): Observable<Response<AMBAsset>>

    @GET("events")
    fun getEvents(@Query("assetId") assetId: String): Observable<Response<List<AMBEvent>>>

    @GET("events?data[type]=ambrosus.asset.identifier")
    fun getEventsByCode(@QueryMap params: Map<String, String>): Observable<Response<List<AMBEvent>>>
}