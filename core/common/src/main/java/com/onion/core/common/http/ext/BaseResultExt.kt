package com.onion.core.common.http.ext

import com.onion.core.common.http.BaseResult
import com.onion.core.constants.http.HttpConstant

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: BaseResultExt
 * Author: admin by 张琦
 * Date: 2024/6/29 14:08
 * Description:
 */

fun BaseResult<*>.isSuccess(): Boolean{
    return code == HttpConstant.Success.OK
}