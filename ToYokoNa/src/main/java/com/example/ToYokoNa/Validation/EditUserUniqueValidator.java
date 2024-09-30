package com.example.ToYokoNa.Validation;

import com.example.ToYokoNa.repository.UserRepository;
import com.example.ToYokoNa.repository.entity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EditUserUniqueValidator implements ConstraintValidator<EditUserUnique, String> {
    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(EditUserUnique annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //valueには、アノテーションを付与したフィールドの項目値が設定される
        //value = account　のこと
        //Repositoryでaccountを引数にDBを検索し、2つ以上(自アカウントのデータを含めるため2件)データが見つからなければ一意とする
        List<User> users = userRepository.findAllByAccount(value);
        if (users.size() == 1) {
            return true;
        }
        return false;
    }
}
