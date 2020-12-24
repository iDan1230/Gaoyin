package com.idan.frame.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ApiGsonConverterFactory : Converter.Factory() {

    val gson: Gson = Gson()

    companion object {
        fun create() = ApiGsonConverterFactory()
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return ApiGsonResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation?>,
        methodAnnotations: Array<Annotation?>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return ApiGsonRequestBodyConverter(gson, adapter)
    }
}