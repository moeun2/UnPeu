package com.unpeu.api.mail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api("Mail 관련 기능")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MailController {
	public static final Logger logger = LoggerFactory.getLogger(MailController.class);

	@Value("${spring.mail.username}")
	private String TO_EMAIL;
	private String FROM_EMAIL = "mo_ah@naver.com";

	private final JavaMailSender mailSender;

	@ApiOperation(value = "메일 보내기 Controller")
	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createMail(@RequestBody String text) {
		logger.info("createMail - 호출");
		System.out.println(text);
		Map<String, Object> resultMap = new HashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String now_dt = format.format(now);
		String title = now_dt + "_Unpeu_Feedback";
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
			mimeMessageHelper.setFrom(TO_EMAIL);
			mimeMessageHelper.setTo(FROM_EMAIL);
			mimeMessageHelper.setSubject(title);
			mimeMessageHelper.setText(text);
			mailSender.send(mimeMessage);
			resultMap.put("Message", "send Mail Success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultMap.put("Message", "send Mail Failure");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}
}
