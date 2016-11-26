package com.simicart.saletracking.login.block;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.login.delegate.LoginDelegate;

import org.json.JSONObject;

/**
 * Created by Glenn on 11/24/2016.
 */

public class LoginBlock extends AppBlock implements LoginDelegate {

    protected ImageView ivUrl, ivUser, ivPass, ivLogo;
    protected EditText etUrl, etUser, etPass;
    protected AppCompatButton btLogin, btDemo;
    protected TextView tvOr, tvHelp;
    protected RelativeLayout rlLogin;

    public LoginBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {

        rlLogin = (RelativeLayout) mView.findViewById(R.id.rl_login);
        rlLogin.setBackgroundColor(Color.parseColor("#FD9802"));

        ivLogo = (ImageView) mView.findViewById(R.id.iv_logo);
        ivLogo.setBackgroundResource(R.drawable.login_logo);

        ivUrl = (ImageView) mView.findViewById(R.id.iv_url);
        ivUrl.setImageDrawable(Utils.coloringIcon(R.drawable.ic_url, "#FD9802"));
        ivUrl.setBackgroundColor(Color.parseColor("#000000"));

        ivUser = (ImageView) mView.findViewById(R.id.iv_user);
        ivUser.setImageDrawable(Utils.coloringIcon(R.drawable.ic_user, "#FD9802"));
        ivUser.setBackgroundColor(Color.parseColor("#000000"));

        ivPass = (ImageView) mView.findViewById(R.id.iv_password);
        ivPass.setImageDrawable(Utils.coloringIcon(R.drawable.ic_password, "#FD9802"));
        ivPass.setBackgroundColor(Color.parseColor("#000000"));

        etUrl = (EditText) mView.findViewById(R.id.et_url);
        etUrl.setHint("Your Url");

        etUser = (EditText) mView.findViewById(R.id.et_user);
        etUser.setHint("Your Email");

        etPass = (EditText) mView.findViewById(R.id.et_password);
        etPass.setHint("Your Password");

        btLogin = (AppCompatButton) mView.findViewById(R.id.bt_login);
        Utils.initButton(btLogin, "LOGIN");

        btDemo = (AppCompatButton) mView.findViewById(R.id.bt_demo);
        Utils.initButton(btDemo, "TRY DEMO");

        tvOr = (TextView) mView.findViewById(R.id.tv_or);
        tvOr.setText("or");
        tvOr.setTextColor(Color.parseColor("#FFFFFF"));

        tvHelp = (TextView) mView.findViewById(R.id.tv_help);
        tvHelp.setText("Need Help?");
        tvHelp.setTextColor(Color.parseColor("#FFFFFF"));

    }

    @Override
    public void updateView(AppCollection collection) {

    }

    public void onTryDemoClick(View.OnClickListener listener) {
        btDemo.setOnClickListener(listener);
    }

}
