<a href='https://bintray.com/meikoz/Things2/logic-x?source=watch' alt='Get automatic notifications about new "logic-x" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a><a href='https://bintray.com/meikoz/Things2/logic-x?source=watch' alt='Get automatic notifications about new "logic-x" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>
## Usage
```
dependencies {
    // implementation 'com.racofix.things2:logic:1.1.5'

    // androidx
    implementation 'com.racofix.things2:logic-x:1.1.5'
}
```

## Example
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
        /*do some things*/
        getView().sign_in_success();
    }
}
```
