package com.example.program_wx.activity.register;


import android.app.Activity;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.program_wx.R;
import com.example.program_wx.utils.CommonUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterManager.View, View.OnClickListener
{
    private RegisterManager.Presenter registerPresenter;
    private EditText etUserTel, etPassWord, etNick, etCode;
    private TextView tv_country, tv_country_code;
    private Button btn_register, btn_Code;
    private RelativeLayout rl_country;
    private ImageView ivV, ivH, ivHead;
    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = CommonUtil.createLoadingDialog(getActivity(), "注册中。。。");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        setListeners();
        return view;
    }

    /** 初始化控件 */
    private void initView(View view)
    {
        etNick = (EditText) view.findViewById(R.id.et_usernick);
        etUserTel = (EditText) view.findViewById(R.id.et_usertel);
        etPassWord = (EditText) view.findViewById(R.id.et_password);
        etCode = (EditText) view.findViewById(R.id.et_code);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_Code = (Button) view.findViewById(R.id.btn_code);
        tv_country = (TextView) view.findViewById(R.id.tv_country);
        tv_country_code = (TextView) view.findViewById(R.id.tv_country_code);
        rl_country = (RelativeLayout) view.findViewById(R.id.rl_country);
        ivV = (ImageView) view.findViewById(R.id.iv_show);
        ivH = (ImageView) view.findViewById(R.id.iv_hide);
        ivHead = (ImageView) view.findViewById(R.id.iv_photo);
    }

    /** 设置监听 */
    private void setListeners()
    {
        TextChange textChange = new TextChange();
        etUserTel.addTextChangedListener(textChange);
        etPassWord.addTextChangedListener(textChange);
        etCode.addTextChangedListener(textChange);
        btn_register.setOnClickListener(this);
        rl_country.setOnClickListener(this);
        btn_Code.setOnClickListener(this);
        ivH.setOnClickListener(this);
        ivV.setOnClickListener(this);
        ivHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_register:
                String tel = etUserTel.getText().toString();
                String pwd = etPassWord.getText().toString();
                String nickName = etNick.getText().toString();
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
                // FIXME: 2017/12/11 先验证验证码在注册
                registerPresenter.requestServer(tel, pwd, nickName);//注册
                break;
            case R.id.iv_photo:
                Toast.makeText(getContext(), "头像", Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_show:
                Toast.makeText(getContext(), "显示", Toast.LENGTH_SHORT).show();
                ivV.setVisibility(View.GONE);
                ivH.setVisibility(View.VISIBLE);
                // FIXME: 2017/12/11
                break;
            case R.id.iv_hide:
                Toast.makeText(getContext(), "隐藏", Toast.LENGTH_SHORT).show();
                ivH.setVisibility(View.GONE);
                ivV.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_code:
                Toast.makeText(getContext(), "发送验证码", Toast.LENGTH_SHORT).show();
                // FIXME: 2017/12/11 倒计时
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
            String code = etCode.getText().toString();
            if (tel.length() == 11)
            {
                if (CommonUtil.isMobile(tel))
                {
                    if (pwd.length() > 4 && code.length() >= 4)
                    {
                        btn_register.setEnabled(true);
                    }
                    else
                    {
                        btn_register.setEnabled(false);
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "非法手机号", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                btn_register.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {
        }
    }

    @Override
    public void setPresenter(RegisterManager.Presenter presenter)
    {
        this.registerPresenter = presenter;
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
}
