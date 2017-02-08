# PasswordInputView #
密码输入框    

 ![image](https://github.com/dongtaonote/PasswordInputView/blob/master/app/src/main/assets/Screenshot_2017-02-07-18-42-40.png)

## 自定义属性 ##

        <!--边框获得焦点时的颜色-->
        <attr name="bordNotFocusedColor" format="color" />
        <!--边框获得焦点时的颜色-->
        <attr name="bordFocusedColor" format="color" />
        <!--边框内容的填充颜色因为花边框需要空心-->
        <attr name="bordContentColor" format="color" />
        <!--数字的颜色-->
        <attr name="textColor" format="color" />
        <!--整个EditTextView的背景颜色-->
        <attr name="backgroundColor" format="color" />
        <!--光标的颜色-->
        <attr name="cursorColor" format="color" />
        <!--光标厚度-->
        <attr name="cursorWidth" format="dimension" />
        <!--数字的textSize-->
        <attr name="textSize" format="dimension" />
        <!--边框的粗细-->
        <attr name="bordWidth" format="dimension" />
        <!--密码的位数-->
        <attr name="passwordLength" format="integer" />
        <!--方块与方块之间的距离-->
        <attr name="boxMarge" format="dimension" />
        <!--方块的圆角角度-->
        <attr name="boxRadius" format="dimension" />
        <!--密文的原点半径-->
        <attr name="dotRadius" format="dimension" />
        <!--是否显示光标-->
        <attr name="cursorVisible" format="boolean" />
        <!--是否显示密文-->
        <attr name="cipherText" format="boolean" />

## 使用 ##

在你的项目中加入PasswordInput Library，之后在xml使用即可  
	
	<com.example.password.lib.PasswordInput
    android:id="@+id/passwordInput"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    app:boxCount="6"
    app:bordFocusedColor="@color/colorAccent"
    app:bordNotFocusedColor="@color/colorPrimary" />

### 获得输入的内容 ###

	PasswordInput passwordInput = (PasswordInput) findViewById(R.id.passwordInput);
	String pwdFirst = passwordInput.getText().toString();

### 设置输入的内容 ###
    PasswordInput passwordInput = (PasswordInput) findViewById(R.id.passwordInput);
    passwordInput.setText("这里");


### 设置内容改变监听 ###

    passwordInput.setTextLenChangeListen(new PasswordInput.TextLenChangeListen() {
        @Override
        public void onTextLenChange(CharSequence text, int len) {
           //do something
        }
    });
