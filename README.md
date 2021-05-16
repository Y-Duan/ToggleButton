# ToggleButton
滑动开关
![](https://s31.aconvert.com/convert/p3r68-cdx67/9os34-wbd9l.gif)
##简单使用
1. 将toggle-1.0.aar文件放入libs目录下
2. 在build.gradle中加入依赖<br/>implementation(name: 'toggle-1.0', ext: 'aar')
###以上两步导入成功就可以使用了
###具体使用
> xml布局文件中

	xmlns:app="http://schemas.android.com/apk/res-auto"
	<com.lange.toggle.ToggleButton
        android:id="@+id/btn_toggle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:btn_open_bg="@drawable/toggle_open_background"//打开时的背景图或图片
        app:btn_close_bg="@drawable/toggle_close_background"//关闭时的背景图或图片
        app:btn_slide_bg="@drawable/toggle_slide_background"//滑动按钮的图或图片
        app:btn_left_right_padding="1dp"//按钮距离背景图左右的距离
		android:layout_centerInParent="true"/>

> activity中

		ToggleButton btn_toggle = findViewById(R.id.btn_toggle);
        //这是方式2，方式1可以在xml布局文件中直接设置开关图片
        //btn_toggle.setToggleButtonOpenBackgroundResId(R.mipmap.switch_background);//设置滑动按钮背景
        //btn_toggle.setToggleButtonCloseBackgroundResId(R.mipmap.switch_background);//设置滑动按钮背景
        //btn_toggle.setToggleButtonSlideResId(R.mipmap.slide_button_background);//设置滑动按钮图
        //btn_toggle.setToggleButtonState(ToggleButton.ToggleButtonState.Close);//设置开关状态
        //回调监听状态
        btn_toggle.setOnToggleStateChangeListener(new ToggleButton.OnToggleStateChangeListener() {
            @Override
            public void onStateChange(ToggleButton.ToggleButtonState state) {
                Toast.makeText(MainActivity.this, state== ToggleButton.ToggleButtonState.Close?"关":"开", Toast.LENGTH_SHORT).show();
            }
        });

![](https://github.com/Y-Duan/ToggleButton/blob/master/%E5%85%B3%E9%97%AD.png?raw=true)![](https://raw.githubusercontent.com/Y-Duan/ToggleButton/master/%E6%89%93%E5%BC%80.png)