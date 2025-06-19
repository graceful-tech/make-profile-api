package com.make_profile.service.impl.password;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.make_profile.configuration.PasswordEncryptor;
import com.make_profile.dto.EmailDto;
import com.make_profile.dto.password.PasswordResetTokenDto;
import com.make_profile.dto.user.UserDto;
import com.make_profile.entity.password.PasswordResetTokenEntity;
import com.make_profile.entity.user.UserEntity;
import com.make_profile.exception.MakeProfileException;
import com.make_profile.repository.password.PasswordResetTokenRepository;
import com.make_profile.repository.user.UserRepository;
import com.make_profile.service.password.EmailService;
import com.make_profile.utility.CommonConstants;
import com.make_profile.utility.CommonUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	Configuration configuration;

	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public boolean sendEmail(EmailDto emailDto) throws Exception {
		logger.debug("Service :: sendEmail :: Entered");
		boolean status = false;
		List<MultipartFile> attachments = null;
		String[] to = null;
		String[] cc = null;
		String[] bcc = null;
		try {
			if (Objects.nonNull(emailDto.getToAddressList()) && !emailDto.getToAddressList().isEmpty()) {
				to = emailDto.getToAddressList().stream().toArray(String[]::new);
			}

			if (Objects.nonNull(emailDto.getCcList()) && !emailDto.getCcList().isEmpty()) {
				cc = emailDto.getCcList().stream().toArray(String[]::new);
			}

			if (Objects.nonNull(emailDto.getBccList()) && !emailDto.getBccList().isEmpty()) {
				bcc = emailDto.getBccList().stream().toArray(String[]::new);
			}

			attachments = emailDto.getAttachments();

			if (CollectionUtils.isEmpty(attachments)) {
				attachments = new ArrayList<>();
			}

			MimeMessage message = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			helper.setTo(to);
			helper.setCc(cc != null ? cc : new String[] {});
			helper.setBcc(bcc != null ? bcc : new String[] {});
			helper.setSubject(emailDto.getSubject());
			helper.setText(emailDto.getMessage(), true);

			if (!CollectionUtils.isEmpty(attachments)) {
				for (MultipartFile attachment : attachments) {
					helper.addAttachment(attachment.getOriginalFilename(), attachment);
				}
			}

			javaMailSender.send(message);
			status = true;
		} catch (Exception e) {
			logger.error("Service :: sendEmail :: Exception :: " + e);
			throw e;
		} finally {
			attachments.clear();
		}
		logger.debug("Service :: sendEmail :: Exited");
		return status;
	}

//	@Override
//	public boolean sendPasswordResetToken(PasswordResetTokenDto passwordResetTokenDto, HttpServletRequest request)
//			throws Exception {
//		logger.debug("Service :: sendPasswordResetToken :: Entered");
//		boolean status = false;
//		EmailDto emailDto = new EmailDto();
//		List<String> toAddressList = new ArrayList<>();
//		List<String> ccAddressList = new ArrayList<>();
//		Map<String, String> variables = new HashedMap<>();
//		Template template = null;
//		try {
//
//			UserEntity userEntity = userRepository.findByEmail(passwordResetTokenDto.getEmail());
//
//			if (Objects.isNull(userEntity)) {
//				throw new MakeProfileException(CommonConstants.MP_0001);
////				HM_0124
//			}
//			String otp = commonUtils.generateOtp();
//			PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
//			passwordResetTokenEntity.setOtp(otp);
//			passwordResetTokenEntity.setExpiryDate(LocalDateTime.now().plusMinutes(10));
//			passwordResetTokenEntity.setUser(userEntity);
//			passwordResetTokenRepository.save(passwordResetTokenEntity);
//
////			String url = environmentRepository.getEnvironmentValueByKey("APP_URL");
//
//			variables.put("recipientName", userEntity.getName());
//			variables.put("otp", otp);
////			variables.put("resetUrl", url + "/#/reset-password?code=" + TenantContext.getCurrentTenant() + "&token="
////					+ passwordResetTokenEntity.getToken());
//
//			template = configuration.getTemplate("reset_password_mail.ftl");
//
//			String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);
//
//			toAddressList.add(userEntity.getEmail());
//			emailDto.setToAddressList(toAddressList);
//			emailDto.setCcList(ccAddressList);
//			emailDto.setSubject("Make Profiles - Reset Password");
//			emailDto.setMessage(htmlBody);
////			sendEmail(emailDto);
//			status = true;
//			otp = null;
//		} catch (Exception e) {
//			logger.error("Service :: sendPasswordResetToken :: Exception :: " + e);
//			throw e;
//		}
//		logger.debug("Service :: sendPasswordResetToken :: Exited");
//		return status;
//	}
//
//	@Override
//	public boolean verifyOtp(PasswordResetTokenDto passwordResetTokenDto) {
//		logger.debug("Service :: sendPasswordResetToken :: Entered");
//		boolean status = false;
//		UserEntity userEntity = null;
//
//		userEntity = userRepository.findByEmail(passwordResetTokenDto.getEmail());
//		try {
//			PasswordResetTokenEntity byUserId = passwordResetTokenRepository.findLastUserId(userEntity.getId());
//			if (byUserId.getOtp().equals(passwordResetTokenDto.getOtp())
//					&& !byUserId.getExpiryDate().isBefore(LocalDateTime.now())) {
//				status = true;
//				passwordResetTokenRepository.deleteById(byUserId.getId());
//			}
//		} catch (Exception e) {
//			logger.error("Service :: sendPasswordResetToken :: HurecomException :: " + e);
//		}
//		logger.debug("Service :: sendPasswordResetToken :: Exited");
//		return status;
//	}
//
//	@Override
//	public boolean updatPassword(PasswordResetTokenDto passwordResetTokenDto) {
//		logger.debug("Service :: updatPassword :: Entered");
//		boolean status = false;
//
//		UserEntity userEntity = userRepository.findByEmail(passwordResetTokenDto.getEmail());
//
//		if (userEntity != null) {
//			userEntity.setPassword(passwordEncoder.encryptPassword(passwordResetTokenDto.getPassword()));
//			userRepository.save(userEntity);
//			status = true;
//		}
//		logger.debug("Service :: updatPassword :: Exited");
//		return status;
//	}

}