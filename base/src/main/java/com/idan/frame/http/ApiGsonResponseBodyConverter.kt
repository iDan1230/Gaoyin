/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.idan.frame.http

import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.io.StringReader

/**
 * createUser : Administrator
 * createDate : 2020/9/2
 * remark     : 自定义ResponseConverter, 直接返回body或者data
 */
internal class ApiGsonResponseBodyConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) :
    Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        return try {
            var response = value.string()
            var jsonObject = JSON.parseObject(response)
            //获取body或者data的结果并返回（根据不同的需求自定义增加）
            var body: String = if (jsonObject.containsKey("body")) {
                jsonObject.getString("body")
            } else if (jsonObject.containsKey("data")) {
                jsonObject.getString("body")
            } else {
                response
            }
            val reader = StringReader(body)
            val jsonReader = gson.newJsonReader(reader)
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            result
        } finally {
            value.close()
        }
    }

}