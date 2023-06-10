package com.kms.booklet.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kms.booklet.Config;
import com.kms.booklet.DataRepository;
import com.kms.booklet.databinding.FragmentAccountBinding;
import com.kms.booklet.db.entity.User;
import com.kms.booklet.ui.LoginRegisterActivity;
import com.kms.booklet.ui.MainActivity;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        User user = DataRepository.getInstance(getActivity().getApplication()).getUserById(Config.username);
        binding.nicknameHolder.setText(user.getNickName());

        Button Logout = binding.logout;
        Button change_password = binding.changePassword;
        Button remove_account = binding.removeAccount;

        Logout.setOnClickListener(v -> {
            logout();
        });

        change_password.setOnClickListener(v -> {
            if(binding.passwordChange.getText() != binding.repeatPasswordChange.getText()){
                Toast.makeText(this.getContext(), "passwords aren't equal", Toast.LENGTH_LONG).show();
            }
            else if(binding.passwordChange.getText().length() > 30 || binding.passwordChange.getText().length() < 6){
                Toast.makeText(this.getContext(), "password length wrong", Toast.LENGTH_LONG).show();
                return;
            }
            DataRepository.getInstance(getActivity().getApplication()).changePassword(Config.username, change_password.getText().toString());
            Toast.makeText(this.getContext(), "password changed", Toast.LENGTH_LONG).show();
        });

        remove_account.setOnClickListener(v ->{
            DataRepository.getInstance(getActivity().getApplication()).removeAccount(Config.username);
            logout();
        });

        return root;
    }

    private void logout() {
        Config.username = "logged out";
        Intent intent = new Intent(this.getContext(), LoginRegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}