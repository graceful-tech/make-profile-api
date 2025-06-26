package com.make_profile.service.impl.forgotpassword;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.map.HashedMap;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.make_profile.configuration.PasswordEncryptor;
import com.make_profile.dto.EmailDto;
import com.make_profile.dto.password.PasswordResetTokenDto;
import com.make_profile.entity.password.PasswordResetTokenEntity;
import com.make_profile.entity.user.UserEntity;
import com.make_profile.exception.MakeProfileException;
import com.make_profile.repository.password.PasswordResetTokenRepository;
import com.make_profile.repository.user.UserRepository;
import com.make_profile.service.forgotpassword.ForgotPasswordService;
import com.make_profile.service.password.EmailService;
import com.make_profile.utility.CommonUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordServiceImpl.class);

	@Autowired
	Configuration configuration;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommonUtils commonUtils;

	@Autowired
	PasswordEncryptor passwordEncoder;

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PasswordResetTokenDto sendPasswordResetToken(PasswordResetTokenDto passwordResetTokenDto,
			HttpServletRequest request) throws Exception {
		logger.debug("Service :: sendPasswordResetToken :: Entered");
		PasswordResetTokenDto resetTokenDto = null;
		EmailDto emailDto = new EmailDto();
		List<String> toAddressList = new ArrayList<>();
		List<String> ccAddressList = new ArrayList<>();
		Map<String, String> variables = new HashedMap<>();
		Template template = null;
		try {

			UserEntity userEntity = userRepository.findByEmail(passwordResetTokenDto.getEmail());

			if (Objects.isNull(userEntity)) {
				throw new MakeProfileException("User not found");
			}
			String otp = commonUtils.generateOtp();
			PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
			passwordResetTokenEntity.setOtp(otp);
			passwordResetTokenEntity.setExpiryDate(LocalDateTime.now().plusMinutes(10));
			passwordResetTokenEntity.setUserId(userEntity);
			passwordResetTokenRepository.save(passwordResetTokenEntity);

//			String url = environmentRepository.getEnvironmentValueByKey("APP_URL");

			variables.put("recipientName", userEntity.getName());
			variables.put("otp", otp);
//			variables.put("resetUrl", url + "/#/reset-password?code=" + TenantContext.getCurrentTenant() + "&token="
//					+ passwordResetTokenEntity.getToken());

			template = configuration.getTemplate("reset_password_mail.ftl");

			String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);

			toAddressList.add(userEntity.getEmail());
			emailDto.setToAddressList(toAddressList);
			emailDto.setCcList(ccAddressList);
			emailDto.setSubject("Make Profiles - Reset Password");
			emailDto.setMessage(htmlBody);
			emailService.sendEmail(emailDto);

			resetTokenDto = modelMapper.map(passwordResetTokenEntity, PasswordResetTokenDto.class);
			otp = null;
		} catch (Exception e) {
			logger.error("Service :: sendPasswordResetToken :: Exception :: " + e);
			throw e;
		}
		logger.debug("Service :: sendPasswordResetToken :: Exited");
		return resetTokenDto;
	}

	@Override
	public boolean verifyOtp(PasswordResetTokenDto passwordResetTokenDto) {
		logger.debug("Service :: sendPasswordResetToken :: Entered");
		boolean status = false;
		UserEntity userEntity = null;

		userEntity = userRepository.findByEmail(passwordResetTokenDto.getEmail());
		try {
			PasswordResetTokenEntity byUserId = passwordResetTokenRepository.findLastUserId(userEntity.getId());
			if (byUserId.getOtp().equals(passwordResetTokenDto.getOtp())
					&& !byUserId.getExpiryDate().isBefore(LocalDateTime.now())) {
				status = true;
				passwordResetTokenRepository.deleteById(byUserId.getId());
			}
		} catch (Exception e) {
			logger.error("Service :: sendPasswordResetToken :: HurecomException :: " + e);
		}
		logger.debug("Service :: sendPasswordResetToken :: Exited");
		return status;
	}

	@Override
	public boolean updatPassword(PasswordResetTokenDto passwordResetTokenDto) {
		logger.debug("Service :: updatPassword :: Entered");
		boolean status = false;

		UserEntity userEntity = userRepository.findByEmail(passwordResetTokenDto.getEmail());

		if (userEntity != null) {
			userEntity.setPassword(passwordEncoder.encryptPassword(passwordResetTokenDto.getPassword()));
			userRepository.save(userEntity);
			status = true;
		}
		logger.debug("Service :: updatPassword :: Exited");
		return status;
	}

}
