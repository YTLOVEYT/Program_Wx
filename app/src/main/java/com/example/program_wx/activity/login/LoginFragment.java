package com.example.program_wx.activity.login;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.program_wx.R;
import com.example.program_wx.activity.register.RegisterActivity;
import com.example.program_wx.utils.CommonUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginManager.View, View.OnClickListener
{
    private LoginManager.Presenter loginPresenter;
    private EditText etUserTel, etPassWord;
    private TextView tv_find_password, tv_country, tv_country_code;
    private Button btn_login, btn_goRegister;
    private RelativeLayout rl_country;
    private Dialog dialog;
    private boolean isAuth = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = CommonUtil.createLoadingDialog(getActivity(), "登录中。。。");
        // FIXME: 2017/12/7  isAuth 这个是干哈的
        isAuth = getActivity().getIntent().getBooleanExtra("isAuth", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        setListeners();
        return view;
    }

    /** 初始化控件 */
    private void initView(View view)
    {
        etUserTel = (EditText) view.findViewById(R.id.et_usertel);
        etPassWord = (EditText) view.findViewById(R.id.et_password);
        btn_login = (Button) view.findViewById(R.id.btn_login);
        tv_country = (TextView) view.findViewById(R.id.tv_country);
        tv_country_code = (TextView) view.findViewById(R.id.tv_country_code);
        tv_find_password = (TextView) view.findViewById(R.id.tv_find_password);
        rl_country = (RelativeLayout) view.findViewById(R.id.rl_country);
        btn_goRegister = (Button) view.findViewById(R.id.btn_goRegister);
    }

    /** 设置监听 */
    private void setListeners()
    {
        TextChange textChange = new TextChange();
        etUserTel.addTextChangedListener(textChange);
        etPassWord.addTextChangedListener(textChange);
        btn_login.setOnClickListener(this);
        rl_country.setOnClickListener(this);
        btn_goRegister.setOnClickListener(this);
        tv_find_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_login:
                String tel = etUserTel.getText().toString();
                String pwd = etPassWord.getText().toString();
                String country = tv_country.getText().toString().trim();
                String country_code = tv_country_code.getText().toString().trim();
                if (TextUtils.isEmpty(tel) || TextUtils.isEmpty(pwd))
                {
                    Toast.makeText(getContext(), "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("中国".equals(country) && "+86".equals(country_code))
                {
                    if (!CommonUtil.isMobile(tel))
                    {
                        Toast.makeText(getContext(), "非法手机号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                loginPresenter.requestServer(tel, pwd, isAuth);//登录
                break;
            case R.id.btn_goRegister:
                Toast.makeText(getContext(), "去注册", Toast.LENGTH_SHORT).show();
                getActivity().startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.rl_country:
                // FIXME: 2017/12/7 选择国家
                Toast.makeText(getContext(), "选择国家", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_find_password:
                // FIXME: 2017/12/7 找回密码
                Toast.makeText(getContext(), "找回密码", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /** 输入框监听 */
    private class TextChange implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            String tel = etUserTel.getText().toString();
            String pwd = etPassWord.getText().toString();
            if (tel.length() == 11)
            {
                if (CommonUtil.isMobile(tel))
                {
                    if (pwd.length() > 4)
                    {
                        btn_login.setEnabled(true);
                    }
                    else
                    {
                        btn_login.setEnabled(false);
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "非法手机号", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                btn_login.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {
        }
    }

    @Override
    public void setPresenter(LoginManager.Presenter presenter)
    {
        this.loginPresenter = presenter;
    }

    @Override
    public Activity getBaseActivity()
    {
        return getActivity();
    }

    @Override
    public void showDialog()
    {
        if (dialog != null)
        {
            dialog.show();
        }
    }

    @Override
    public void cancelDialog()
    {
        if (dialog != null && dialog.isShowing())
        {
            dialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg)
    {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loginPresenter.start();
    }
}
