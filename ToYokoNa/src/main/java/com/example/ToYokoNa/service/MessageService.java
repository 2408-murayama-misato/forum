package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.MessageForm;
import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.controller.form.UserMessageForm;
import com.example.ToYokoNa.repository.BranchRepository;
import com.example.ToYokoNa.repository.CommentRepository;
import com.example.ToYokoNa.repository.MessageRepository;
import com.example.ToYokoNa.repository.UserRepository;
import com.example.ToYokoNa.repository.entity.Comment;
import com.example.ToYokoNa.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    UserRepository userRepository;

    /*
    全投稿取得処理
     */
    public Page<UserMessageForm> findALLMessages(String startDate, String endDate, String category, Pageable pageable) throws ParseException {
//        絞込日付作成処理
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat noTime = new SimpleDateFormat("yyyy-MM-dd");
        if (isBlank(startDate)) {
            startDate = "2022-01-01 00:00:00";
        } else {
            startDate = startDate + " 00:00:00";
        }
        if (isBlank(endDate)) {
            endDate = noTime.format(new Date()) + " 23:59:59";
        } else {
            endDate = endDate + " 23:59:59";
        }
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);

        // 投稿からの差分計算のため現在時刻を渡す
        LocalDateTime now = LocalDateTime.now();
        if (isBlank(category)) {
//            カテゴリー情報なし投稿取得処理
            Page<Message> results = messageRepository.findAllByOrderByCreateDateDesc(start, end, pageable);
            Page<UserMessageForm> messages = setUserMessageForm(results, pageable, now);
            return messages;
        } else {
//            カテゴリー情報あり投稿取得処理
            Page<Message> results = messageRepository.findAllByWHERECategoryOrderByCreateDateDesc(start, end, category, pageable);
            Page<UserMessageForm> messages = setUserMessageForm(results, pageable, now);
            return messages;
        }
    }

    private Page<UserMessageForm> setUserMessageForm(Page<Message> results, Pageable pageable, LocalDateTime now) {
        List<UserMessageForm> userMessageForms = new ArrayList<>();
        for (Message message : results.getContent()) {
            UserMessageForm userMessageForm = new UserMessageForm();
            userMessageForm.setId(message.getId());
            userMessageForm.setText(message.getText());
            userMessageForm.setTitle(message.getTitle());
            userMessageForm.setUserId(message.getUserId());
            userMessageForm.setUserName(message.getUser().getName());
            userMessageForm.setCreatedDate(message.getCreatedDate());
            userMessageForm.setUpdatedDate(message.getUpdatedDate());
            userMessageForm.setCategory(message.getCategory());
            userMessageForm.setDepartmentId(message.getUser().getDepartmentId());
            userMessageForm.setBranchId(message.getUser().getBranchId());
            // DateからLocalDateTimeに変換する場合Date→Instant→LocalDateTimeという流れになる
            Instant instant = message.getUpdatedDate().toInstant();
            LocalDateTime t1 = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            // QA表URL参考
            userMessageForm.setSeconds(ChronoUnit.SECONDS.between(t1, now));
            userMessageForm.setMinutes(ChronoUnit.MINUTES.between(t1, now));
            userMessageForm.setHours(ChronoUnit.HOURS.between(t1, now));
            // 正確な日付の計算はPeriodクラスを使用する
            Period period = Period.between(t1.toLocalDate(), now.toLocalDate());
            if (period.getMonths() >= 1 || period.getYears() >= 1) { // 1か月以上経過している場合(年越しまたいでる場合も考慮)
                userMessageForm.setMonths(period.getMonths() + (period.getYears() * 12));
            } else { // １か月未満の場合
                userMessageForm.setDays(ChronoUnit.DAYS.between(t1, now));
            }
            userMessageForm.setYears(period.getYears());
            userMessageForms.add(userMessageForm);
        }
        // PageImpl<>は引数に
        // (ページのコンテンツ, ページング情報, 利用可能なアイテムの合計数(long型なのでPage型.getTotalElements()を使う))を設定する。
        // ただのリスト型にページング情報を足してPage<T>として扱うためのクラス。
        return new PageImpl<>(userMessageForms, pageable, results.getTotalElements());
    }
    /*
    投稿追加処理
     */
    @Transactional
    public void save(MessageForm messageForm, UserForm loginUser) {
        messageForm.setUserId(loginUser.getId());
        Message message = setMessage(messageForm);
        messageRepository.save(message);
        userRepository.countUpMessage(loginUser.getId());
        branchRepository.countUpMessage(loginUser.getBranchId());
    }

    private Message setMessage(MessageForm messageForm) {
        Message message = new Message();
        message.setId(messageForm.getId());
        message.setTitle(messageForm.getTitle());
        message.setText(messageForm.getText());
        message.setCategory(messageForm.getCategory());
        message.setUserId(messageForm.getUserId());
        return message;
    }
    /*
    投稿取得処理
     */
    public MessageForm findMessage(int id) {
        Message result = messageRepository.findById(id).orElse(null);
        MessageForm messageForm = setMessageForm(result);
        return messageForm;
    }

    private MessageForm setMessageForm(Message result) {
        MessageForm messageForm = new MessageForm();
        messageForm.setId(result.getId());
        messageForm.setTitle(result.getTitle());
        messageForm.setText(result.getText());
        messageForm.setCategory(result.getCategory());
        messageForm.setUserId(result.getUserId());
        messageForm.setCreatedDate(result.getCreatedDate());
        messageForm.setUpdatedDate(result.getUpdatedDate());
        return messageForm;
    }

    /*
    投稿削除処理
     */
    @Transactional
    public void deleteMessage(int id, int messageUserId, int messageBranch) {
        messageRepository.deleteById(id);
        userRepository.countDownMessage(messageUserId);
        branchRepository.countDownMessage(messageBranch);
        List<Comment> comments = commentRepository.findByMessageId(id);
        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                commentService.deleteComment(comment.getId(), comment.getUserId());
            }
        }
    }
}
