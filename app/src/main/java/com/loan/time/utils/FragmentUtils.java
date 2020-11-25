package com.loan.time.utils;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentUtils {

    private static final int TYPE_ADD_FRAGMENT = 0x01;
    private static final int TYPE_REMOVE_FRAGMENT = 0x01 << 1;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 2;
    private static final int TYPE_REPLACE_FRAGMENT = 0x01 << 3;
    private static final int TYPE_POP_ADD_FRAGMENT = 0x01 << 4;
    private static final int TYPE_HIDE_FRAGMENT = 0x01 << 5;
    private static final int TYPE_SHOW_FRAGMENT = 0x01 << 6;
    private static final int TYPE_HIDE_SHOW_FRAGMENT = 0x01 << 7;

    private static final String ARGS_ID = "args_id";
    private static final String ARGS_IS_HIDE = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";

    /**
     * 新增fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       int containerId) {
        return addFragment(fragmentManager, fragment, containerId, false);
    }

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isHide          是否显示
     * @return fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       int containerId,
                                       boolean isHide) {
        return addFragment(fragmentManager, fragment, containerId, isHide, false);
    }

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isHide          是否显示
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       int containerId,
                                       boolean isHide,
                                       boolean isAddStack) {
        putArgs(fragment, new Args(containerId, isHide, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT);
    }

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isAddStack      是否入回退栈
     * @param sharedElement   共享元素
     * @return fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       int containerId,
                                       boolean isAddStack,
                                       SharedElement... sharedElement) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT, sharedElement);
    }

    /*新增多个fragment
        fragmentManager fragment管理器
        fragments       fragments
        showIndex       要显示的fragment索引
        containerId     布局Id
        return fragment*/
    public static Fragment addFragments(@NonNull FragmentManager fragmentManager,
                                        @NonNull List<Fragment> fragments,
                                        int showIndex,
                                        int containerId) {
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                putArgs(fragment, new Args(containerId, showIndex != i, false));
                operateFragment(fragmentManager, null, fragment, TYPE_ADD_FRAGMENT);
            }
        }
        return fragments.get(showIndex);
    }

    /**
     * 移除fragment
     *
     * @param fragment fragment
     */
    public static void removeFragment(@NonNull Fragment fragment) {
        operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_REMOVE_FRAGMENT);
    }

    /**
     * 移除到指定fragment
     *
     * @param fragment      fragment
     * @param isIncludeSelf 是否包括Fragment类自己
     */
    public static void removeToFragment(@NonNull Fragment fragment, boolean isIncludeSelf) {
        operateFragment(fragment.getFragmentManager(), isIncludeSelf ? fragment : null, fragment, TYPE_REMOVE_TO_FRAGMENT);
    }

    /**
     * 移除同级别fragment
     */
    public static void removeFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) removeFragment(fragment);
        }
    }

    /**
     * 移除所有fragment
     */
    public static void removeAllFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                removeAllFragments(fragment.getChildFragmentManager());
                removeFragment(fragment);
            }
        }
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     * @param isAddStack   是否入回退栈
     * @return 目标fragment
     */
    public static Fragment replaceFragment(@NonNull Fragment srcFragment,
                                           @NonNull Fragment destFragment,
                                           boolean isAddStack) {
        if (srcFragment.getArguments() == null) return null;
        int containerId = srcFragment.getArguments().getInt(ARGS_ID);
        if (containerId == 0) return null;
        return replaceFragment(srcFragment.getFragmentManager(), containerId, destFragment, isAddStack);
    }

    /**
     * 替换fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment replaceFragment(@NonNull FragmentManager fragmentManager,
                                           int containerId,
                                           @NonNull Fragment fragment,
                                           boolean isAddStack) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_REPLACE_FRAGMENT);
    }

    /**
     * 出栈fragment
     *
     * @param fragmentManager fragment管理器
     * @return {@code true}: 出栈成功<br>{@code false}: 出栈失败
     */
    public static boolean popFragment(@NonNull FragmentManager fragmentManager) {
        return fragmentManager.popBackStackImmediate();
    }

    /**
     * 出栈到指定fragment
     *
     * @param fragmentManager fragment管理器
     * @param fragmentClass   Fragment类
     * @param isIncludeSelf   是否包括Fragment类自己
     * @return {@code true}: 出栈成功<br>{@code false}: 出栈失败
     */
    public static boolean popToFragment(@NonNull FragmentManager fragmentManager,
                                        Class<? extends Fragment> fragmentClass,
                                        boolean isIncludeSelf) {
        return fragmentManager.popBackStackImmediate(fragmentClass.getName(), isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
    }

    /**
     * 出栈同级别fragment
     *
     * @param fragmentManager fragment管理器
     */
    public static void popFragments(@NonNull FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    /**
     * 出栈所有fragment
     *
     * @param fragmentManager fragment管理器
     */
    public static void popAllFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) popAllFragments(fragment.getChildFragmentManager());
        }
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    /**
     * 先出栈后新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment popAddFragment(@NonNull FragmentManager fragmentManager,
                                          int containerId,
                                          @NonNull Fragment fragment,
                                          boolean isAddStack,
                                          SharedElement... sharedElement) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_POP_ADD_FRAGMENT, sharedElement);
    }

    /**
     * 先出栈后新增fragment
     * <p>
     * fragmentManager fragment管理器
     * containerId     布局Id
     * fragment        fragment
     * isAddStack      是否入回退栈
     * return fragment
     */
    public static Fragment popAddFragment(@NonNull FragmentManager fragmentManager,
                                          int containerId,
                                          @NonNull Fragment fragment,
                                          boolean isAddStack) {
        putArgs(fragment, new Args(containerId, false, isAddStack));
        return operateFragment(fragmentManager, null, fragment, TYPE_POP_ADD_FRAGMENT);
    }

    /**
     * 隐藏fragment
     * <p>
     * fragment fragment
     * return 隐藏的Fragment
     */
    public static Fragment hideFragment(@NonNull Fragment fragment) {
        Args args = getArgs(fragment);
        if (args != null) {
            putArgs(fragment, new Args(args.id, true, args.isAddStack));
        }
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_HIDE_FRAGMENT);
    }

    /**
     * 隐藏同级别fragment
     * <p>
     * fragmentManager fragment管理器
     */
    public static void hideFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) hideFragment(fragment);
        }
    }

    /**
     * 显示fragment
     * <p>
     * fragment fragment
     * return show的Fragment
     */
    public static Fragment showFragment(@NonNull Fragment fragment) {
        Args args = getArgs(fragment);
        if (args != null) {
            putArgs(fragment, new Args(args.id, false, args.isAddStack));
        }
        return operateFragment(fragment.getFragmentManager(), null, fragment, TYPE_SHOW_FRAGMENT);
    }

    /**
     * 先隐藏后显示fragment
     * <p>
     * hideFragment 需要隐藏的Fragment
     * showFragment 需要显示的Fragment
     * return 显示的Fragment
     */
    public static Fragment hideShowFragment(@NonNull Fragment hideFragment,
                                            @NonNull Fragment showFragment) {
        Args args = getArgs(hideFragment);
        if (args != null) {
            putArgs(hideFragment, new Args(args.id, true, args.isAddStack));
        }
        args = getArgs(showFragment);
        if (args != null) {
            putArgs(showFragment, new Args(args.id, false, args.isAddStack));
        }
        return operateFragment(showFragment.getFragmentManager(), hideFragment, showFragment, TYPE_HIDE_SHOW_FRAGMENT);
    }

    /**
     * 传参
     * <p>
     * fragment fragment
     * param args     参数
     */
    private static void putArgs(@NonNull Fragment fragment, Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    /**
     * 获取参数
     * <p>
     * fragment fragment
     */
    private static Args getArgs(@NonNull Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null || bundle.getInt(ARGS_ID) == 0) return null;
        return new Args(bundle.getInt(ARGS_ID), bundle.getBoolean(ARGS_IS_HIDE), bundle.getBoolean(ARGS_IS_ADD_STACK));
    }

    /**
     * 操作fragment
     * <p>
     * fragmentManager fragment管理器
     * srcFragment     源fragment
     * destFragment    目标fragment
     * type            操作类型
     * sharedElements  共享元素
     * return destFragment
     */
    private static Fragment operateFragment(@NonNull FragmentManager fragmentManager,
                                            Fragment srcFragment,
                                            @NonNull Fragment destFragment,
                                            int type,
                                            SharedElement... sharedElements) {
        if (srcFragment == destFragment) return null;
        if (srcFragment != null && srcFragment.isRemoving()) {
            Log.e("TAG", srcFragment.getClass().getName() + " is isRemoving");
            return null;
        }
        String name = destFragment.getClass().getName();
        Bundle args = destFragment.getArguments();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (sharedElements == null || sharedElements.length == 0) {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        } else {
            for (SharedElement element : sharedElements) {
                ft.addSharedElement(element.sharedElement, element.name);
            }
        }
        switch (type) {
            case TYPE_ADD_FRAGMENT:
                ft.add(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_HIDE)) ft.hide(destFragment);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_REMOVE_FRAGMENT:
                ft.remove(destFragment);
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                List<Fragment> fragments = getFragments(fragmentManager);
                for (int i = fragments.size() - 1; i >= 0; --i) {
                    Fragment fragment = fragments.get(i);
                    if (fragment == destFragment) {
                        if (srcFragment != null) ft.remove(fragment);
                        break;
                    }
                    ft.remove(fragment);
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                ft.replace(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_POP_ADD_FRAGMENT:
                popFragment(fragmentManager);
                ft.add(args.getInt(ARGS_ID), destFragment, name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_HIDE_FRAGMENT:
                ft.hide(destFragment);
                break;
            case TYPE_SHOW_FRAGMENT:
                ft.show(destFragment);
                break;
            case TYPE_HIDE_SHOW_FRAGMENT:
                ft.hide(srcFragment).show(destFragment);
                break;
        }
        ft.commitAllowingStateLoss();
        return destFragment;
    }

    /**
     * 获取同级别最后加入的fragment
     *
     * @param fragmentManager fragment管理器
     * @return 最后加入的fragment
     */
    public static Fragment getLastAddFragment(@NonNull FragmentManager fragmentManager) {
        return getLastAddFragmentIsInStack(fragmentManager, false);
    }

    /**
     * 获取栈中同级别最后加入的fragment
     *
     * @param fragmentManager fragment管理器
     * @return 最后加入的fragment
     */
    public static Fragment getLastAddFragmentInStack(@NonNull FragmentManager fragmentManager) {
        return getLastAddFragmentIsInStack(fragmentManager, true);
    }

    /**
     * 根据栈参数获取同级别最后加入的fragment
     *
     * @param fragmentManager fragment管理器
     * @param isInStack       是否是栈中的
     * @return 栈中最后加入的fragment
     */
    private static Fragment getLastAddFragmentIsInStack(@NonNull FragmentManager fragmentManager,
                                                        boolean isInStack) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return null;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return fragment;
                    }
                } else {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * 获取顶层可见fragment
     *
     * @param fragmentManager fragment管理器
     * @return 顶层可见fragment
     */
    public static Fragment getTopShowFragment(@NonNull FragmentManager fragmentManager) {
        return getTopShowFragmentIsInStack(fragmentManager, null, false);
    }

    /**
     * 获取栈中顶层可见fragment
     *
     * @param fragmentManager fragment管理器
     * @return 栈中顶层可见fragment
     */
    public static Fragment getTopShowFragmentInStack(@NonNull FragmentManager fragmentManager) {
        return getTopShowFragmentIsInStack(fragmentManager, null, true);
    }

    /**
     * 根据栈参数获取顶层可见fragment
     *
     * @param fragmentManager fragment管理器
     * @param parentFragment  父fragment
     * @param isInStack       是否是栈中的
     * @return 栈中顶层可见fragment
     */
    private static Fragment getTopShowFragmentIsInStack(@NonNull FragmentManager fragmentManager,
                                                        Fragment parentFragment,
                                                        boolean isInStack) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return parentFragment;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isResumed() && fragment.isVisible() && fragment.getUserVisibleHint()) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return getTopShowFragmentIsInStack(fragment.getChildFragmentManager(), fragment, true);
                    }
                } else {
                    return getTopShowFragmentIsInStack(fragment.getChildFragmentManager(), fragment, false);
                }
            }
        }
        return parentFragment;
    }

    /**
     * 获取同级别fragment
     *
     *
     * @param fragmentManager  fragment管理器
     * @return同级别的fragments
     */
    public static List<Fragment> getFragments(@NonNull FragmentManager fragmentManager) {
        return getFragmentsIsInStack(fragmentManager, false);
    }

    /**
     * 获取栈中同级别fragment
     *
     * @param fragmentManager fragment管理器
     * @return 栈中同级别fragment
     */
    public static List<Fragment> getFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        return getFragmentsIsInStack(fragmentManager, true);
    }

    /**
     * 根据栈参数获取同级别fragment
     *
     * @param fragmentManager fragment管理器
     * @param isInStack       是否是栈中的
     * @return 栈中同级别fragment
     */
    @SuppressLint("RestrictedApi")
    private static List<Fragment> getFragmentsIsInStack(@NonNull FragmentManager fragmentManager, boolean isInStack) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty()) return Collections.emptyList();
        List<Fragment> result = new ArrayList<>();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        result.add(fragment);
                    }
                } else {
                    result.add(fragment);
                }
            }
        }
        return result;
    }

    /**
     * 获取所有fragment
     *
     * @param fragmentManager fragment管理器
     * @return 所有fragment
     */
    public static List<FragmentNode> getAllFragments(@NonNull FragmentManager fragmentManager) {
        return getAllFragmentsIsInStack(fragmentManager, new ArrayList<FragmentNode>(), false);
    }

    /**
     * 获取栈中所有fragment
     *
     * @param fragmentManager fragment管理器
     * @return 所有fragment
     */
    public static List<FragmentNode> getAllFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        return getAllFragmentsIsInStack(fragmentManager, new ArrayList<FragmentNode>(), true);
    }

    /**
     * 根据栈参数获取所有fragment
     * <p>需之前对fragment的操作都借助该工具类</p>
     *
     * @param fragmentManager fragment管理器
     * @param result          结果
     * @param isInStack       是否是栈中的
     * @return 栈中所有fragment
     */
    @SuppressLint("RestrictedApi")
    private static List<FragmentNode> getAllFragmentsIsInStack(@NonNull FragmentManager fragmentManager,
                                                               List<FragmentNode> result,
                                                               boolean isInStack) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty()) return Collections.emptyList();
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        result.add(new FragmentNode(fragment, getAllFragmentsIsInStack(fragment.getChildFragmentManager(), new ArrayList<FragmentNode>(), true)));
                    }
                } else {
                    result.add(new FragmentNode(fragment, getAllFragmentsIsInStack(fragment.getChildFragmentManager(), new ArrayList<FragmentNode>(), false)));
                }
            }
        }
        return result;
    }

    /**
     * 获取目标fragment的前一个fragment
     *
     * @param destFragment 目标fragment
     * @return 目标fragment的前一个fragment
     */
    public static Fragment getPreFragment(@NonNull Fragment destFragment) {
        FragmentManager fragmentManager = destFragment.getFragmentManager();
        if (fragmentManager == null) return null;
        List<Fragment> fragments = getFragments(fragmentManager);
        boolean flag = false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (flag && fragment != null) {
                return fragment;
            }
            if (fragment == destFragment) {
                flag = true;
            }
        }
        return null;
    }

    /**
     * 查找fragment
     *
     * @param fragmentManager fragment管理器
     * @param fragmentClass   fragment类
     * @return 查找到的fragment
     */
    public static Fragment findFragment(@NonNull FragmentManager fragmentManager, Class<? extends Fragment> fragmentClass) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments.isEmpty()) return null;
        return fragmentManager.findFragmentByTag(fragmentClass.getName());
    }

    /**
     * 处理fragment回退键
     * <p>如果fragment实现了OnBackClickListener接口，返回{@code true}: 表示已消费回退键事件，反之则没消费</p>
     * <p>具体示例见FragmentActivity</p>
     *
     * @param fragment fragment
     * @return 是否消费回退事件
     */

    public static boolean dispatchBackPress(@NonNull Fragment fragment) {
        return dispatchBackPress(fragment.getFragmentManager());
    }

    /**
     * 处理fragment回退键
     * <p>如果fragment实现了OnBackClickListener接口，返回{@code true}: 表示已消费回退键事件，反之则没消费</p>
     * <p>具体示例见FragmentActivity</p>
     *
     * @param fragmentManager fragment管理器
     * @return 是否消费回退事件
     */
    @SuppressLint("RestrictedApi")
    public static boolean dispatchBackPress(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty()) return false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置背景色
     *
     * @param fragment fragment
     * @param color    背景色
     */
    public static void setBackgroundColor(@NonNull Fragment fragment, @ColorInt int color) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    /**
     * 设置背景资源
     *
     * @param fragment fragment
     * @param resId    资源Id
     */
    public static void setBackgroundResource(@NonNull Fragment fragment, @DrawableRes int resId) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundResource(resId);
        }
    }

    /**
     * 设置背景
     *
     * @param fragment   fragment
     * @param background 背景
     */
    //在API16之后，Android系统由setBackground方法替代了setBackgroundDrawable方法
    public static void setBackground(@NonNull Fragment fragment, Drawable background) {
        View view = fragment.getView();
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(background);
            } else {
             //  view.setBackgroundDrawable(background);
            }
        }
    }

    //
    static class Args {
        int id;
        boolean isHide;
        boolean isAddStack;

        Args(int id, boolean isHide, boolean isAddStack) {
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }

    // 添加共享元素动画
    public static class SharedElement {
        View sharedElement;
        String name;
        public SharedElement(View sharedElement, String name) {
            this.sharedElement = sharedElement;
            this.name = name;
        }
    }
    static class FragmentNode {
        Fragment fragment;
        List<FragmentNode> next;
        public FragmentNode(Fragment fragment, List<FragmentNode> next) {
            this.fragment = fragment;
            this.next = next;
        }
        @Override
        public String toString() {
            return fragment.getClass().getSimpleName() + "->" + ((next == null || next.isEmpty()) ? "no child" : next.toString());
        }
    }
    public interface OnBackClickListener {
        boolean onBackClick();
    }
}
