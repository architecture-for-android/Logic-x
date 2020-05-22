## Logic-x [ ![Download](https://api.bintray.com/packages/meikoz/Things2/logic-x/images/download.svg) ](https://bintray.com/meikoz/Things2/logic-x/_latestVersion)
Model-View-Presenter

## Usage
```
repositories {
    maven { url 'https://dl.bintray.com/meikoz/Things2' }
}

dependencies {
    //android support v7
    implementation 'com.racofix.things2:logic:1.1.7'
    //androidx
    implementation 'com.racofix.things2:logic-x:1.1.7'
}
```

#### View Layer (视图层)
```
@LogicArr({Register.class, Login.class})
public class LoginActivity extends BaseLogicActivity implements LoginI.View, RegisterI.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLogic(Login.class).sign_in("用户名", "密码");
    }

    @Override
    public void sign_in_success() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sign_up_success() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }
}
```

#### P Layer (逻辑层)
```
public interface LoginI {

    interface Logic {
        void sign_in(String username, String password);
    }

    interface View {
        void sign_in_success();
    }
}

public class Login extends BaseLogicImpl<LoginI.View> implements LoginI.Logic {

    @Override
    public void sign_in(String username, String password) {
        getView().sign_in_success();
    }
}
```
