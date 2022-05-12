package com.unpeu.config.media;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import com.unpeu.config.exception.CustomException;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.unpeu.config.exception.ErrorCode.IMAGE_NOT_FOUNT;

@Service
@AllArgsConstructor
@Slf4j
public class MediaService {

	private GlobalConfig config;

	public String save(MultipartFile file) {
		// 파일 저장 경로에서 현재 날짜로 된 폴더 내에 저장
		String today = new SimpleDateFormat("yyyyMMdd")
			.format(Calendar.getInstance().getTime());
		String uploadPath = config.getUploadPath() + today + "/";

		// 해당 저장 경로가 존재하지 않을 경우
		File folder = new File(uploadPath);
		if (!folder.isDirectory()) {
			if (!folder.mkdirs()) {
				System.out.println("[ERROR] Folder generation failed");
				return null;
			}
		}

		// 저장 파일명 생성
		String ogFilename = file.getOriginalFilename(); // original filename
		String ext = ogFilename.substring(ogFilename.lastIndexOf(".") + 1); // 확장자 추출
		String filename = UUID.randomUUID() + "." + ext;

		// 파일 저장
		File savedFile = new File(uploadPath + filename);
		try {
			file.transferTo(savedFile);
		} catch (IOException e) {
			log.error("IOException", e);
		}

		// 리소스 경로 생성
		return config.getResourcePath() + today + "/" + filename;
	}

	public void delete(String imgUrl) {
		// 이미지 저장된 경로 찾기

		String saveFilePath = imgUrl.replace(config.getResourcePath(), config.getUploadPath());
		File savedFile = new File(saveFilePath);

		if (!savedFile.exists()) {
			throw new CustomException(IMAGE_NOT_FOUNT);
		}

		savedFile.delete();
	}

}
