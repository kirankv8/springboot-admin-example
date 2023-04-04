package com.admin.application.otp;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class OtpGenarator {

	private final static Integer expTime = 2;

	private LoadingCache<String, Integer> otpCache;
	SecureRandom random = new SecureRandom();

	public OtpGenarator() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(expTime, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {

					@Override
					public Integer load(String key) throws Exception {

						return 0;
					}

				});
	}

	public Integer otp(String email) {
		char[] otp = new char[6];
		String numbers = "0123456789";
		for (int i = 0; i < otp.length; i++) {
			otp[i] = numbers.charAt(random.nextInt(numbers.length()));
		}
		otpCache.put(email, Integer.valueOf(String.valueOf(otp)));
		return Integer.valueOf(String.valueOf(otp));
	}

	public void clearOtpfromCache(String key) {
		otpCache.invalidate(key);
	}

	public Integer getOTPByKey(String key) {
		return otpCache.getIfPresent(key);
	}
}
