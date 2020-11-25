package com.loan.time.api;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loan.time.App;
import com.loan.time.R;
import com.loan.time.utils.EmptyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhoneCode extends RelativeLayout {
    private Context context;
    private TextView tv_code1;
    private TextView tv_code2;
    private TextView tv_code3;
    private TextView tv_code4;
    private EditText et_code;
    private View view1_ind;
    private View view2_ind;
    private View view3_ind;
    private View view4_ind;
    private List<String> codes = new ArrayList<>();
    private InputMethodManager imm;

    public PhoneCode(Context context) {
        super(context);
        this.context = context;
        loadView();
    }

    public PhoneCode(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadView();
    }

    private void loadView() {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.phone_code, this);
        initView(view);
        initEvent();
    }

    private void initView(View view) {
        tv_code1 = (TextView) view.findViewById(R.id.tv_code1);
        tv_code2 = (TextView) view.findViewById(R.id.tv_code2);
        tv_code3 = (TextView) view.findViewById(R.id.tv_code3);
        tv_code4 = (TextView) view.findViewById(R.id.tv_code4);
        view1_ind=(View) view.findViewById(R.id.view1_ind);
        view2_ind=(View) view.findViewById(R.id.view2_ind);
        view3_ind=(View) view.findViewById(R.id.view3_ind);
        view4_ind=(View) view.findViewById(R.id.view4_ind);
        et_code = (EditText) view.findViewById(R.id.et_code);
        et_code.requestFocus();
        et_code.setLongClickable(true);//支持长按
    }

    private void initEvent() {
        //验证码输入
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0) {
                    et_code.setText("");
                    if (codes.size() < 4) {
                        String data = editable.toString().trim();
                        if (data.length() >= 4) {
                            //将string转换成List
                            List<String> list = Arrays.asList(data.split(""));
                            //Arrays.asList没有实现add和remove方法，继承的AbstractList，需要将list放进java.util.ArrayList中
                            codes = new ArrayList<>(list);
                            if (EmptyUtils.isNotEmpty(codes) && codes.size() > 6) {
                                //使用data.split("")方法会将""放进第一下标里需要去掉
                                codes.remove(0);
                            }
                        } else {
                            codes.add(data);
                        }
                        showCode();
                    }
                }
            }
        });
        // 监听验证码删除按键
        et_code.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (EmptyUtils.isNotEmpty(codes)) {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && codes.size() > 0) {
                    codes.remove(codes.size() - 1);
                    showCode();
                    return true;
                }
            }
            return false;
        });
    }

    /**
     * 显示输入的验证码
     */
    private void showCode() {
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        if (codes.size()<=0){
            view1_ind.setVisibility(View.VISIBLE);
            view2_ind.setVisibility(View.GONE);
            view3_ind.setVisibility(View.GONE);
            view4_ind.setVisibility(View.GONE);
        }
        if (codes.size() >= 1) {
            code1 = codes.get(0);
            view1_ind.setVisibility(View.GONE);
            view2_ind.setVisibility(View.VISIBLE);
            view3_ind.setVisibility(View.GONE);
            view4_ind.setVisibility(View.GONE);
        }
        if (codes.size() >= 2) {
            code2 = codes.get(1);
            view1_ind.setVisibility(View.GONE);
            view2_ind.setVisibility(View.GONE);
            view3_ind.setVisibility(View.VISIBLE);
            view4_ind.setVisibility(View.GONE);
        }
        if (codes.size() >= 3) {
            code3 = codes.get(2);
            view1_ind.setVisibility(View.GONE);
            view2_ind.setVisibility(View.GONE);
            view3_ind.setVisibility(View.GONE);
            view4_ind.setVisibility(View.VISIBLE);
        }
        if (codes.size() >= 4) {
            code4 = codes.get(3);
            view1_ind.setVisibility(View.GONE);
            view2_ind.setVisibility(View.GONE);
            view3_ind.setVisibility(View.GONE);
            view4_ind.setVisibility(View.GONE);
        }
        tv_code1.setText(code1);
        tv_code2.setText(code2);
        tv_code3.setText(code3);
        tv_code4.setText(code4);
        callBack();//回调
    }

    /**
     * 显示输入的验证码
     */
    public void showEmptyCode() {
        tv_code1.setText("");
        tv_code2.setText("");
        tv_code3.setText("");
        tv_code4.setText("");
        codes.clear();
        et_code.setText("");
    }


    /**
     * 回调
     */
    private void callBack() {
        if (onInputListener == null) {
            return;
        }
        if (codes.size() == 4) {
            onInputListener.onSucess(getPhoneCode());
        } else {
            onInputListener.onInput();
        }
    }

    //定义回调
    public interface OnInputListener {
        void onSucess(String codes);

        void onInput();
    }
    private OnInputListener onInputListener;
    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    /**
     * 显示键盘
     */
    public void showSoftInput() {
        //显示软键盘
        if (imm != null && et_code != null) {
            et_code.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imm.showSoftInput(et_code, 0);
                }
            }, 1500);
        }
    }

    /**
     * 获得手机号验证码
     *
     * @return 验证码
     */
    public String getPhoneCode() {
        StringBuilder sb = new StringBuilder();
        for (String codes : codes) {
            sb.append(codes);
        }
        return sb.toString();
    }

    public  String getTextFromText() {
        ClipboardManager clipboard = (ClipboardManager) App.context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0 && EmptyUtils.isNotEmpty(clip.getItemAt(0).getText())) {
            return clip.getItemAt(0).getText().toString();
        }
        return null;
    }
}
