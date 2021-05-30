package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IFragmentRouter {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    public void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    public void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.container, secondFragment)
                .commit();
    }


    // Переопределяем поведение хардверной кнопки "назад"
    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            // если в стэке окажется фрагмент с реализацией интерфейса, то
            // выполняем реализованый метод
            if (fragment instanceof OnBackListener) {
                ((OnBackListener) fragment).doBack();
                return;
            }
        }
        // в противном случае реализуем действие выхода
        finish();
    }
}
