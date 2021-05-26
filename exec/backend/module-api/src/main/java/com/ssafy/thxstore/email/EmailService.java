package com.ssafy.thxstore.email;

import com.ssafy.thxstore.common.exceptions.AuthException;
import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.member.dto.response.CheckEmailResponse;
import com.ssafy.thxstore.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender emailSender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //TODO 인증코드 저장 하는 곳 생각해보기
    public static final String ePw = createKey();

    private MimeMessage createMessage(String to)throws Exception{
        logger.info("보내는 대상 : "+ to);
        logger.info("인증 번호 : " + ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        String code = createCode(ePw);
        message.addRecipients(MimeMessage.RecipientType.TO, to); //보내는 대상
        message.setSubject("THXSTORE 확인 코드: " + code); //제목

        String msg="";
        msg += "<img width=\"120\" height=\"36\" style=\"margin-top: 0; margin-right: 0; margin-bottom: 32px; margin-left: 0px; padding-right: 30px; padding-left: 30px;\" src=\"https://user-images.githubusercontent.com/70404643/118411541-0fd4f700-b6d0-11eb-802f-6b66ffe69f5d.png\" alt=\"\" loading=\"lazy\">";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 THXSTORE의 이메일 인증번호 입력창이 있는 브라우저 창에 입력하세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += code;
        msg += "</td></tr></tbody></table></div>";
        msg += "<a href=\"https://k4b202.p.ssafy.io/\" style=\"text-decoration: none; color: #434245;\" rel=\"noreferrer noopener\" target=\"_blank\">THXSTORE Technologies, Inc</a>";

        message.setText(msg, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("thxstore.help@gmail.com","THXSTORE")); //보내는 사람

        return message;
    }


    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    public CheckEmailResponse sendSimpleMessage(String to)throws Exception {
        if (!memberRepository.existsByEmail(to)) {
            throw new AuthException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
        MimeMessage message = createMessage(to);
        try{
            emailSender.send(message);
            return CheckEmailResponse.of(true);
        }catch(MailException es){
            es.printStackTrace();
            return CheckEmailResponse.of(false);
        }
    }

    public String createCode(String ePw){
        return ePw.substring(0, 3) + "-" + ePw.substring(3, 6);
    }
}
