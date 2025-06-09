package com.onion.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.onion.center.protocol.AppConstant
import com.onion.center.protocol.LoginStatus
import com.onion.core.common.http.HttpState
import com.onion.core.platformhub.extra.queryResult
import com.onion.core.platformhub.model.PluginSender
import com.onion.login.bean.LoginReq
import com.onion.login.plugin.LoginPlugin
import com.onion.login.viewmodel.LoginViewModel
import com.onion.login.widget.LoginIcon
import com.onion.login.widget.moreLogin
import com.onion.resource.common.EditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named
import com.onion.resource.R as Resource

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: LoginActivity
 * Author: admin by 张琦
 * Date: 2024/3/24 18:54
 * Description:
 */
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var loginPlugin: LoginPlugin

    @Inject
    @Named(AppConstant.Plugin.AppPlugin)
    lateinit var appSender: PluginSender

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val loginStatus = loginPlugin.queryResult<LoginStatus>(appSender.query("GetLoginStatus"))

        setContent {
            val max = Modifier.fillMaxSize()

            val finish = {
                finish()
            }

            Surface(
                modifier = max
                    .background(Color.White),
            ) {
                loginScreen(finish)
            }

        }
    }

}

@Composable
internal fun loginScreen(finish: () -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
    val sendState = viewModel.sendCodeState.collectAsStateWithLifecycle()

    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    val s = remember {
        mutableStateOf(false)
    }

    when(loginState){
        is HttpState.Loading -> {

        }
        is HttpState.Success -> {

        }
        else -> {}
    }

    val login = { req: LoginReq ->
        viewModel.login(req)
    }

    loginView(finish,login)
}

@Composable
internal fun loginView(finish: () -> Unit, login: (LoginReq) -> Unit) {

    val loginSwitch = remember {
        mutableStateOf(true)
    }

    val phoneInput = remember {
        mutableStateOf("")
    }

    val phoneTextChange = { text: String ->
        phoneInput.value = text
    }

    val passwordInput = remember {
        mutableStateOf("")
    }

    val passwordTextChange = { text: String ->
        passwordInput.value = text
    }

    val checkBox = remember {
        mutableStateOf(false)
    }

    val moreLoginList = arrayListOf<LoginIcon>()

    val buttonEnable = remember {
        derivedStateOf {
            ((loginSwitch.value && phoneInput.value.length >= 11) || (!loginSwitch.value && phoneInput.value.length >= 11 && passwordInput.value.length >= 9))
                    && checkBox.value
        }
    }

    moreLoginList.add(LoginIcon(0, R.drawable.icon_wechart, "wechat", {

    }))
    moreLoginList.add(LoginIcon(1, R.drawable.icon_qq, "qq", {
    }))
    moreLoginList.add(LoginIcon(2, R.drawable.icon_weibo, "微博", {

    }))
    moreLoginList.add(LoginIcon(3, R.drawable.icon_iphone, "苹果", {

    }))

    val btnClick = {
        if(loginSwitch.value){
            //验证码登录
        }else{
            //密码登录
            val req = LoginReq(
                phone = phoneInput.value,
                password = passwordInput.value
            )
            login(req)
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (close, open, canNotLogin, moreLogin, title, subTitle, loginBtn, phoneCons, passwordCons, switch, checked) = createRefs()

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0x33409EFF),
                            Color.Transparent
                        ),
                    )
                )
        ) {

        }

        IconButton(modifier = Modifier
            .constrainAs(close) {
                top.linkTo(parent.top, 40.dp)
                start.linkTo(parent.start)
            }, onClick = {
            finish()
        }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
        }

        Text(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(canNotLogin) {
                    top.linkTo(parent.top, 60.dp)
                    end.linkTo(parent.end, 20.dp)
                },
            text = stringResource(id = R.string.login_can_not_login),
            color = colorResource(id = Resource.color.resource_text_color_333)
        )

        Text(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(title) {
                    top.linkTo(close.bottom, 40.dp)
                    start.linkTo(parent.start, 40.dp)
                },
            fontSize = 25.sp,
            fontWeight = FontWeight(700),
            text = stringResource(id = R.string.login_please_input_phone),
            color = colorResource(id = Resource.color.resource_text_color_111),
        )

        Text(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(subTitle) {
                    top.linkTo(title.bottom, 5.dp)
                    start.linkTo(title.start)
                },
            fontSize = 14.sp,
            fontWeight = FontWeight(400),
            text = stringResource(id = R.string.login_login_phone_tips),
            color = colorResource(id = Resource.color.resource_text_color_999),
        )

        //手机号输入框
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 40.dp)
                .clip(shape = RoundedCornerShape(28.dp))
                .background(color = colorResource(id = Resource.color.resource_button_normal_background))
                .padding(horizontal = 20.dp)
                .constrainAs(phoneCons) {
                    top.linkTo(subTitle.bottom, 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "+86", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(
                    id = Resource.color.resource_text_color_111
                )
            )
            Icon(
                modifier = Modifier.padding(start = 1.dp),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "down"
            )

            EditText(
                value = phoneInput.value,
                onValueChange = phoneTextChange,
                modifier = Modifier.padding(start = 10.dp),
                maxLines = 11,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = colorResource(id = Resource.color.resource_text_color_111),
                    fontWeight = FontWeight.Medium
                ),
                hintTextStyle = TextStyle(
                    fontSize = 18.sp,
                    color = colorResource(id = Resource.color.resource_hint_text_color),
                    fontWeight = FontWeight.Medium
                ),
                hint = stringResource(id = R.string.login_please_input_phone)
            )
        }

        //密码输入框
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 40.dp)
                .clip(shape = RoundedCornerShape(28.dp))
                .background(color = colorResource(id = Resource.color.resource_button_normal_background))
                .padding(horizontal = 20.dp)
                .constrainAs(passwordCons) {
                    top.linkTo(phoneCons.bottom, 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    visibility = if (loginSwitch.value) Visibility.Gone else Visibility.Visible
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            EditText(
                value = passwordInput.value,
                onValueChange = passwordTextChange,
                modifier = Modifier.padding(start = 10.dp),
                maxLines = 11,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = colorResource(id = Resource.color.resource_text_color_111),
                    fontWeight = FontWeight.Medium
                ),
                hintTextStyle = TextStyle(
                    fontSize = 18.sp,
                    color = colorResource(id = Resource.color.resource_hint_text_color),
                    fontWeight = FontWeight.Medium
                ),
                hint = stringResource(id = R.string.login_please_input_password)
            )
        }

        //登录按钮
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 40.dp)
                .clip(shape = RoundedCornerShape(28.dp))
                .constrainAs(loginBtn) {
                    top.linkTo(passwordCons.bottom, 15.dp, goneMargin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            enabled = buttonEnable.value,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = Resource.color.resource_button_primary_color),
                disabledContainerColor = colorResource(id = Resource.color.resource_button_normal_color)
            ),
            onClick = btnClick,
        ) {
            Text(
                text = if (loginSwitch.value) stringResource(id = R.string.login_get_verify_code) else stringResource(
                    id = R.string.login_go_to_login
                ), fontSize = 16.sp, fontWeight = FontWeight.Medium, color = colorResource(
                    id = Resource.color.resource_white
                )
            )
        }

        //登录状态切换
        TextButton(modifier = Modifier
            .wrapContentSize()
            .constrainAs(switch) {
                top.linkTo(loginBtn.bottom, 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, onClick = {
            loginSwitch.value = !loginSwitch.value
            passwordInput.value = ""
        }, interactionSource = remember {
            MutableInteractionSource()
        }) {
            Text(
                text = if (loginSwitch.value) stringResource(id = R.string.login_password_login) else stringResource(
                    id = R.string.login_code_login
                ), fontSize = 14.sp, color = colorResource(
                    id = Resource.color.resource_text_color_666
                )
            )
        }

        //协议框
        Row(modifier = Modifier
            .wrapContentSize()
            .constrainAs(checked) {
                top.linkTo(switch.bottom, 0.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(modifier = Modifier
                .wrapContentSize(),
                colors = CheckboxDefaults.colors(colorResource(id = Resource.color.resource_button_primary_color)),
                checked = checkBox.value,
                onCheckedChange = { checkBox.value = it })
            Text(text = "我已经阅读并同意", fontSize = 12.sp,color = colorResource(id = Resource.color.resource_text_color_999))
            Text(text = "《用户协议》", fontSize = 12.sp,color = colorResource(id = Resource.color.resource_button_primary_color))
            Spacer(modifier = Modifier.size(2.dp, 0.dp))
            Text(text = "《隐私协议》", fontSize = 12.sp,color = colorResource(id = Resource.color.resource_button_primary_color))
        }

        moreLogin(
            Modifier
                .wrapContentSize()
                .constrainAs(moreLogin) {
                    bottom.linkTo(parent.bottom, 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, moreLoginList
        )
    }
}